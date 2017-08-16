package com.yg.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.dao.ImageDao;
import com.yg.reservation.dao.ProductDao;
import com.yg.reservation.dao.ProductPriceDao;
import com.yg.reservation.domain.ProductPrice;
import com.yg.reservation.vo.ProductDetailVo;
import com.yg.reservation.vo.ProductReservationVo;
import com.yg.reservation.vo.ProductSummaryVo;

@Service
public class ProductService {
	private ProductDao productDao;
	private ProductPriceDao productPriceDao;
	private ImageDao imageDao;

	@Autowired
	public ProductService(ProductDao productDao, ImageDao imageDao, ProductPriceDao productPriceDao) {
		this.productDao = productDao;
		this.imageDao = imageDao;
		this.productPriceDao = productPriceDao;
	}

	public List<ProductSummaryVo> getPromotion() {
		return productDao.selectPromotion();
	}

	public String getName(int id) {
		return productDao.selectName(id);
	}

	public List<ProductSummaryVo> getSummaries(int categoryId, int page) {
		if (categoryId < 0 || page < 1) {
			return null;
		}
		int offset = (page - 1) * 10;
		List<ProductSummaryVo> productSummaryVos = null;
		if (categoryId == 0) {
			productSummaryVos = productDao.selectLimitedWithOffset(offset);
		} else {
			productSummaryVos = productDao.selectLimitedWithOffsetByCategoryId(categoryId, offset);
		}
		return productSummaryVos;
	}

	@Transactional(readOnly=true)
	public ProductDetailVo getDetail(int id) {
		if(id < 1) {
			return null;
		}
		ProductDetailVo productDetailVo = productDao.selectDetail(id);
		if(productDetailVo == null) {
			return null;
		}
		List<Integer> images = imageDao.selectIdsByProductId(id);
		productDetailVo.setImages(images);
		return productDetailVo;
	}

	public ProductReservationVo getReservation(int id) {
		return productDao.selectReservation(id);
	}

	public List<ProductPrice> getPrices(int id) {
		return productPriceDao.selectPrices(id);
	}
}
