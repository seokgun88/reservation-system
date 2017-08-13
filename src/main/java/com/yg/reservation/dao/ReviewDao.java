package com.yg.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.ReviewSqls;
import com.yg.reservation.domain.Review;

@Repository
public class ReviewDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private RowMapper<Review> reviewRowMapper = BeanPropertyRowMapper.newInstance(Review.class);
	
	public ReviewDao(DataSource dataSource) {
		jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
	}

	public List<Review> selectLimitedByProductId(int productId, int limit) {
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
		params.put("limit", limit);
		return jdbc.query(ReviewSqls.SELECT_LIMITED_BY_PRODUCT_ID, params, reviewRowMapper);
	}
}
