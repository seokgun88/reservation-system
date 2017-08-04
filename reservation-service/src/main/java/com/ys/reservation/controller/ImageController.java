package com.ys.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ys.reservation.service.ImageService;

@Controller
@RequestMapping("/admin/images")
@PropertySource("classpath:/application.properties")
public class ImageController {
	private ImageService imageService;
	@Value("${file.basedir}")
	private String baseDir;

	@Autowired
	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping
	public String imageAdmin() {
		return "imageAdmin";
	}
	
	@PostMapping
	public String create(@RequestParam int id, @RequestParam MultipartFile file) {
		MultipartFile[] files = { file };
		imageService.create(id, files);
		return "redirect:/admin/images";
	}
}
