package com.ys.reservation.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class DaoUtil {
	public static <T> T getFirstOrNull(NamedParameterJdbcTemplate jdbc, 
			String sql, SqlParameterSource params, Class<T> elementType) {
		List<T> list = jdbc.queryForList(sql, params, elementType);
		return getFirstOrNullFromList(list);
	}
	public static <T> T getFirstOrNull(NamedParameterJdbcTemplate jdbc, 
			String sql, Map<String, ?> params, Class<T> elementType) {
		List<T> list = jdbc.queryForList(sql, params, elementType);
		return getFirstOrNullFromList(list);
	}
	public static <T> T getFirstOrNull(NamedParameterJdbcTemplate jdbc, 
			String sql, SqlParameterSource params, RowMapper<T> rowMapper) {
		List<T> list = jdbc.query(sql, params, rowMapper);
		return getFirstOrNullFromList(list);
	}
	public static <T> T getFirstOrNull(NamedParameterJdbcTemplate jdbc, 
			String sql, Map<String, ?> params, RowMapper<T> rowMapper) {
		List<T> list = jdbc.query(sql, params, rowMapper);
		return getFirstOrNullFromList(list);
	}
	public static <T> T getFirstOrNullFromList(List<T> list) {
		if(list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
}
