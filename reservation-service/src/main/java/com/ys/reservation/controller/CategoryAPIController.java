package com.ys.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.Category;
import com.ys.reservation.service.CategoryService;
import com.ys.reservation.service.ProductService;
import com.ys.reservation.vo.ProductVo;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPIController {
	private CategoryService categoryService;
	private ProductService productService;

	@Autowired
	public CategoryAPIController(CategoryService categoryService, ProductService productService) {
		this.categoryService = categoryService;
		this.productService = productService;
	}

	@GetMapping
	public List<Category> getAll() {
		return categoryService.getAll();
	}

	@DeleteMapping("/{id:[\\d]+}") // 숫자 받는 정규식
	public void remove(@PathVariable int id) {
		categoryService.remove(id);
	}

	@PutMapping("/{id:[\\d]+}")
	public void update(@PathVariable int id, @RequestBody Category category) {
		category.setId(id);
		categoryService.update(category);
	}
	
	@GetMapping("{categoryId:[\\d]+}/products")
	public List<ProductVo> getLimitedProductsByPage(@PathVariable int categoryId, @RequestParam int page) {
		return productService.getWithLimitByCategoryId(categoryId, page);
	}
	
	@GetMapping("{categoryId:[\\d]+}/products/count")
	public int getProductsCount(@PathVariable int categoryId) {
		return productService.getCountByCategoryId(categoryId);
	}
}
