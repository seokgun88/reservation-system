package com.ys.reservation.dao;

import java.util.Collections;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.FileSqls;
import com.ys.reservation.domain.FileDomain;

@Repository
public class FileDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<FileDomain> rowMapper = BeanPropertyRowMapper.newInstance(FileDomain.class);
	
	public FileDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("file")
				.usingGeneratedKeyColumns("id");
	}
	
	public int insert(FileDomain file) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(file);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public FileDomain select(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(FileSqls.SELECT, params, rowMapper);
	}
	
	public FileDomain selectSubImage(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(FileSqls.SELECT_SUB_IMAGE, params, rowMapper);
	}
}
