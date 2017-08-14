package com.yg.reservation.dao.sql;

public class ReviewSqls {

	public static final String SELECT_LIMITED_BY_PRODUCT_ID = 
			"SELECT r.id, r.review, r.score, r.modify_date, u.email AS user_email "
			+ "FROM reservation_user_reviews AS r "
			+ "INNER JOIN users AS u ON r.user_id=u.id "
			+ "WHERE product_id=:productId "
			+ "LIMIT :limit";

}
