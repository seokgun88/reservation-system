package com.ys.reservation.dao;

public class FileSqls {
	public final static String SELECT = 
			"SELECT * "
			+ "FROM file "
			+ "WHERE id = :id";

	public final static String SELECT_SUB_IMAGE = 
			"SELECT f.* "
			+ "FROM product_image p, file f "
			+ "WHERE p.product_id = :id and p.type=1 and p.file_id=f.id";
}
