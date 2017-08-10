package com.yg.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.dao.CategoryDao;
import com.yg.reservation.domain.Category;

@Service
public class CategoryService {
	private CategoryDao categoryDao;

	@Autowired
	public CategoryService(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public List<Category> getAll() {
		List<Category> categories = categoryDao.selectAll();
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
