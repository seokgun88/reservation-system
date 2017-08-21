package com.yg.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.ReservationSqls;
import com.yg.reservation.domain.Reservation;
import com.yg.reservation.vo.ReservationVo;

@Repository
public class ReservationDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<ReservationVo> reservationVoRowMapper = BeanPropertyRowMapper
			.newInstance(ReservationVo.class);

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

	public List<ReservationVo> selectMy(int userId) {
		Map<String, ?> params = Collections.singletonMap("userId", userId);
		return jdbc.query(ReservationSqls.SELECT_MY_BY_USER_ID, params,
				reservationVoRowMapper);
	}
}
