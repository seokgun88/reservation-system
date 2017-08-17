package com.yg.reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.dao.ReservationDao;
import com.yg.reservation.domain.Reservation;
import com.yg.reservation.vo.MyReservationVo;
import com.yg.reservation.vo.ReservationVo;

@Service
public class ReservationService {
	ReservationDao reservationDao;

	@Autowired
	public ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	public boolean add(Reservation reservation) {
		if (!reservation.hasRequiredValues()) {
			return false;
		}
		reservationDao.insert(reservation);
		return true;
	}

	public MyReservationVo getMy(int userId) {
		MyReservationVo myReservation = new MyReservationVo();
		List<ReservationVo> reservations = reservationDao.selectMy(userId);
		if (reservations == null) {
			return null;
		}

		myReservation.setReservations(reservations.stream().collect(
				Collectors.groupingBy(ReservationVo::getReservationType)));
		myReservation.setTypeCounts(reservations.stream().collect(Collectors
				.groupingBy(ReservationVo::getReservationType, Collectors.counting())));

		return myReservation;
	}

}
