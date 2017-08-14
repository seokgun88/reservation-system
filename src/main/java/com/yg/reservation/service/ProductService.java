package com.yg.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.dao.ImageDao;
import com.yg.reservation.dao.ProductDao;
import com.yg.reservation.domain.ProductPrice;
import com.yg.reservation.vo.ProductDetailVo;
import com.yg.reservation.vo.ProductReservationVo;
import com.yg.reservation.vo.ProductSummaryVo;

@Service
public class ProductService {
	private ProductDao productDao;
	private ImageDao imageDao;

	@Autowired
	public ProductService(ProductDao productDao, ImageDao imageDao) {
		this.productDao = productDao;
		this.imageDao = imageDao;
	}

	public List<ProductSummaryVo> getPromotion() {
		return productDao.selectPromotion();
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
		List<Integer> images = imageDao.selectByProductId(id);
		productDetailVo.setImages(images);
		return productDetailVo;
	}

	public ProductReservationVo getReservation(int id) {
		return productDao.selectReservation(id);
	}

	public List<ProductPrice> getPrices(int id) {
		return productDao.selectPrices(id);
	}
}
