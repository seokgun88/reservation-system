package com.ys.reservation.dao.sqls;

public class ReservationSqls {
	public static final String SELECT_BY_USER_ID = 
			"SELECT r.id, r.product_id, p.name product_name, r.general_ticket_count, "
			+ "r.youth_ticket_count, r.child_ticket_count, r.reservation_type, "
			+ "d.display_start, d.display_end "
			+ "FROM reservation_info r "
			+ "INNER JOIN product p "
			+ "ON r.product_id = p.id "
			+ "INNER JOIN display_info d "
			+ "ON r.product_id = d.product_id "
			+ "WHERE AND r.user_id = :id";
	public static final String UPDATE_SET_TYPE_TO_4 = 
			"UPDATE reservation_info SET reservation_type=4 WHERE id=:id";
}
