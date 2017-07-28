package com.ys.reservation.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.PriceDao;
import com.ys.reservation.dao.ReservationDao;
import com.ys.reservation.domain.Price;
import com.ys.reservation.domain.ReservationInfo;
import com.ys.reservation.vo.MyReservationVo;
import com.ys.reservation.vo.ReservationVo;

@Service
public class ReservationService {
	private ReservationDao reservationDao;
	private PriceDao priceDao;

	@Autowired
	public ReservationService(ReservationDao reservationDao, PriceDao priceDao) {
		super();
		this.reservationDao = reservationDao;
		this.priceDao = priceDao;
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
		ret.setId(reservationDao.insert(reservationInfo));
		return ret;
	}

	public MyReservationVo getMyReservation(int userId) {
		MyReservationVo myReservation = new MyReservationVo();
		List<ReservationVo> reservations = reservationDao.selectReservations(userId);
		List<Integer> ids = reservations.stream().map(r -> r.getProductId()).distinct().collect(Collectors.toList());
		List<Price> priceList = priceDao.selectByProductIds(ids);
		Map<Integer, Map<Integer, Integer>> productIdToPrice = priceList.stream()
				.collect(Collectors.groupingBy(Price::getProductId,
						Collectors.toMap(Price::getPriceType, Price::getPrice)
				));
		
		int totalReservationCount=0;
		int requestedReservationCount=0;
		int confirmedReservationCount=0;
		int completedReservationCount=0;
		int canceledReservationCount=0;
		
		for(ReservationVo reservation : reservations) {
			if(reservation.getReservationType()==1) {
				requestedReservationCount++;
			}
			else if(reservation.getReservationType()==2) {
				confirmedReservationCount++;
			}
			else if(reservation.getReservationType()==3) {
				completedReservationCount++;
			}
			else if(reservation.getReservationType()==4) {
				canceledReservationCount++;
			}
			Map<Integer, Integer> typeToPrice = productIdToPrice.get(reservation.getProductId());
			int generalPrice = typeToPrice.get(1) != null ? reservation.getGeneralTicketCount() * typeToPrice.get(1) : 0;
			int youthPrice = typeToPrice.get(2) != null ? reservation.getYouthTicketCount() * typeToPrice.get(2) : 0;
			int childPrice = typeToPrice.get(3) != null ? reservation.getChildTicketCount() * typeToPrice.get(3) : 0;
			reservation.setTotalPrice(generalPrice, youthPrice, childPrice);
		}
		
		Map<Integer, List<ReservationVo>> typeToReservation = reservations.stream().collect(Collectors.groupingBy(ReservationVo::getReservationType));
		
		totalReservationCount = requestedReservationCount + confirmedReservationCount
				+ completedReservationCount + canceledReservationCount;
		myReservation.setScheduledReservationCount(requestedReservationCount+confirmedReservationCount);
		myReservation.setCompletedReservationCount(completedReservationCount);
		myReservation.setCanceledReservationCount(canceledReservationCount);
		myReservation.setTotalReservationCount(totalReservationCount);
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
