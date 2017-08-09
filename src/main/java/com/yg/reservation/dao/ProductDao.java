package com.yg.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.ProductSqls;
import com.yg.reservation.vo.ProductSummaryVo;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductSummaryVo> productSummaryRowMapper = BeanPropertyRowMapper
			.newInstance(ProductSummaryVo.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<ProductSummaryVo> selectPromotions() {
		return jdbc.query(ProductSqls.SELECT_PROMOTION,
				productSummaryRowMapper);
	}

	public List<ProductSummaryVo> selectProducts(int offset) {
		Map<String, Integer> params = Collections.singletonMap("offset",
				offset);

		return jdbc.query(ProductSqls.SELECT_SUMMARY_LIMITED_10, params,
				productSummaryRowMapper);
	}

	public List<ProductSummaryVo> selectProducts(int categoryId, int offset) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("categoryId", categoryId);
		params.put("offset", offset);

		return jdbc.query(ProductSqls.SELECT_SUMMARY_LIMITED_10_BY_CATEGORY_ID,
				params, productSummaryRowMapper);
	}
}
