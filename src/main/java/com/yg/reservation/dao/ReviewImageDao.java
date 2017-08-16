package com.yg.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.yg.reservation.domain.ReviewImage;

@Repository
public class ReviewImageDao {
	private SimpleJdbcInsert insertAction;
	
	public ReviewImageDao(DataSource dataSource) {
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_user_review_images")
				.usingGeneratedKeyColumns("id");
	}
	
	public int insert(ReviewImage reviewImage) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reviewImage);
		return insertAction.executeAndReturnKey(params).intValue();
	}
}
