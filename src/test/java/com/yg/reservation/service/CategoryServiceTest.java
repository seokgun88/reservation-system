package com.yg.reservation.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.config.RootApplicationContextConfig;
import com.yg.reservation.domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class CategoryServiceTest {
	@Autowired
	private CategoryService categoryService;

	private static Logger logger = LoggerFactory
			.getLogger(CategoryServiceTest.class);

	@Test
	public void shouldGetAll() {
		List<Category> categoryList = categoryService.getAll();

		assertThat(categoryList, is(notNullValue()));

		categoryList.stream().forEach(category -> {
			logger.info(category.toString());
		});
	}

}
