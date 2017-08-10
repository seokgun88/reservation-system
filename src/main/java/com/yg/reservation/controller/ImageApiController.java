package com.yg.reservation.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yg.reservation.service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageApiController {
	private ImageService imageService;
	
	@Autowired
	public ImageApiController(ImageService imageService) {
		this.imageService = imageService;
	}

	@PostMapping
	public List<Integer> create(@RequestParam int userId, @RequestBody MultipartFile[] files) throws IllegalStateException, IOException {
		return imageService.create(userId, files); 
	}
}
