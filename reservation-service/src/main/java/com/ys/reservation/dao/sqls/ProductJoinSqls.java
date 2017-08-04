package com.ys.reservation.dao.sqls;

public class ProductJoinSqls {
	public static final String SELECT_ALL = 
			"SELECT p.id, p.name, p.description, d.place_name, d.id "
			+ "FROM product p INNER JOIN display_info d ON p.id = d.product_id";
	public static final String LIMITED_SELECT_ALL = 
			"SELECT p.id, p.name, p.description, d.place_name, d.id "
			+ "FROM product p INNER JOIN display_info d ON p.id = d.product_id "
			+ "LIMIT :offset, 10";
	public static final String LIMITED_SELECT_BY_CATEGORY_ID = 
			"SELECT p.id, p.name, p.description, d.place_name, d.id "
			+ "FROM product p INNER JOIN display_info d ON p.id = d.product_id "
			+ "WHERE p.category_id = :categoryId "
			+ "LIMIT :offset, 10";
	public static final String SELECT_DETAIL = 
			"SELECT p.id, p.name, p.description, p.event, p.sales_end, p.sales_flag, d.content "
			+ "FROM product p INNER JOIN product_detail d ON p.id = d.product_id "
			+ "WHERE p.id = :id";
	public static final String SELECT_FILES = 
			"SELECT f.id, file_name, save_file_name, file_length, content_type "
			+ "FROM product_image p INNER JOIN file f ON p.file_id = f.id "
			+ "WHERE p.product_id = :id AND p.type = :type";
	public static final String SELECT_DISPLAY_INFO = 
			"SELECT place_name, place_lot, place_street, tel, homepage, email "
			+ "FROM display_info "
			+ "WHERE product_id = :id";
	public static final String SELECT_RESERVATION_INFO = 
			"SELECT p.name, d.place_name, d.display_start, d.display_end, d.observation_time "
			+ "FROM product p INNER JOIN display_info d ON p.id = d.product_id "
			+ "WHERE p.id = :id";
}
