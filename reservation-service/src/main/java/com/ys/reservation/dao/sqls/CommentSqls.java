package com.ys.reservation.dao.sqls;

public class CommentSqls {
	public static final String SELECT_COMMENT = 
			"SELECT user_id, score, comment "
			+ "FROM reservation_user_comment "
			+ "WHERE product_id = :id";
	public static final String SELECT_COMMENT_IMG = 
			"SELECT file_name, save_file_name, file_length, content_type "
			+ "FROM reservation_user_comment_image c INNER JOIN file f ON c.file_id = f.id "
			+ "WHERE c.reservation_user_comment_id = :id";
}
