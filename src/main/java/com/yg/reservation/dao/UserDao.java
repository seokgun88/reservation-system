package com.yg.reservation.dao;

import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.UserSqls;
import com.yg.reservation.domain.User;

@Repository
public class UserDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<User> userRowMapper = BeanPropertyRowMapper
			.newInstance(User.class);

	public UserDao(DataSource dataSource) {
		jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
		insertAction = new SimpleJdbcInsert(dataSource).withTableName("users")
				.usingGeneratedKeyColumns("id");
	}

	public User select(String snsId) {
		Map<String, ?> params = Collections.singletonMap("snsId", snsId);
		return jdbc.queryForObject(UserSqls.SELECT, params, userRowMapper);
	}

	public User insert(User user) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		int id = insertAction.executeAndReturnKey(params).intValue();
		user.setId(id);
		return user;
	}
}
