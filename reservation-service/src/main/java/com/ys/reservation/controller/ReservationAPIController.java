package com.ys.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.ReservationInfo;
import com.ys.reservation.service.ReservationService;
import com.ys.reservation.vo.MyReservationVo;

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
	
	@PutMapping("/{id:[\\d]+}")
	public void update(@PathVariable int id) {
		reservationService.update(id);
	}

	@GetMapping("/users/{id:[\\d]+}")
	public MyReservationVo getMyReservation(@PathVariable int id) {
		return reservationService.getMyReservation(id);
	}
}
