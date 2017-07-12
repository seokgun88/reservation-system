package com.ys.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.Category;
import com.ys.reservation.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryAPIController {
	@Autowired
	private CategoryService categoryService;

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
