package com.yg.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my")
public class ReservationController {
	@GetMapping
	public String myReservation() {
		return "/myReservation";
	}
}
