package com.yg.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.ImageSqls;
import com.yg.reservation.domain.Image;
import com.yg.reservation.vo.ReviewImageVo;

@Repository
public class ImageDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Image> imageRowMapper =
			BeanPropertyRowMapper.newInstance(Image.class);
	private RowMapper<ReviewImageVo> reviewImageVoRowMapper =
			BeanPropertyRowMapper.newInstance(ReviewImageVo.class);
	
	public ImageDao(DataSource dataSource) {
		this.jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("files")
				.usingGeneratedKeyColumns("id");
	}
	
	public int insert(Image file) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(file);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public Image select(int id) {
		Map<String, ?> param = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ImageSqls.SELECT, param, imageRowMapper);
	}

	public List<Integer> selectIdsByProductId(int productId) {
		Map<String, Integer> param = Collections.singletonMap("productId", productId);
		return jdbc.queryForList(ImageSqls.SELECT_BY_PRODUCT_ID, param, Integer.class);
	}
	
	public List<ReviewImageVo> selectIdsByReviewIds(List<Integer> reviewIds) {
		Map<String, List<Integer>> param = Collections.singletonMap("reviewIds", reviewIds);
		return jdbc.query(ImageSqls.SELECT_BY_REVIEW_IDS, param, reviewImageVoRowMapper);
	}
	
	public int updateDeleteFlagTo0(List<Integer> ids) {
		Map<String, List<Integer>> param = Collections.singletonMap("ids", ids);
		return jdbc.update(ImageSqls.UPDATE_DELETE_FLAG_TO_0, param);
	}
}
