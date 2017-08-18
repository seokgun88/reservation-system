package com.yg.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yg.reservation.domain.ProductPrice;
import com.yg.reservation.service.ProductService;
import com.yg.reservation.service.ReviewService;
import com.yg.reservation.vo.ProductDetailVo;
import com.yg.reservation.vo.ProductReservationVo;
import com.yg.reservation.vo.ProductSummaryVo;
import com.yg.reservation.vo.ReviewVo;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {
	private ProductService productService;
	private ReviewService reviewService;

	@Autowired
	public ProductApiController(ProductService productService,
			ReviewService reviewService) {
		this.productService = productService;
		this.reviewService = reviewService;
	}

	@GetMapping("/promotion")
	public List<ProductSummaryVo> getPromotion() {
		return productService.getPromotion();
	}

	@GetMapping("/{id:[\\d]+}")
	public ProductDetailVo getDetail(@PathVariable int id) {
		return productService.getDetail(id);
	}

	@GetMapping("/{id:[\\d]+}/reviews")
	public List<ReviewVo> getReviews(@PathVariable int id,
			@RequestParam(required = false) Integer limit) {
		if (limit == null) {
			return null;
		}
		return reviewService.getLimitedByProductId(id, limit);
	}

	@GetMapping("/{id:[\\d]+}/reservation")
	public ProductReservationVo getReservation(@PathVariable int id) {
		return productService.getReservation(id);
	}

	@GetMapping("/{id:[\\d]+}/prices")
	public List<ProductPrice> getPrices(@PathVariable int id) {
		return productService.getPrices(id);
	}

}
