package com.yg.reservation.dao.sql;

public class ProductPriceSqls {
	public static final String SELECT_BY_PRODUCT_ID = 
			"SELECT product_id, price_type, price, discount_rate "
			+ "FROM product_prices "
			+ "WHERE product_id=:productId";
}
