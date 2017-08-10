package com.yg.reservation.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.yg.reservation.domain.Image;
import com.yg.reservation.service.ImageService;

@RestController
@RequestMapping("/api/images")
public class ImageApiController {
	private ImageService imageService;
	
	@Autowired
	public ImageApiController(ImageService imageService) {
		this.imageService = imageService;
	}
	
	@GetMapping("/{id:[\\d]+}")
	public ModelAndView getImage(@PathVariable int id) {
		Image image = imageService.get(id);
		return new ModelAndView("imageDownloadView", "image", image);
	}

	@PostMapping
	public List<Integer> create(@RequestParam int userId, @RequestBody MultipartFile[] files) throws IllegalStateException, IOException {
		return imageService.create(userId, files); 
	}
}
