package com.ys.reservation.dao;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ys.reservation.config.RootApplicationContextConfig;
import com.ys.reservation.domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class CategoryDaoTest {
	@Autowired
	CategoryDao categoryDao;
	private static final Logger log = LoggerFactory.getLogger(ProductDaoTest.class);
	
	@Test
	public void shouldInsert() {
		Category category = new Category();
		category.setName("새 카테고리");
		Integer categoryPk = categoryDao.insert(category);
		log.info("{}", categoryPk);
	}
	
	@Test
	public void shouldSelect() {		
		List<Category> categoryList = categoryDao.selectAll();
		categoryList.forEach( item -> {
			log.info("{}", item);
		});
	}
	
	@Test
	public void shouldInsertAndDelete() {
		Category category = new Category();
		category.setName("새 카테고리");
		Integer categoryPk = categoryDao.insert(category);
		
		int deletedItems = categoryDao.delete(categoryPk);
		
		assertThat(deletedItems, is(1));
	}
	
	@Test
	public void shouldInsertAndUpdate() {
		Category category = new Category();
		category.setName("새 카테고리");
		Integer categoryPk = categoryDao.insert(category);
		
		Category category2 = new Category();
		category2.setId(categoryPk);
		category2.setName("업데이트");
		int deletedItems = categoryDao.update(category2);
		
		assertThat(deletedItems, is(1));
	}
	
}
