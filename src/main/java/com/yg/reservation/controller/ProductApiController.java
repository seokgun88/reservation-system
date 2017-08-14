package com.yg.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg.reservation.domain.ProductPrice;
import com.yg.reservation.service.ProductService;
import com.yg.reservation.vo.ProductReservationVo;
import com.yg.reservation.vo.ProductSummaryVo;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {
	private ProductService productService;

	@Autowired
	public ProductApiController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/promotion")
	public List<ProductSummaryVo> getPromotion() {
		return productService.getPromotion();
	}
	
	@GetMapping("/{id:[\\d]+}/reservation")
	public ProductReservationVo getReservation(@PathVariable int id){
		return productService.getReservation(id);
	}
	 
	@GetMapping("/{id:[\\d]+}/prices")
	public List<ProductPrice> getPrices(@PathVariable int id){
		return productService.getPrices(id);
	}

}
