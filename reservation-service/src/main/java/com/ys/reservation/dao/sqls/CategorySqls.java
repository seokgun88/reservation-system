package com.ys.reservation.dao.sqls;

public class CategorySqls {
	public static final String SELECT_ALL = 
			"SELECT * FROM category ORDER BY name";
	public static final String DELETE_BY_ID = 
			"DELETE FROM category WHERE id = :id";
	public static final String UPDATE_BY_ID = 
			"UPDATE category SET name = :name WHERE id = :id";
}
