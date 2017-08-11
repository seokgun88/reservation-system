package com.yg.reservation.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.config.RootApplicationContextConfig;
import com.yg.reservation.domain.NaverProfile;
import com.yg.reservation.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class UserServiceTest {
	@Autowired
	private UserService userService;
	private static Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Test
	public void shouldAdd() {
		NaverProfile profile = new NaverProfile();
		// 기존 가입한 사람
		profile.setId("1");
		profile.setAge("25");
		profile.setBirthday("19900925");
		profile.setEmail("ddd@dddd.com");
		profile.setGender("남");
		profile.setName("이름");
		profile.setNickname("별명");
		User user = userService.add(profile);
		assertThat(user, is(notNullValue()));
		logger.info(user.toString());
		
		// 새로 가입한 사람
		profile.setId("2");
		profile.setAge("25");
		profile.setBirthday("19900925");
		profile.setEmail("ddd@dddd.com");
		profile.setGender("남");
		profile.setName("이름");
		profile.setNickname("별명");
		user = userService.add(profile);
		assertThat(user, is(notNullValue()));
		logger.info(user.toString());
	}

}
