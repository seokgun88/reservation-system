package com.yg.reservation.dao.sql;

public class UserSqls {
	public static final String SELECT = 
			"SELECT id, username, email, nickname, sns_id, admin_flag "
			+ "FROM users WHERE sns_id = :snsId";
}
