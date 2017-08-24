package com.yg.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.domain.Category;
import com.yg.reservation.repository.CategoryRepository;

@Service
public class CategoryService {
	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getAll() {
		List<Category> categories = categoryRepository.findAll();
		if (categories == null || categories.isEmpty()) {
			return null;
		}
		int productsTotalCount = categories.stream()
				.mapToInt(Category::getProductCount).sum();
		Category totalCategory = new Category();
		totalCategory.setId(0);
		totalCategory.setName("전체");
		totalCategory.setProductCount(productsTotalCount);

		categories.add(0, totalCategory);

		return categories;
	}
}
