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
}
