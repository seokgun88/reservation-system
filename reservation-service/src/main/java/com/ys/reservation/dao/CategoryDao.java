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

import com.ys.reservation.domain.Category;

@Repository
public class CategoryDao {
	
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<Category> rowMapper = BeanPropertyRowMapper.newInstance(Category.class);
	
	public CategoryDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("category")
				.usingGeneratedKeyColumns("id");
	}
	
	public Integer insert(Category category) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(category);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public List<Category> selectAll() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(CategorySqls.SELECT_ALL, params, rowMapper);
	}
	
	public int delete(Integer id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.update(CategorySqls.DELETE_BY_ID, params);
	}
	
	public int update(Category category) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(category);
		return jdbc.update(CategorySqls.UPDATE_BY_ID, params);
	}
	
}
