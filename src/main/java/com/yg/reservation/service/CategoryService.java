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

	public List<Category> getCategories() {
		return categoryDao.selectCategories();
	}

}
