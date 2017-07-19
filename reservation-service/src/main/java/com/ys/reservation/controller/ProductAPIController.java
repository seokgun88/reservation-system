package com.ys.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.Product;
import com.ys.reservation.service.ProductService;
import com.ys.reservation.vo.DisplayInfoVo;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductVo;
import com.ys.reservation.vo.UserCommentVo;
import com.ys.reservation.vo.CommentsSummaryVo;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<ProductVo> getAll() {
		return productService.getAll();
	}

	@GetMapping("/pages/{page}")
	public List<ProductVo> getByPage(@PathVariable int page) {
		return productService.getWithLimit(page);
	}
	
	@GetMapping("/{id}")
	public Product getById(@PathVariable int id) {
		return productService.getById(id);
	}
	
	@GetMapping("/count")
	public int getCount() {
		return productService.getCount();
	}
	
	@GetMapping("/{id}/detail")
	public ProductDetailVo getDetailById(@PathVariable int id) {
		return productService.getDetailById(id);
	}
	
	@GetMapping("/{id}/files")
	public List<Integer> getProductFileIds(@PathVariable int id, @RequestParam int type, HttpServletResponse response) {
		List<Integer> fileIds = productService.getFileIds(id, type);
		return fileIds;
	}
	
	@GetMapping("/{id}/displayInfo")
	public DisplayInfoVo getDisplayInfo(@PathVariable int id) {
		return productService.getDisplayInfo(id);
	}
	
	@GetMapping("/{id}/subImage")
	public int getSubImageId(@PathVariable int id) {
		return productService.getSubImageId(id);
	}
	
	@GetMapping("{id}/comments")
	public List<UserCommentVo> getUserComment(@PathVariable int id) {
		return productService.getUserComment(id);
	}
	
	@GetMapping("{id}/comments/summary")
	public CommentsSummaryVo getAvgCommentScore(@PathVariable int id) {
		return productService.getAvgCommentScore(id);
	}
}