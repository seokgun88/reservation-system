package com.ys.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.UserCommentSqls;
import com.ys.reservation.domain.UserComment;
import com.ys.reservation.domain.UserCommentImage;
import com.ys.reservation.vo.CommentsSummaryVo;
import com.ys.reservation.vo.UserCommentVo;

@Repository
public class UserCommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private SimpleJdbcInsert imageInsertAction;
	private RowMapper<UserCommentVo> userCommentRowMapper = BeanPropertyRowMapper.newInstance(UserCommentVo.class);
	private RowMapper<CommentsSummaryVo> userCommentVoRowMapper = BeanPropertyRowMapper.newInstance(CommentsSummaryVo.class);
	
	public UserCommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_comment")
				.usingGeneratedKeyColumns("id");
		this.imageInsertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_comment_image")
				.usingGeneratedKeyColumns("id");
	}

	public List<UserCommentVo> selectLimitedByProductId(int id, int offset, int limit) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", id);
		params.put("offset", offset);
		params.put("limit", limit);
		
		return jdbc.query(UserCommentSqls.SELECT_BY_PRODUCT_ID, params, userCommentRowMapper);
	}
	
	public CommentsSummaryVo selectAvgScoreByProductId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return DaoUtil.getFirstOrNull(jdbc, UserCommentSqls.SELECT_AVG_SCORE_BY_PRODUCT_ID, params, userCommentVoRowMapper);
	}
	
	public int insert(UserComment userComment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(userComment);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public int update(UserComment userComment) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(userComment);
		return jdbc.update(UserCommentSqls.UPDATE_USER_COMMENT , params);
	}
	
	public int[] insertImages(List<UserCommentImage> images) {
		SqlParameterSource[] params = SqlParameterSourceUtils.createBatch(images.toArray());
		return imageInsertAction.executeBatch(params);
	}
}
