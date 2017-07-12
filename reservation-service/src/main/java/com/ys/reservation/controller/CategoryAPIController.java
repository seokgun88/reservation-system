package com.ys.reservation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.Category;
import com.ys.reservation.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPIController {
	@Autowired
	private CategoryService categoryService;

	@GetMapping
	public List<Category> getAll() {
		return categoryService.getAll();
	}

	@DeleteMapping("/{id:[\\d]+}") // 숫자 받는 정규식
	public void delete(@PathVariable Integer id) {
		categoryService.remove(id);
	}

	@PutMapping("/{id}")
	public void update(@PathVariable Integer id, @RequestBody Category category) {
		category.setId(id);
		categoryService.update(category);
	}

}
