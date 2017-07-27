package com.ys.reservation.dao.sqls;

public class PriceSqls {
	public static final String SELECT_BY_PRODUCT_ID = 
			"SELECT price_type, price, discount_rate "
			+ "FROM product_price "
			+ "WHERE product_id = :id "
			+ "ORDER BY price_type";
	public static final String SELECT_BY_PRODUCT_IDS = 
			"SELECT product_id, price_type, price, discount_rate "
			+ "FROM product_price "
			+ "WHERE product_id IN (:ids)";
			
}
