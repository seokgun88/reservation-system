package com.yg.reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.domain.Product;
import com.yg.reservation.domain.Reservation;
import com.yg.reservation.repository.ProductRepository;
import com.yg.reservation.repository.ReservationRepository;
import com.yg.reservation.vo.MyReservationVo;

@Service
public class ReservationService {
	private ReservationRepository reservationRepository;
	private ProductRepository productRepository;

	@Autowired
	public ReservationService(ReservationRepository reservationRepository,
			ProductRepository productRepository) {
		this.reservationRepository = reservationRepository;
		this.productRepository = productRepository;
	}

	public boolean add(Reservation reservation, int productId) {
		if (!reservation.hasRequiredValues() || productId < 1) {
			return false;
		}
		Product product = productRepository.findOne(productId);
		reservation.setProduct(product);
		reservationRepository.save(reservation);
		return true;
	}

	public MyReservationVo getByUserId(int userId) {
		MyReservationVo myReservation = new MyReservationVo();
		List<Reservation> reservations = reservationRepository
				.findByUser_id(userId);
		if (reservations == null) {
			return null;
		}

		myReservation.setReservations(reservations.stream().collect(
				Collectors.groupingBy(Reservation::getReservationType)));
		myReservation.setTypeCounts(reservations.stream()
				.collect(Collectors.groupingBy(Reservation::getReservationType,
						Collectors.counting())));
		for (int i = 0; i < 4; i++) {
			if (myReservation.getTypeCounts().get(i) == null) {
				myReservation.getTypeCounts().put(i, 0l);
			}
		}

		return myReservation;
	}

}
