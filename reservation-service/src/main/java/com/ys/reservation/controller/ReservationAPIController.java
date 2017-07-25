package com.ys.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.ReservationInfo;
import com.ys.reservation.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
public class ReservationAPIController {
	private ReservationService reservationService;

	@Autowired
	public ReservationAPIController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}
	
	@PostMapping
	public ReservationInfo create(@RequestBody ReservationInfo reservationInfo) {
		return reservationService.create(reservationInfo);
	}
}
