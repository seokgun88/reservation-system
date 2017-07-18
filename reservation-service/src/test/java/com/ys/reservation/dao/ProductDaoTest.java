package com.ys.reservation.dao;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ys.reservation.config.RootApplicationContextConfig;
import com.ys.reservation.domain.Product;
import com.ys.reservation.vo.ProductVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ProductDaoTest {
	@Autowired
	ProductDao productDao;

	@Test
	public void shouldSelectAllLimit10() {
		List<ProductVo> productList = productDao.limitedSelect(0);
		productList.forEach( item -> {
			System.out.println(item);
		});
	}
	
	@Test
	public void shouldSelectById() {
		List<ProductVo> productList = productDao.selectAll();
		productList.forEach( item -> {
			System.out.println(item);
			Product product = productDao.select(item.getId());
			assertThat(product.getId(), is(item.getId()));
		});
	}

}
