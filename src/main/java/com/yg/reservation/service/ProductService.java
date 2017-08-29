package com.yg.reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.domain.Product;
import com.yg.reservation.domain.ProductDetail;
import com.yg.reservation.domain.ProductDisplay;
import com.yg.reservation.domain.ProductImage;
import com.yg.reservation.domain.ProductPrice;
import com.yg.reservation.repository.ProductPriceRepository;
import com.yg.reservation.repository.ProductRepository;
import com.yg.reservation.vo.ProductDetailVo;
import com.yg.reservation.vo.ProductReservationVo;
import com.yg.reservation.vo.ProductSummaryVo;

@Service
public class ProductService {
	private ProductRepository productRepository;
	private ProductPriceRepository productPriceRepository;

	@Autowired
	public ProductService(ProductRepository productRepository,
			ProductPriceRepository productPriceRepository) {
		this.productRepository = productRepository;
		this.productPriceRepository = productPriceRepository;
	}

	public List<ProductSummaryVo> getPromotion() {
		List<Product> products = productRepository
				.findTop5ByOrderByIdDesc();
		List<ProductSummaryVo> productSummaryVos = products.stream().map(p -> {
			ProductDisplay productDisplay = p.getProductDisplay();
			int mainImageId = p.getProductImages().stream()
					.filter(pi -> pi.getType() == 0).findFirst()
					.orElse(new ProductImage()).getImage().getId();

			ProductSummaryVo productSummaryVo = new ProductSummaryVo();
			productSummaryVo.setId(p.getId());
			productSummaryVo.setName(p.getName());
			productSummaryVo.setDescription(p.getDescription());
			productSummaryVo.setPlaceName(productDisplay.getPlaceName());
			productSummaryVo.setMainImageId(mainImageId);

			return productSummaryVo;
		}).collect(Collectors.toList());

		return productSummaryVos;
	}

	public String getName(int id) {
		if (id < 1) {
			return null;
		}
		return productRepository.findOne(id).getName();
	}

	public List<ProductSummaryVo> getSummaries(int categoryId, int page) {
		if (categoryId < 0 || page < 1) {
			return null;
		}

		List<Product> products = null;
		if (categoryId == 0) {
			products = productRepository.findAll(new PageRequest(page-1, 10)).getContent();
		} else {
			products = productRepository.findByCategoryId(categoryId,
					new PageRequest(page-1, 10));
		}

		List<ProductSummaryVo> productSummaryVos = products.stream().map(p -> {
			ProductDisplay productDisplay = p.getProductDisplay();
			int mainImageId = p.getProductImages().stream()
					.filter(pi -> pi.getType() == 0).findFirst()
					.orElse(new ProductImage()).getImage().getId();

			ProductSummaryVo productSummaryVo = new ProductSummaryVo();
			productSummaryVo.setId(p.getId());
			productSummaryVo.setName(p.getName());
			productSummaryVo.setDescription(p.getDescription());
			productSummaryVo.setPlaceName(productDisplay.getPlaceName());
			productSummaryVo.setMainImageId(mainImageId);

			return productSummaryVo;
		}).collect(Collectors.toList());

		return productSummaryVos;
	}

	@Transactional(readOnly = true)
	public ProductDetailVo getDetail(int id) {
		if (id < 1) {
			return null;
		}
		Product product = productRepository.findOne(id);
		ProductDetail productDetail = product.getProductDetail();
		ProductDisplay productDisplay = product.getProductDisplay();
		List<Integer> imageIds = product.getProductImages().stream()
				.filter(pi -> (pi.getType() == 0 || pi.getType() == 1))
				.map(pi -> pi.getImage().getId()).collect(Collectors.toList());
		int subImageId = product.getProductImages().stream()
				.filter(pi -> pi.getType() == 2).findFirst()
				.orElse(new ProductImage()).getImage().getId();

		ProductDetailVo productDetailVo = new ProductDetailVo();
		productDetailVo.setName(product.getName());
		productDetailVo.setDescription(product.getDescription());
		productDetailVo.setEvent(product.getEvent());
		productDetailVo.setSalesEnd(product.getSalesEnd());
		productDetailVo.setSalesFlag(product.getSalesFlag());
		productDetailVo.setContent(productDetail.getContent());
		productDetailVo.setImages(imageIds);
		productDetailVo.setSubImage(subImageId);
		productDetailVo.setPlaceName(productDisplay.getPlaceName());
		productDetailVo.setPlaceLot(productDisplay.getPlaceLot());
		productDetailVo.setPlaceStreet(productDisplay.getPlaceStreet());
		productDetailVo.setTel(productDisplay.getTel());
		productDetailVo.setHomepage(productDisplay.getHomepage());
		productDetailVo.setEmail(productDisplay.getEmail());
		productDetailVo.setReviewCount(product.getReviewCount());
		productDetailVo.setReviewTotalScore(product.getReviewTotalScore());

		return productDetailVo;
	}

	public ProductReservationVo getReservation(int id) {
		if (id < 1) {
			return null;
		}
		Product product = productRepository.findOne(id);
		ProductDisplay productDisplay = product.getProductDisplay();
		List<ProductImage> productImages = product.getProductImages();
		int mainImageId = productImages.stream().filter(pi -> pi.getType() == 0)
				.findFirst().orElse(new ProductImage()).getImage().getId();

		ProductReservationVo productReservationVo = new ProductReservationVo();
		productReservationVo.setName(product.getName());
		productReservationVo.setPlaceName(productDisplay.getPlaceName());
		productReservationVo.setDisplayStart(productDisplay.getDisplayStart());
		productReservationVo.setDisplayEnd(productDisplay.getDisplayEnd());
		productReservationVo
				.setObservationTime(productDisplay.getObservationTime());
		productReservationVo.setMainImageId(mainImageId);

		return productReservationVo;
	}

	public List<ProductPrice> getPrices(int id) {
		if (id < 1) {
			return null;
		}
		return productPriceRepository.findByProductId(id);
	}
}
