package com.ys.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

import com.ys.reservation.dao.sqls.FileSqls;
import com.ys.reservation.dao.sqls.ProductJoinSqls;
import com.ys.reservation.domain.FileDomain;

@Repository
public class FileDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<FileDomain> fileRowMapper = BeanPropertyRowMapper.newInstance(FileDomain.class);

	
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
		return jdbc.queryForObject(FileSqls.SELECT, params, fileRowMapper);
	}
	
	public List<FileDomain> selectByProductId(int id, int type) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", id);
		params.put("type", type);
		return jdbc.query(ProductJoinSqls.SELECT_FILES, params, fileRowMapper);
	}
	
	public int selectMainImageId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		try {
			return jdbc.queryForObject(FileSqls.SELECT_MAIN_IMAGE_ID, params, Integer.class);
		} catch(EmptyResultDataAccessException e) {
			return -1;
		}
	}
	
	public FileDomain selectSubImage(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(FileSqls.SELECT_SUB_IMAGE, params, fileRowMapper);
	}
}
