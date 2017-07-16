package com.ys.reservation.dao;

public class DetailSqls {
	public final static String SELECT_COMMENT = 
			"SELECT user_id, score, comment "
			+ "FROM reservation_user_comment "
			+ "WHERE product_id = :id";
	public final static String SELECT_COMMENT_IMG = 
			"SELECT file_name, save_file_name, file_length, content_type "
			+ "FROM reservation_user_comment_image c, file f "
			+ "WHERE c.reservation_user_comment_id = :id AND c.file_id = f.id";
}
