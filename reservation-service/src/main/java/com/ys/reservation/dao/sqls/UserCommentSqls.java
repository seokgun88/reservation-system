package com.ys.reservation.dao.sqls;

public class UserCommentSqls {
	public static final String SELECT_BY_PRODUCT_ID = 
			"SELECT o.id, r.product_id, r.user_id, r.score, r.comment, u.sns_id " 
			+ "FROM ( " 
			+ "	SELECT id " 
			+ "	FROM reservation_user_comment " 
			+ "	WHERE product_id=:id " 
			+ "	ORDER BY id DESC " 
			+ "	LIMIT :offset, :limit " 
			+ "	) o " 
			+ "JOIN reservation_user_comment r ON o.id=r.id " 
			+ "JOIN users u ON r.user_id=u.id";
	public static final String SELECT_AVG_SCORE_BY_PRODUCT_ID = 
			"SELECT COUNT(*) num, AVG(score) avg_score "
			+ "FROM reservation_user_comment "
			+ "WHERE product_id = :id";
	public static final String UPDATE_USER_COMMENT = 
			"UPDATE reservation_user_comment "
			+ "SET score=:score, comment=:comment "
			+ "WHERE id=:id";
}
