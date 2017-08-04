package com.ys.reservation.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.PriceDao;
import com.ys.reservation.dao.ReservationDao;
import com.ys.reservation.domain.Price;
import com.ys.reservation.domain.Reservation;
import com.ys.reservation.vo.MyReservationVo;
import com.ys.reservation.vo.ReservationVo;

@Service
public class ReservationService {
	private ReservationDao reservationDao;
	private PriceDao priceDao;

	@Autowired
	public ReservationService(ReservationDao reservationDao, PriceDao priceDao) {
		this.reservationDao = reservationDao;
		this.priceDao = priceDao;
	}
	
	public Reservation create(Reservation reservation) {
		System.out.println(reservation.hasRequiredFields());
		if(reservation == null || !reservation.hasRequiredFields()) {
			return null;
		}
		Reservation ret = new Reservation();
		ret.setId(reservationDao.insert(reservation));
		return ret;
	}

	public MyReservationVo getMyReservation(int userId) {
		MyReservationVo myReservation = new MyReservationVo(0, 0, 0, 0, null);
		
		List<ReservationVo> reservations = reservationDao.selectReservations(userId);
		if (reservations == null || reservations.size() == 0) {
			return myReservation;
		}
		
		List<Integer> ids = reservations.stream().map(r -> r.getProductId())
				.distinct().collect(Collectors.toList());
		List<Price> priceList = priceDao.selectByProductIds(ids);
		Map<Integer, Map<Integer, Integer>> productIdToPrice = priceList.stream()
				.collect(Collectors.groupingBy(Price::getProductId,
						Collectors.toMap(Price::getPriceType, Price::getPrice)
				));
		
		// 0: total, 1: requested, 2: confirmed, 3: completed, 4: canceled
		int[] reservationCounts = new int[5];
		
		for(ReservationVo reservation : reservations) {
			reservationCounts[0]++;
			reservationCounts[reservation.getReservationType()]++;
			Map<Integer, Integer> typeToPrice = productIdToPrice.get(reservation.getProductId());
			int generalPrice = typeToPrice.getOrDefault(1, 0) * reservation.getGeneralTicketCount();
			int youthPrice = typeToPrice.getOrDefault(2, 0) * reservation.getYouthTicketCount();
			int childPrice = typeToPrice.getOrDefault(3, 0) * reservation.getChildTicketCount();
			reservation.setTotalPrice(generalPrice, youthPrice, childPrice);
		}
		
		Map<Integer, List<ReservationVo>> typeToReservation = reservations.stream()
				.collect(Collectors.groupingBy(ReservationVo::getReservationType));
		
		myReservation.setScheduledReservationCount(reservationCounts[1] + reservationCounts[2]);
		myReservation.setCompletedReservationCount(reservationCounts[3]);
		myReservation.setCanceledReservationCount(reservationCounts[4]);
		myReservation.setTotalReservationCount(reservationCounts[0]);
		myReservation.setReservations(typeToReservation);
		
		return myReservation;
	}
	
	public int update(int reservationId) {
		if(reservationId<=0) {
			return 0;
		}
		return reservationDao.update(reservationId);
	}
}
