package com.yg.reservation.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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

import com.yg.reservation.config.RootApplicationContextConfig;
import com.yg.reservation.vo.ProductSummaryVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ProductServiceTest {
	@Autowired
	private ProductService productService;

	private static Logger logger = LoggerFactory
			.getLogger(ProductServiceTest.class);

	@Test
	public void shouldGetPromotions() {
		List<ProductSummaryVo> productSummaryVos = productService
				.getPromotions();

		assertThat(productSummaryVos, is(notNullValue()));

		productSummaryVos.stream().forEach(promotion -> {
			logger.info(promotion.toString());
		});
	}

	@Test
	public void shouldGetSummaries() {
		List<ProductSummaryVo> productSummaryVos = productService
				.getSummaries(-1, -1);

		assertThat(productSummaryVos, is(nullValue()));

		productSummaryVos = productService.getSummaries(0, 1);

		assertThat(productSummaryVos, is(notNullValue()));

		productSummaryVos = productService.getSummaries(1, 1);

		assertThat(productSummaryVos, is(notNullValue()));

		productSummaryVos.stream().forEach(promotion -> {
			logger.info(promotion.toString());
		});
	}
}
