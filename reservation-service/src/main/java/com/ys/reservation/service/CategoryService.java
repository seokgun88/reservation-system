package com.ys.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.CategoryDao;
import com.ys.reservation.domain.Category;

@Service //여기서 변수 검증해야함
public class CategoryService {
	
	@Autowired
	private CategoryDao categoryDao;
	
	public List<Category> getAll(){
		return categoryDao.selectAll();
	}
	
	public int create(Category category){
		if(category != null && category.getName() != null && ! category.getName().trim().isEmpty()){ 
			return categoryDao.insert(category);
		}
		return -1;
	}
	
	public int remove(int id){
		return categoryDao.delete(id);
	}
	
	public int update(Category category){
		return categoryDao.update(category);
	}
	
}
