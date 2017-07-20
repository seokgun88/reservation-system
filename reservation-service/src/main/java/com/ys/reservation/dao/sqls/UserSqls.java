package com.ys.reservation.dao.sqls;

public class UserSqls {
	public static final String SELECT = 
			"SELECT * "
			+ "FROM users "
			+ "WHERE email = :email";
}
