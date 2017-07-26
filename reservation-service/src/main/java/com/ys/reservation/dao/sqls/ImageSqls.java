package com.ys.reservation.dao.sqls;

public class ImageSqls {
	public static final String SELECT = 
			"SELECT * "
			+ "FROM file "
			+ "WHERE id = :id";
	public static final String SELECT_MAIN_IMAGE_ID = 
			"SELECT f.id "
			+ "FROM product_image p, file f "
			+ "WHERE p.product_id = :id and p.type=0 and p.file_id=f.id "
			+ "ORDER BY id LIMIT 1";
	public static final String SELECT_SUB_IMAGE = 
			"SELECT f.* "
			+ "FROM product_image p, file f "
			+ "WHERE p.product_id = :id and p.type=1 and p.file_id=f.id";
	public static final String SELECT_COMMENT_IMAGES = 
			"SELECT f.* "
			+ "FROM reservation_user_comment_image c, file f "
			+ "WHERE c.reservation_user_comment_id = :id and c.file_id=f.id";
}
