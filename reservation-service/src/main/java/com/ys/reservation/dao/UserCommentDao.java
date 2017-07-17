package com.ys.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ys.reservation.domain.FileDomain;

@Repository
public class UserCommentDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<FileDomain> fileRowMapper = BeanPropertyRowMapper.newInstance(FileDomain.class);
	
	public UserCommentDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<FileDomain> selectFiles(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.query(FileSqls.SELECT_COMMENT_IMAGES, params, fileRowMapper);
	}
}
