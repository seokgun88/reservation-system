package com.yg.reservation.dao;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.ProductPriceSqls;
import com.yg.reservation.domain.ProductPrice;

@Repository
public class ProductPriceDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductPrice> productPriceRowMapper = BeanPropertyRowMapper
			.newInstance(ProductPrice.class);

	public ProductPriceDao(DataSource dataSource) {
		this.jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
	}

	public List<ProductPrice> selectPrices(int id) {
		Map<String, Integer> param = Collections.singletonMap("productId", id);
		return jdbc.query(ProductPriceSqls.SELECT_BY_PRODUCT_ID, param,
				productPriceRowMapper);
	}

}
