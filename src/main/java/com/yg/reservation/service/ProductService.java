package com.yg.reservation.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.dao.ImageDao;
import com.yg.reservation.dao.ProductDao;
import com.yg.reservation.domain.ProductPrice;
import com.yg.reservation.vo.MainImageVo;
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
		return mainImageMapper(productDao.selectPromotion());
	}

	@Transactional
	public List<ProductSummaryVo> getSummaries(int categoryId, int page) {
		if (categoryId < 0 || page < 1) {
			return null;
		}
		int offset = (page - 1) * 10;
		List<ProductSummaryVo> productSummaryVos = null;
		if (categoryId == 0) {
			productSummaryVos = productDao.selectProducts(offset);
		} else {
			productSummaryVos = productDao.selectProducts(categoryId, offset);
		}
		mainImageMapper(productSummaryVos);
		return productSummaryVos;
	}

	private List<ProductSummaryVo> mainImageMapper(
			List<ProductSummaryVo> productSummaryVos) {
		List<Integer> productIds = productSummaryVos.stream().map(
				ProductSummaryVo::getId).collect(Collectors.toList());

		List<MainImageVo> mainImageVos = imageDao
				.selectMainImageByProductId(productIds);
		
		Map<Integer, Integer> productIdToMainImageId = mainImageVos.stream()
				.collect(Collectors.toMap(MainImageVo::getProductId,
						MainImageVo::getMainImageId));

		for (ProductSummaryVo productSummary : productSummaryVos) {
			productSummary.setMainImageId(productIdToMainImageId
					.getOrDefault(productSummary.getId(), 0));
		}
		return productSummaryVos;
	}

	public ProductReservationVo getReservation(int id) {
		return productDao.selectReservation(id);
	}

	public List<ProductPrice> getPrices(int id) {
		return productDao.selectPrices(id);
	}
}
