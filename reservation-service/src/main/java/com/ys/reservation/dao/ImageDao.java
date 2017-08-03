package com.ys.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.ImageSqls;
import com.ys.reservation.dao.sqls.ProductJoinSqls;
import com.ys.reservation.domain.Image;
import com.ys.reservation.vo.CommentImageVo;

@Repository
public class ImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Image> imageRowMapper = BeanPropertyRowMapper.newInstance(Image.class);
	private RowMapper<CommentImageVo> commentImageRowMapper = BeanPropertyRowMapper.newInstance(CommentImageVo.class);

	
	public ImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("file")
				.usingGeneratedKeyColumns("id");
	}
	
	public int insert(Image file) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(file);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Image select(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return DaoUtil.getFirstOrNull(jdbc, ImageSqls.SELECT, params, imageRowMapper);
	}
	
	public List<Image> selectByProductId(int id, int type) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", id);
		params.put("type", type);
		return jdbc.query(ProductJoinSqls.SELECT_FILES, params, imageRowMapper);
	}
	
	public int selectMainImageId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		try {
			return jdbc.queryForObject(ImageSqls.SELECT_MAIN_IMAGE_ID, params, Integer.class);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}
	
	public Image selectSubImage(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ImageSqls.SELECT_SUB_IMAGE, params, imageRowMapper);
	}
	
	public List<Integer> selectIdByCommentId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForList(ImageSqls.SELECT_ID_BY_COMMENT_ID, params, Integer.class);
	}

	public List<CommentImageVo> selectIdByCommentIds(List<Integer> ids) {
		Map<String, ?> params = Collections.singletonMap("ids", ids);
		return jdbc.query(ImageSqls.SELECT_ID_BY_COMMENT_IDS, params, commentImageRowMapper);
	}
	
	public int delete(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(ImageSqls.UPDATE_DELETE_FLAG_TO_1, params);
	}
	
	public int update(List<Integer> ids) {
		Map<String, ?> params = Collections.singletonMap("ids", ids);
		return jdbc.update(ImageSqls.UPDATE_DELETE_FLAG_TO_0_BY_IDS, params);
	}
}
