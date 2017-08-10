package com.yg.reservation.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yg.reservation.config.RootApplicationContextConfig;
import com.yg.reservation.domain.Image;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
public class ImageServiceTest {
	@Autowired
	ImageService imageService;
	Logger logger = LoggerFactory.getLogger(ImageServiceTest.class);

	@Test
	public void shouldGet() {
		Image image = imageService.get(1);
		assertThat(image, is(notNullValue()));
		logger.info(image.toString());
	}

}
