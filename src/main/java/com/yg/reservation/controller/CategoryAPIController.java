package com.yg.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yg.reservation.domain.Category;
import com.yg.reservation.service.CategoryService;
import com.yg.reservation.service.ProductService;
import com.yg.reservation.vo.ProductSummaryVo;

@Controller
@RequestMapping("/api/categories")
public class CategoryAPIController {
	private CategoryService categoryService;
	private ProductService productService;

	@Autowired
	public CategoryAPIController(CategoryService categoryService,
			ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@GetMapping
	public List<Category> getAll() {
		return categoryService.getAll();
	}

	@GetMapping("/{categoryId}/products")
	public List<ProductSummaryVo> getProducts(@PathVariable int categoryId,
			@RequestParam int page) {
		return productService.getSummaries(categoryId, page);
	}
}
