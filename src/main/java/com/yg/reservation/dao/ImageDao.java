package com.yg.reservation.dao;

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

import com.yg.reservation.dao.sql.ImageSqls;
import com.yg.reservation.domain.Image;
import com.yg.reservation.vo.MainImageVo;

@Repository
public class ImageDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	private RowMapper<MainImageVo> mainImageVoRowMapper = 
			BeanPropertyRowMapper.newInstance(MainImageVo.class);
	
	public ImageDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("files")
				.usingGeneratedKeyColumns("id");
	}
	
	public int insert(Image file) {
		SqlParameterSource params = new BeanPropertySqlParameterSource(file);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public List<MainImageVo> selectMainImageByProductId(List<Integer> productIds) {
		Map<String, ?> params = Collections.singletonMap("productIds", productIds);
		return jdbc.query(ImageSqls.SELECT_MIN_IDS_BY_PRODUCT_IDS, params, mainImageVoRowMapper);
	}
}
