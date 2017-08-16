package com.yg.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.dao.ReservationDao;
import com.yg.reservation.domain.Reservation;

@Service
public class ReservationService {
	ReservationDao reservationDao;

	@Autowired
	public ReservationService(ReservationDao reservationDao) {
		this.reservationDao = reservationDao;
	}

	public boolean add(Reservation reservation) {
		System.out.println(reservation.toString());
		if(!reservation.hasRequiredValues()) {
			return false;
		}
		reservationDao.insert(reservation);
		return true;
	}

}
