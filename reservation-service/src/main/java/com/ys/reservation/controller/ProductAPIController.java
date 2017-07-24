package com.ys.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.Price;
import com.ys.reservation.domain.Product;
import com.ys.reservation.service.ProductService;
import com.ys.reservation.vo.CommentsSummaryVo;
import com.ys.reservation.vo.DisplayInfoVo;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductReservationInfoVo;
import com.ys.reservation.vo.ProductVo;
import com.ys.reservation.vo.UserCommentVo;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {
	private ProductService productService;

	@Autowired
	public ProductAPIController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<ProductVo> getByPage(@RequestParam(value="page", required=false) Integer page) {
		if(page == null) {
			return productService.getAll();			
		}
		return productService.getWithLimit(page);
	}

	@GetMapping("/{id:[\\d]+}")
	public Product getById(@PathVariable int id) {
		return productService.getById(id);
	}
	
	@GetMapping("/count")
	public int getCount() {
		return productService.getCount();
	}
	
	@GetMapping("/{id:[\\d]+}/detail")
	public ProductDetailVo getDetailById(@PathVariable int id) {
		return productService.getDetailById(id);
	}
	
	@GetMapping("/{id:[\\d]+}/files")
	public List<Integer> getFileIds(@PathVariable int id, @RequestParam int type, HttpServletResponse response) {
		List<Integer> fileIds = productService.getFileIds(id, type);
		return fileIds;
	}
	
	@GetMapping("/{id:[\\d]+}/displayInfo")
	public DisplayInfoVo getDisplayInfo(@PathVariable int id) {
		return productService.getDisplayInfo(id);
	}

	@GetMapping("/{id:[\\d]+}/mainImage")
	public int getMainImageId(@PathVariable int id) {
		return productService.getMainImageId(id);
	}
	
	@GetMapping("/{id:[\\d]+}/subImage")
	public int getSubImageId(@PathVariable int id) {
		return productService.getSubImageId(id);
	}
	
	@GetMapping("/{id:[\\d]+}/comments")
	public List<UserCommentVo> getUserComment(@PathVariable int id) {
		return productService.getUserComment(id);
	}
	
	@GetMapping("/{id:[\\d]+}/comments/summary")
	public CommentsSummaryVo getAvgCommentScore(@PathVariable int id) {
		return productService.getAvgCommentScore(id);
	}

	@GetMapping("/{id:[\\d]+}/reservationInfo")
	public ProductReservationInfoVo getReservationInfo(@PathVariable int id) {
		return productService.getReservationInfo(id);
	}
	
	@GetMapping("/{id:[\\d]+}/prices")
	public List<Price> getPrices(@PathVariable int id) {
		return productService.getPrices(id);
	}
}
