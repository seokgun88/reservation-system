package com.ys.reservation.dao;

import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.UserSqls;
import com.ys.reservation.domain.User;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
	
	public UserDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
		insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("users")
				.usingGeneratedKeyColumns("id");
	}
	
	public User select(String email) {
		Map<String, String> params = Collections.singletonMap("email", email);
		try {
			return jdbc.queryForObject(UserSqls.SELECT, params, userRowMapper);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public User insert(User user) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(user);
		int id = insertAction.executeAndReturnKey(params).intValue();
		user.setId(id);
		return user;
	}
	
}
