package com.ys.reservation.dao;

public class CategorySqls {
	public final static String SELECT_ALL = "SELECT * FROM category ORDER BY name";
	public final static String DELETE_BY_ID = "DELETE FROM category WHERE id = :id";
	public final static String UPDATE_BY_ID = "UPDATE category SET name = :name WHERE id = :id";
}
