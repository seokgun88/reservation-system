package com.ys.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.ImageSqls;
import com.ys.reservation.dao.sqls.UserCommentSqls;
import com.ys.reservation.domain.Image;
import com.ys.reservation.vo.CommentsSummaryVo;
import com.ys.reservation.vo.UserCommentVo;

@Repository
public class UserCommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<UserCommentVo> userCommentRowMapper = BeanPropertyRowMapper.newInstance(UserCommentVo.class);
	private RowMapper<CommentsSummaryVo> userCommentVoRowMapper = BeanPropertyRowMapper.newInstance(CommentsSummaryVo.class);
	private RowMapper<Image> imageRowMapper = BeanPropertyRowMapper.newInstance(Image.class);
	
	public UserCommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<UserCommentVo> selectByProductId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.query(UserCommentSqls.SELECT_BY_PRODUCT_ID, params, userCommentRowMapper);
	}
	
	public CommentsSummaryVo selectAvgScoreByProductId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(UserCommentSqls.SELECT_AVG_SCORE_BY_PRODUCT_ID, params, userCommentVoRowMapper);
	}
	
	public List<Image> selectImages(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.query(ImageSqls.SELECT_COMMENT_IMAGES, params, imageRowMapper);
	}
}
