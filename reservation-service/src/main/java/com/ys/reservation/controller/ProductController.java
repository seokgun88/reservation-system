package com.ys.reservation.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ys.reservation.domain.Product;
import com.ys.reservation.domain.User;
import com.ys.reservation.security.AuthUser;
import com.ys.reservation.service.ProductService;

@Controller
@RequestMapping("/products")
@PropertySource("classpath:/application.properties")
public class ProductController {
	@Value("${naver.openapi.clientid}")
	private String clientId;
	private ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/{id:[\\d]+}")
	public String productDetailById(@PathVariable int id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("clientId", clientId);
		return "detail";
	}

	@GetMapping("/{id:[\\d]+}/reservation")
	public String reservation(@PathVariable int id, Model model, HttpSession session) {
		model.addAttribute("profile", session.getAttribute("profile"));
		model.addAttribute("id", id);
		return "reserve";
	}

	@GetMapping("/{productId:[\\d]+}/comments/form")
	public String comment(@PathVariable int productId, @AuthUser User user, Model model) {
		Product p = productService.get(productId);
		model.addAttribute("productId", productId);
		model.addAttribute("userId", user.getId());
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
