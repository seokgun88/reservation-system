package com.ys.reservation.dao.sqls;

public class ImageSqls {
	public static final String SELECT = 
			"SELECT * "
			+ "FROM file "
			+ "WHERE id = :id";
	public static final String SELECT_MAIN_IMAGE_ID = 
			"SELECT f.id "
			+ "FROM product_image p INNER JOIN file f ON p.file_id = f.id "
			+ "WHERE p.product_id = :id AND p.type=0 "
			+ "ORDER BY id LIMIT 1";
	public static final String SELECT_SUB_IMAGE = 
			"SELECT f.* "
			+ "FROM product_image p INNER JOIN file f ON p.file_id = f.id "
			+ "WHERE p.product_id = :id AND p.type=1";
	public static final String SELECT_ID_BY_COMMENT_ID = 
			"SELECT f.id "
			+ "FROM reservation_user_comment_image c INNER JOIN file f ON c.file_id = f.id "
			+ "WHERE c.reservation_user_comment_id = :id";
	public static final String SELECT_ID_BY_COMMENT_IDS = 
			"SELECT c.reservation_user_comment_id comment_id, f.id file_id "
			+ "FROM reservation_user_comment_image c INNER JOIN file f ON c.file_id = f.id "
			+ "WHERE c.reservation_user_comment_id IN (:ids)";
	public static final String UPDATE_DELETE_FLAG_TO_1 = 
			"UPDATE file SET delete_flag=1 WHERE id=:id";
	public static final String UPDATE_DELETE_FLAG_TO_0_BY_IDS = 
			"UPDATE file SET delete_flag=0 WHERE id IN (:ids)";
}
