package com.yg.reservation.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
import com.yg.reservation.domain.User;
import com.yg.reservation.repository.ReservationRepository;
import com.yg.reservation.repository.UserRepository;
import com.yg.reservation.vo.MyReservationVo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootApplicationContextConfig.class)
@Transactional
public class ReservationServiceTest {
	@Autowired
	private ReservationService reservationService;
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private UserRepository userRepository;
	private Logger logger = LoggerFactory.getLogger(ReviewServiceTest.class);

	@Test
	public void shouldAddAndGet() {
		User user = userRepository.findOne(1);
		Reservation reservation = new Reservation();
		reservation.setChildTicketCount(1);
		reservation.setReservationDate(new Date());
		reservation.setTotalPrice(5000);
		reservation.setReservationType(1);
		reservation.setUser(user);
		reservation.setReservationName("테스트이름");
		reservation.setReservationTel("010-0000-0000");
		reservation.setReservationEmail("dkanakf@naver.com");
		
		reservationService.add(reservation, 1);
		
		MyReservationVo myReservation = reservationService.getByUserId(1);
		logger.info(myReservation.toString());
	}
	
	@Test
	public void shouldModifyReservationType() {
		reservationService.modifyReservationType(3, 3);
		
		assertThat(reservationRepository.findOne(3).getReservationType(), is(3));
		logger.info(reservationRepository.findOne(3).toString());
	}

}
