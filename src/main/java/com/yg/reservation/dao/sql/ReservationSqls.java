package com.yg.reservation.dao.sql;

public class ReservationSqls {
	public static final String SELECT_MY_BY_USER_ID = 
			"SELECT r.id, r.product_id, p.name AS product_name, r.general_ticket_count, r.youth_ticket_count, r.child_ticket_count, r.reservation_type, d.display_start, d.display_end, r.total_price "
			+ "FROM reservations AS r "
			+ "INNER JOIN products AS p ON r.product_id=p.id "
			+ "INNER JOIN product_displays AS d ON r.product_id=d.product_id "
			+ "WHERE r.user_id=:userId ";
}
