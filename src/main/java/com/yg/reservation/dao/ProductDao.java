package com.yg.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.yg.reservation.dao.sql.ProductSqls;
import com.yg.reservation.vo.ProductDetailVo;
import com.yg.reservation.vo.ProductReservationVo;
import com.yg.reservation.vo.ProductSummaryVo;

@Repository
public class ProductDao {
	private NullableNamedParameterJdbcTemplate jdbc;
	private RowMapper<ProductSummaryVo> productSummaryRowMapper = BeanPropertyRowMapper
			.newInstance(ProductSummaryVo.class);
	private RowMapper<ProductReservationVo> productReservationRowMapper = BeanPropertyRowMapper
			.newInstance(ProductReservationVo.class);
	private RowMapper<ProductDetailVo> productDetailVoRowMapper = BeanPropertyRowMapper
			.newInstance(ProductDetailVo.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NullableNamedParameterJdbcTemplate(dataSource);
	}

	public List<ProductSummaryVo> selectPromotion() {
		return jdbc.query(ProductSqls.SELECT_PROMOTION,
				productSummaryRowMapper);
	}

	public List<ProductSummaryVo> selectLimitedWithOffset(int offset) {
		Map<String, Integer> param = Collections.singletonMap("offset", offset);

		return jdbc.query(ProductSqls.SELECT_SUMMARY_LIMITED_10, param,
				productSummaryRowMapper);
	}

	public List<ProductSummaryVo> selectLimitedWithOffsetByCategoryId(
			int categoryId, int offset) {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("categoryId", categoryId);
		params.put("offset", offset);

		return jdbc.query(ProductSqls.SELECT_SUMMARY_LIMITED_10_BY_CATEGORY_ID,
				params, productSummaryRowMapper);
	}

	public ProductDetailVo selectDetail(int id) {
		Map<String, Integer> param = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ProductSqls.SELECT_DETAIL, param,
				productDetailVoRowMapper);
	}

	public ProductReservationVo selectReservation(int id) {
		Map<String, Integer> param = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ProductSqls.SELECT_DISPLAY, param,
				productReservationRowMapper);
	}

	public String selectName(int id) {
		Map<String, Integer> param = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ProductSqls.SELECT_NAME, param,
				String.class);
	}
}
