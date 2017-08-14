package com.yg.reservation.dao.sql;

public class ImageSqls {
	public static final String SELECT = 
			"SELECT id, user_id, file_name, save_file_name, file_length, content_type, delete_flag "
			+ "FROM files "
			+ "WHERE id=:id";
	public static final String SELECT_MIN_IDS_BY_PRODUCT_IDS = 
			"SELECT product_id, MIN(file_id) AS main_image_id "
			+ "FROM product_images "
			+ "WHERE product_id IN (:productIds) "
			+ "GROUP BY product_id";
	public static final String SELECT_BY_PRODUCT_ID = 
			"SELECT file_id FROM product_images WHERE product_id=:productId AND type=0";
	public static final String SELECT_BY_REVIEW_IDS = 
			"SELECT reservation_user_review_id AS review_id, file_id AS image_id "
			+ "FROM reservation_user_review_images "
			+ "WHERE reservation_user_review_id IN (:reviewIds)";
}
