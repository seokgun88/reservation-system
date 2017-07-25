package com.ys.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.ReservationDao;
import com.ys.reservation.domain.ReservationInfo;

@Service
public class ReservationService {
	private ReservationDao reservationInfoDao;

	public ReservationDao getReservationInfoDao() {
		return reservationInfoDao;
	}

	@Autowired
	public ReservationService(ReservationDao reservationInfoDao) {
		this.reservationInfoDao = reservationInfoDao;
	}
	
	
	public ReservationInfo create(ReservationInfo reservationInfo) {
		if(reservationInfo == null 
				|| reservationInfo.getProductId() == 0
				|| reservationInfo.getUserId() == 0 
				|| reservationInfo.getReservationName() == null
				|| reservationInfo.getReservationName().trim().isEmpty()
				|| reservationInfo.getReservationTel() == null 
				|| reservationInfo.getReservationTel().trim().isEmpty()
				|| reservationInfo.getReservationEmail() == null
				|| reservationInfo.getReservationEmail().trim().isEmpty()
				|| reservationInfo.getReservationDate() == null) {
			return null;
		}
		ReservationInfo ret = new ReservationInfo();
		ret.setId(reservationInfoDao.insert(reservationInfo));
		return ret;
	}
}
