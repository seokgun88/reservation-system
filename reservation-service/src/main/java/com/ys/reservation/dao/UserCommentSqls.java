package com.ys.reservation.dao;

public class UserCommentSqls {
	public final static String SELECT_BY_PRODUCT_ID = 
			"SELECT c.*, u.sns_id "
			+ "FROM reservation_user_comment c, users u "
			+ "WHERE c.product_id = :id AND c.user_id = u.id";
	public final static String SELECT_AVG_SCORE_BY_PRODUCT_ID = 
			"SELECT COUNT(*) num, AVG(score) avg_score "
			+ "FROM reservation_user_comment "
			+ "WHERE product_id = :id";
}
