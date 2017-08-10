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
}
