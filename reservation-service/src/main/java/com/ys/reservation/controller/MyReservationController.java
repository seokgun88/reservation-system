package com.ys.reservation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/myreservation")
public class MyReservationController {
	@GetMapping
	public String myReservation(HttpSession session) {
		if(session.getAttribute("login") == null) {
			return "redirect:/";
		}
		return "myreservation";
	}
}
