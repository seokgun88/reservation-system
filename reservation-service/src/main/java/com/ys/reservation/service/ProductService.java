package com.ys.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.FileDao;
import com.ys.reservation.dao.ProductDao;
import com.ys.reservation.domain.FileDomain;
import com.ys.reservation.domain.Product;
import com.ys.reservation.vo.DisplayInfoVo;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductVo;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	@Autowired
	private FileDao fileDao;
	
	public List<ProductVo> getAllProducts() {
		return productDao.selectAll();
	}
	
	public List<ProductVo> limitedGet(int page) {
		int offset = 10 * (page-1);
		return productDao.limitedSelect(offset);
	}
	
	public Product getById(int id) {
		return productDao.select(id);
	}
	
	public int getCount() {
		return productDao.countAll();
	}
	
//	public List<Product> getProductsByCategory(int categoryId) {
//		return productDao.selectByCategory(categoryId);
//	}
	
	public List<ProductVo> limitedGetByCategoryId(int categoryId, int page) {
		int offset = 10 * (page-1);
		return productDao.limitedSelectByCategoryId(categoryId, offset);
	}
	
	public int getCountByCategoryId(int categoryId) {
		return productDao.countByCategoryId(categoryId);
	}
	
	public ProductDetailVo getDetailById(int id) {
		return productDao.selectDetail(id);
	}
	
	public List<Integer> getFileIds(int id, int type) {
		List<Integer> ids = new ArrayList<>();
		for(FileDomain fileDomain : productDao.selectFiles(id, type)) {
			ids.add(fileDomain.getId());
		}
		return ids;
	}
	
	public DisplayInfoVo getDisplayInfo(int id){ 
		return productDao.selectDisplayInfo(id);
	}
	
	public int getSubImageId(int id) {
		return fileDao.selectSubImage(id).getId();
	}
}
