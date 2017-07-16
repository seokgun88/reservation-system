package com.ys.reservation.dao;

public class ProductJoinSqls {
	public final static String SELECT_ALL = 
			"SELECT p.id, p.name, p.description, d.place_name, d.id "
			+ "FROM product p, display_info d "
			+ "WHERE p.id = d.product_id";
	public final static String LIMITED_SELECT_ALL = 
			"SELECT p.id, p.name, p.description, d.place_name, d.id "
			+ "FROM product p, display_info d "
			+ "WHERE p.id = d.product_id "
			+ "LIMIT :offset, 10";
	public final static String LIMITED_SELECT_BY_CATEGORY_ID = 
			"SELECT p.id, p.name, p.description, d.place_name, d.id "
			+ "FROM product p, display_info d "
			+ "WHERE p.category_id = :categoryId AND p.id = d.product_id "
			+ "LIMIT :offset, 10";
//	public final static String SELECT_DETAIL = 
//			"SELECT p.id, p.name, p.event, p.sales_end, p.sales_flag, f.file_name, f.save_file_name, f.file_length, f.content_type"
//			+ "FROM product p, product_image i, file f"
//			+ "WHERE p.id = :id AND p.id = i.product_id AND f.id = i.file_id";
	public final static String SELECT_DETAIL = 
			"SELECT p.id, p.name, p.description, p.event, p.sales_end, p.sales_flag, d.content "
			+ "FROM product p, product_detail d "
			+ "WHERE p.id = :id AND p.id = d.product_id;";
	public final static String SELECT_FILES = 
			"SELECT file_name, save_file_name, file_length, content_type "
			+ "FROM product_image p, file f "
			+ "WHERE p.product_id = :id AND p.type = :type AND p.file_id = f.id";
	public final static String SELECT_DISPLAY_INFO = 
			"SELECT place_name, place_lot, place_street, tel, homepage, email "
			+ "FROM display_info "
			+ "WHERE product_id = :id";
}
