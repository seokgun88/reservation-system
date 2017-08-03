package com.ys.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.ProductJoinSqls;
import com.ys.reservation.dao.sqls.ReservationSqls;
import com.ys.reservation.domain.ReservationInfo;
import com.ys.reservation.vo.ProductReservationInfoVo;
import com.ys.reservation.vo.ReservationVo;

@Repository
public class ReservationDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<ProductReservationInfoVo> reservationInfoRowMapper = BeanPropertyRowMapper.newInstance(ProductReservationInfoVo.class);
	private RowMapper<ReservationVo> reservationRowMapper = BeanPropertyRowMapper.newInstance(ReservationVo.class);

	public ReservationDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("reservation_info")
				.usingGeneratedKeyColumns("id");
	}

	public ProductReservationInfoVo selectByProductId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return DaoUtil.getFirstOrNull(jdbc, ProductJoinSqls.SELECT_RESERVATION_INFO, params, reservationInfoRowMapper);
	}
	
	public int insert(ReservationInfo reservationInfo) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(reservationInfo);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public List<ReservationVo> selectReservations(int userId){
		Map<String, ?> params = Collections.singletonMap("id", userId);
		return jdbc.query(ReservationSqls.SELECT_BY_USER_ID, params, reservationRowMapper);
	}
	
	public int update(int reservationId) {
		Map<String, ?> params = Collections.singletonMap("id", reservationId);
		return jdbc.update(ReservationSqls.UPDATE_SET_TYPE_TO_4, params);
	}
}
