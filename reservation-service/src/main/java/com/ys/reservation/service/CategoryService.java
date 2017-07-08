package com.ys.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.CategoryDao;
import com.ys.reservation.domain.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	public List<Category> getAllCategory(){
		return categoryDao.selectAll();
	}
	
	public Integer create(Category category){
		return categoryDao.insert(category);
	}
	
	public int remove(Integer id){
		return categoryDao.delete(id);
	}
	
	public int update(Category category){
		return categoryDao.update(category);
	}
	
}
