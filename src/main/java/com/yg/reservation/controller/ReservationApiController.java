package com.yg.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg.reservation.domain.Reservation;
import com.yg.reservation.domain.User;
import com.yg.reservation.security.AuthUser;
import com.yg.reservation.service.ReservationService;
import com.yg.reservation.vo.MyReservationVo;

@RestController
@RequestMapping("/api/reservations")
public class ReservationApiController {
	ReservationService reservationService;

	@Autowired
	public ReservationApiController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@PostMapping
	public boolean add(@RequestBody Reservation reservation,
			@AuthUser User user) {
		reservation.setUserId(user.getId());
		return reservationService.add(reservation);
	}

	@GetMapping("/my")
	public MyReservationVo getMy(@AuthUser User user) {
		return reservationService.getMy(user.getId());
	}

}
