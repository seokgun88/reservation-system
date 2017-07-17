package com.ys.reservation.dao;

public class FileSqls {
	public final static String SELECT = 
			"SELECT * "
			+ "FROM file "
			+ "WHERE id = :id";
	public final static String SELECT_SUB_IMAGE = 
			"SELECT f.* "
			+ "FROM product_image p, file f "
			+ "WHERE p.product_id = :id and p.type=1 and p.file_id=f.id";
	public final static String SELECT_COMMENT_IMAGES = 
			"SELECT f.* "
			+ "FROM reservation_user_comment_image c, file f "
			+ "WHERE c.reservation_user_comment_id = :id and c.file_id=f.id";
}
