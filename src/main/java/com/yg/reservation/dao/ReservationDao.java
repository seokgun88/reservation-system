package com.yg.reservation.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.yg.reservation.domain.Reservation;

@Repository
public class ReservationDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Reservation> reservationRowMapper = BeanPropertyRowMapper
			.newInstance(Reservation.class);

	public ReservationDao(DataSource dataSource) {
		this.jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservations")
				.usingGeneratedKeyColumns("id", "create_date", "modify_date");
	}

	public int insert(Reservation reservation) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(
				reservation);
		return insertAction.executeAndReturnKey(params).intValue();
	}

}
