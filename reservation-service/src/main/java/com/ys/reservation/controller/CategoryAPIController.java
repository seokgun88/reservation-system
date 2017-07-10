package com.ys.reservation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String delete(@PathVariable Integer id) {
		categoryService.remove(id);
		return "redirect:/category/admin";
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody Map<String, Object> payload) {
		String name = (String)payload.get("name");
		if(name != null && name.trim().length() != 0){
			Category category = new Category();
			category.setId(id);
			category.setName(name);
			categoryService.update(category);			
		}
	}
	
}
