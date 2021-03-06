package com.yg.reservation.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.ReviewSqls;
import com.yg.reservation.domain.Review;
import com.yg.reservation.vo.ReviewVo;

@Repository
public class ReviewDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<ReviewVo> reviewRowMapper = BeanPropertyRowMapper
			.newInstance(ReviewVo.class);

	public ReviewDao(DataSource dataSource) {
		this.jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_reviews")
				.usingGeneratedKeyColumns("id", "create_date", "modify_date");
	}

	public List<ReviewVo> selectLimitedByProductId(int productId, int limit) {
		Map<String, Integer> params = new HashMap<>();
		params.put("productId", productId);
		params.put("limit", limit);
		return jdbc.query(ReviewSqls.SELECT_LIMITED_BY_PRODUCT_ID, params,
				reviewRowMapper);
	}

	public int insert(Review review) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(review);
		return insertAction.executeAndReturnKey(params).intValue();
	}
}
