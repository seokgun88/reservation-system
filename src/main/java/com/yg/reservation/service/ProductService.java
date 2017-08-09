package com.yg.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.dao.ProductDao;
import com.yg.reservation.vo.ProductSummaryVo;

@Service
public class ProductService {
	private ProductDao productDao;

	@Autowired
	public ProductService(ProductDao productDao) {
		this.productDao = productDao;
	}

	public List<ProductSummaryVo> getPromotions() {
		return productDao.selectPromotions();
	}

	public List<ProductSummaryVo> getSummaries(int categoryId, int page) {
		if (categoryId < 0 || page < 1) {
			return null;
		}
		int offset = (page - 1) * 10;
		if (categoryId == 0) {
			return productDao.selectProducts(offset);
		}
		return productDao.selectProducts(categoryId, offset);
	}
}
