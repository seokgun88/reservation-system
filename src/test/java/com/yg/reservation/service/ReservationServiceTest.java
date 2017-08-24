package com.yg.reservation.service;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.config.RootApplicationContextConfig;
import com.yg.reservation.domain.Product;
import com.yg.reservation.domain.Reservation;
import com.yg.reservation.domain.User;
import com.yg.reservation.repository.ProductRepository;
import com.yg.reservation.repository.UserRepository;
import com.yg.reservation.vo.MyReservationVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ReservationServiceTest {
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	private Logger logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	@Test
	public void shouldAdd() {
		Product product = productRepository.findOne(1);
		User user = userRepository.findOne(1);
		Reservation reservation = new Reservation();
		reservation.setProduct(product);
		reservation.setChildTicketCount(1);
		reservation.setReservationDate(new Date());
		reservation.setTotalPrice(5000);
		reservation.setReservationType(1);
		reservation.setUser(user);
		reservation.setReservationTel("010-0000-0000");
		reservation.setReservationEmail("dkanakf@naver.com");
		
		reservationService.add(reservation, 1);
	}
	
	@Test
	public void shouldGet() {
		MyReservationVo reservation = reservationService.getByUserId(4);
		logger.info(reservation.toString());
	}

}
