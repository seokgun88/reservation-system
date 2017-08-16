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
import com.yg.reservation.domain.Reservation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ReservationServiceTest {
	
	@Autowired
	ReservationService reservationService;
	private Logger logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	@Test
	public void shouldAdd() {
		Reservation reservation = new Reservation();
		reservation.setProductId(1);
		reservation.setChildTicketCount(1);
		reservation.setReservationDate(new Date());
		reservation.setTotalPrice(5000);
		reservation.setReservationType(1);
		reservation.setUserId(1);
		reservation.setReservationTel("010-0000-0000");
		reservation.setReservationEmail("dkanakf@naver.com");
		
		reservationService.add(reservation);
	}

}
