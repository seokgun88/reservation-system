package com.ys.reservation.dao.sqls;

public class ProductSqls {
	public static final String SELECT_ALL = 
			"SELECT * FROM product";
	public static final String LIMITED_SELECT = 
			"SELECT * FROM product LIMIT :offset, 10";
	public static final String SELECT_BY_ID = 
			"SELECT * FROM product WHERE id = :id";
	public static final String SELECT_BY_CATEGORY_ID = 
			"SELECT * FROM product WHERE category_id = :categoryId";
	public static final String LIMITED_SELECT_BY_CATEGORY_ID = 
			"SELECT * FROM product WHERE category_id = :categoryId LIMIT :offset, 10";
	public static final String COUNT_ALL = 
			"SELECT COUNT(*) FROM product";
	public static final String COUNT_BY_CATEGORY_ID = 
			"SELECT COUNT(*) FROM product WHERE category_id = :categoryId";
}
