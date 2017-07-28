package com.ys.reservation.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ys.reservation.config.RootApplicationContextConfig;
import com.ys.reservation.vo.MyReservationVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ReservationServiceTest {
	@Autowired
	ReservationService reservationService;
	
	@Test
	public void test() {
		MyReservationVo myReservation = reservationService.getMyReservation(2);
		System.out.println(myReservation);
	}

}
