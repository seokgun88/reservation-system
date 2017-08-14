package com.yg.reservation.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
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
import com.yg.reservation.domain.Review;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ReviewServiceTest {
	@Autowired
	private ReviewService reviewService;
	private Logger Logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	@Test
	public void shouldGetLimitedByProductId() {
		List<Review> reviews = reviewService.getLimitedByProductId(1, 3);
		assertThat(reviews, is(notNullValue()));
		reviews.stream().forEach(r -> {
			Logger.info(r.toString());
		});
	}

}
