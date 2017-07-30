package com.ys.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.PriceSqls;
import com.ys.reservation.domain.Price;

@Repository
public class PriceDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Price> priceRowMapper = BeanPropertyRowMapper.newInstance(Price.class);

	public PriceDao(DataSource dataSource) {
		jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<Price> selectByProductId(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.query(PriceSqls.SELECT_BY_PRODUCT_ID, params, priceRowMapper);
	}

	public List<Price> selectByProductIds(List<Integer> ids) {
		Map<String, ?> params = Collections.singletonMap("ids", ids);
		return jdbc.query(PriceSqls.SELECT_BY_PRODUCT_IDS, params, priceRowMapper);
	}
}
