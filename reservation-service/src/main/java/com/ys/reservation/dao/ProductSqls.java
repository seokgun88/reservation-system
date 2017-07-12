package com.ys.reservation.dao;

public class ProductSqls {
	public final static String SELECT_ALL = "SELECT * FROM product";
	public final static String LIMITED_SELECT = "SELECT * FROM product LIMIT :offset, 10";
	public final static String SELECT_BY_ID = "SELECT * FROM product WHERE id = :id";
	public final static String SELECT_BY_CATEGORY_ID = "SELECT * FROM product WHERE category_id = :categoryId";
	public final static String LIMITED_SELECT_BY_CATEGORY_ID = "SELECT * FROM product WHERE category_id = :categoryId LIMIT :offset, 10";
	public final static String COUNT_ALL = "SELECT COUNT(*) FROM product";
	public final static String COUNT_BY_CATEGORY_ID = "SELECT COUNT(*) FROM product WHERE category_id = :categoryId";
}
