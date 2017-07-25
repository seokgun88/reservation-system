package com.ys.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ys.reservation.domain.Category;
import com.ys.reservation.service.CategoryService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private CategoryService categoryService;

	@Autowired
	public AdminController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	// 카테고리 관리페이지 요청시 전체 카테고리를 이름순으로 정렬한 리스트 반환
	@GetMapping("/categories")
	public String categoryAdmin(Model model) {
		List<Category> categories = categoryService.getAll();
		model.addAttribute("categories", categories);
		return "categoryAdmin";
	}

	// post 방식으로 category 생성 요청이 들어오면 생성 후 관리페이지로 리다이렉트
	@PostMapping("/categories")
	public String create(@ModelAttribute Category category) {
		categoryService.create(category);
		return "redirect:/admin/categories";
	}
}
