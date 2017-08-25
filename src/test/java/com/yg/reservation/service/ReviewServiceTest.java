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
import com.yg.reservation.domain.User;
import com.yg.reservation.repository.ProductRepository;
import com.yg.reservation.repository.UserRepository;
import com.yg.reservation.vo.ReviewVo;
import com.yg.reservation.vo.ReviewWriteVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ReviewServiceTest {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	private Logger logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	@Test
	public void shouldGetLimitedByProductId() {
		List<ReviewVo> reviews = reviewService.getLimitedByProductId(1, 3);
		assertThat(reviews, is(notNullValue()));
		reviews.stream().forEach(r -> {
			logger.info(r.toString());
		});
	}

	@Test
	public void shouldAdd() {
		User user = userRepository.findOne(1);
		ReviewWriteVo reviewWriteVo = new ReviewWriteVo();
		Review review = new Review();
		review.setProduct(productRepository.findOne(1));
		review.setUser(user);
		review.setScore(30);
		review.setReview("good!");
		reviewWriteVo.setReview(review);
		reviewService.addWithImageIds(reviewWriteVo);
	}
}
