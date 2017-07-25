package com.ys.reservation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
	@GetMapping("/{id:[\\d]+}")
	public String productDetailById(@PathVariable int id, Model model) {
		model.addAttribute("id", id);
		return "detail";
	}

	@GetMapping("/{id:[\\d]+}/reservation")
	public String reservation(@PathVariable int id, Model model, HttpSession session) {
		if(session.getAttribute("login") == null) {
			return "redirect:/";
		}
		model.addAttribute("profile", session.getAttribute("profile"));
		model.addAttribute("id", id);
		return "reserve";
	}
}
