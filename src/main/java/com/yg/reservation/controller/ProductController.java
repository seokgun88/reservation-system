package com.yg.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yg.reservation.domain.User;
import com.yg.reservation.security.AuthUser;
import com.yg.reservation.service.ProductService;

@Controller
@RequestMapping("/products")
@PropertySource("classpath:/application.properties")
public class ProductController {
	ProductService productService;
	@Value("${naver.openapi.clientid}")
	private String naverClientId;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{id:[\\d]+}")
	public String detail(Model model) {
		model.addAttribute("clientId", naverClientId);
		return "detail";
	}

	@GetMapping("/{id:[\\d]+}/reservation")
	public String reservation(@PathVariable int id, @AuthUser User user,
			Model model) {
		model.addAttribute("id", id);
		model.addAttribute("name", productService.getName(id));
		model.addAttribute("user", user);

		return "reserve";
	}

	@GetMapping("/{id:[\\d]+}/review")
	public String reviewWrite() {
		return "reviewWrite";
	}
}
