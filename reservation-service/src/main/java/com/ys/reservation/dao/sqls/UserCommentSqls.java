package com.ys.reservation.dao.sqls;

public class UserCommentSqls {
	public static final String SELECT_BY_PRODUCT_ID = 
			"SELECT c.*, u.sns_id "
			+ "FROM reservation_user_comment c, users u "
			+ "WHERE c.product_id = :id AND c.user_id = u.id";
	public static final String SELECT_AVG_SCORE_BY_PRODUCT_ID = 
			"SELECT COUNT(*) num, AVG(score) avg_score "
			+ "FROM reservation_user_comment "
			+ "WHERE product_id = :id";
	public static final String UPDATE_USER_COMMENT = 
			"UPDATE reservation_user_comment "
			+ "SET score=:score, comment=:comment "
			+ "WHERE id=:id";
}
