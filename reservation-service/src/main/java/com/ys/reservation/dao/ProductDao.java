package com.ys.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.ProductJoinSqls;
import com.ys.reservation.dao.sqls.ProductSqls;
import com.ys.reservation.domain.Product;
import com.ys.reservation.vo.DisplayInfoVo;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductVo;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> productRowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<ProductVo> dtoRowMapper = BeanPropertyRowMapper.newInstance(ProductVo.class);
	private RowMapper<ProductDetailVo> detailRowMapper = BeanPropertyRowMapper.newInstance(ProductDetailVo.class);
	private RowMapper<DisplayInfoVo> displayInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoVo.class);

	public ProductDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
	}
	
	public List<ProductVo> selectAll() {
		//return jdbc.query(ProductSqls.SELECT_ALL, rowMapper);
		return jdbc.query(ProductJoinSqls.SELECT_ALL, dtoRowMapper);
	}
	
	public List<ProductVo> selectLimited(int offset) {
		Map<String, ?> params = Collections.singletonMap("offset", offset);
		return jdbc.query(ProductJoinSqls.LIMITED_SELECT_ALL, params, dtoRowMapper);
	}

	public Product select(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return DaoUtil.getFirstOrNull(jdbc, ProductSqls.SELECT_BY_ID, params, productRowMapper);
	}
	
	public int countAll() {
		Map<String, ?> params = Collections.emptyMap();
		return DaoUtil.getFirstOrNull(jdbc, ProductSqls.COUNT_ALL, params, Integer.class);
	}
	
	public List<ProductVo> selectLimitedByCategoryId(int categoryId, int offset) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("offset", offset);
		return jdbc.query(ProductJoinSqls.LIMITED_SELECT_BY_CATEGORY_ID, params, dtoRowMapper);
	}
	
	public ProductDetailVo selectDetail(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return DaoUtil.getFirstOrNull(jdbc, ProductJoinSqls.SELECT_DETAIL, params, detailRowMapper);
	}
	
	public DisplayInfoVo selectDisplayInfo(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return DaoUtil.getFirstOrNull(jdbc, ProductJoinSqls.SELECT_DISPLAY_INFO, params, displayInfoRowMapper);
	}
}
