package com.yg.reservation.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.CategorySqls;
import com.yg.reservation.domain.Category;

@Repository
public class CategoryDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private RowMapper<Category> categoryRowMapper = BeanPropertyRowMapper
			.newInstance(Category.class);

	public CategoryDao(DataSource dataSource) {
		this.jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
	}

	public List<Category> selectAll() {
		return jdbc.query(CategorySqls.SELECT_ALL, categoryRowMapper);
	}
}
