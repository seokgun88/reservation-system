package com.yg.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg.reservation.service.ProductService;
import com.yg.reservation.vo.ProductSummaryVo;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {
	private ProductService productService;

	@Autowired
	public ProductApiController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/promotions")
	public List<ProductSummaryVo> getPromotions() {
		return productService.getPromotions();
	}
}
