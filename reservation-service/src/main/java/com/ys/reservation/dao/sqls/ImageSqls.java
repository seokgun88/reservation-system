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
	public static final String SELECT_ID_BY_COMMENT_ID = 
			"SELECT f.id "
			+ "FROM reservation_user_comment_image c, file f "
			+ "WHERE c.reservation_user_comment_id = :id and c.file_id=f.id";
	public static final String SELECT_ID_BY_COMMENT_IDS = 
			"SELECT c.reservation_user_comment_id comment_id, f.id file_id "
			+ "FROM reservation_user_comment_image c, file f "
			+ "WHERE c.file_id = f.id AND c.reservation_user_comment_id IN (:ids)";
	public static final String DELETE_IMAGE = "UPDATE file SET delete_flag=1 WHERE id=:id";
	public static final String UPDATE_IMAGES = "UPDATE file SET delete_flag=0 WHERE id IN (:ids)";
}
