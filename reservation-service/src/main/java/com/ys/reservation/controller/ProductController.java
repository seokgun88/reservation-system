package com.ys.reservation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ys.reservation.domain.Product;
import com.ys.reservation.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {
	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

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

	@GetMapping("/{productId:[\\d]+}/comments/users/{userId:[\\d]+}")
	public String comment(@PathVariable int productId, @PathVariable int userId, Model model) {
		Product p = productService.get(productId);
		model.addAttribute("productId", productId);
		model.addAttribute("userId", userId);
		model.addAttribute("productName", p.getName());
		return "reviewWrite";
	}

	@GetMapping("/{productId:[\\d]+}/comments")
	public String commentAll(@PathVariable int productId, Model model) {
		Product product = productService.get(productId);
		model.addAttribute("product", product);
		return "review";
	}
}
