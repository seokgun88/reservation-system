package com.ys.reservation.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ys.reservation.dao.sqls.ProductJoinSqls;
import com.ys.reservation.dao.sqls.ProductSqls;
import com.ys.reservation.dao.sqls.UserCommentSqls;
import com.ys.reservation.domain.FileDomain;
import com.ys.reservation.domain.Product;
import com.ys.reservation.vo.CommentsSummaryVo;
import com.ys.reservation.vo.DisplayInfoVo;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductReservationInfoVo;
import com.ys.reservation.vo.ProductVo;
import com.ys.reservation.vo.UserCommentVo;

@Repository
public class ProductDao {
	private NamedParameterJdbcTemplate jdbc;
	private RowMapper<Product> rowMapper = BeanPropertyRowMapper.newInstance(Product.class);
	private RowMapper<ProductVo> dtoRowMapper = BeanPropertyRowMapper.newInstance(ProductVo.class);
	private RowMapper<ProductDetailVo> detailRowMapper = BeanPropertyRowMapper.newInstance(ProductDetailVo.class);
	private RowMapper<FileDomain> fileRowMapper = BeanPropertyRowMapper.newInstance(FileDomain.class);
	private RowMapper<DisplayInfoVo> placeInfoRowMapper = BeanPropertyRowMapper.newInstance(DisplayInfoVo.class);
	private RowMapper<UserCommentVo> userCommentRowMapper = BeanPropertyRowMapper.newInstance(UserCommentVo.class);
	private RowMapper<CommentsSummaryVo> userCommentVoRowMapper = BeanPropertyRowMapper.newInstance(CommentsSummaryVo.class);
	private RowMapper<ProductReservationInfoVo> reservationInfoRowMapper = BeanPropertyRowMapper.newInstance(ProductReservationInfoVo.class);
	
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
		try {
			return jdbc.queryForObject(ProductSqls.SELECT_BY_ID, params, rowMapper);
		} catch(EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	public int countAll() {
		Map<String, ?> params = Collections.emptyMap();
		return jdbc.queryForObject(ProductSqls.COUNT_ALL, params, Integer.class);
	}
	
	public List<ProductVo> selectLimitedByCategoryId(int categoryId, int offset) {
		Map<String, Integer> params = new HashMap<>();
		params.put("categoryId", categoryId);
		params.put("offset", offset);
		return jdbc.query(ProductJoinSqls.LIMITED_SELECT_BY_CATEGORY_ID, params, dtoRowMapper);
	}
	
	public int countByCategoryId(int categoryId) {
		Map<String, ?> params = Collections.singletonMap("categoryId", categoryId);
		return jdbc.queryForObject(ProductSqls.COUNT_BY_CATEGORY_ID, params, Integer.class);
	}
	
	public ProductDetailVo selectDetail(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ProductJoinSqls.SELECT_DETAIL, params, detailRowMapper);
	}
	
	public List<FileDomain> selectFiles(int id, int type) {
		Map<String, Integer> params = new HashMap<>();
		params.put("id", id);
		params.put("type", type);
		return jdbc.query(ProductJoinSqls.SELECT_FILES, params, fileRowMapper);
	}
	
	public DisplayInfoVo selectDisplayInfo(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ProductJoinSqls.SELECT_DISPLAY_INFO, params, placeInfoRowMapper);
	}
	
	public List<UserCommentVo> selectUserComment(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.query(UserCommentSqls.SELECT_BY_PRODUCT_ID, params, userCommentRowMapper);
	}
	
	public CommentsSummaryVo selectAvgCommentScore(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(UserCommentSqls.SELECT_AVG_SCORE_BY_PRODUCT_ID, params, userCommentVoRowMapper);
	}
	
	public ProductReservationInfoVo selectReservationInfo(int id) {
		Map<String, ?> params = Collections.singletonMap("id", id);
		return jdbc.queryForObject(ProductJoinSqls.SELECT_RESERVATION_INFO, params, reservationInfoRowMapper);
	}
}
