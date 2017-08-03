package com.ys.reservation.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ys.reservation.domain.Image;
import com.ys.reservation.domain.User;
import com.ys.reservation.security.AuthUser;
import com.ys.reservation.service.ImageService;

@RestController
@RequestMapping("/api/images")
@PropertySource("classpath:/application.properties")
public class ImageApiController {
	private ImageService imageService;
	@Value("${file.basedir}")
	private String baseDir;

	@Autowired
	public ImageApiController(ImageService imageService) {
		this.imageService = imageService;
	}

	@GetMapping("/{id:[\\d]+}")
	public ModelAndView getFile(@PathVariable int id, HttpServletResponse response) {
		Image image = imageService.get(id);
		return new ModelAndView("imageDownloadView", "image", image);
	}

	@PostMapping
	public List<Integer> create(@RequestBody MultipartFile[] files, 
			@AuthUser User user) {
		return imageService.create(user.getId(), files); 
	}

	@DeleteMapping("/{id:[\\d]+}")
	public void delete(@PathVariable int id) {
		imageService.delete(id);
	}
}
