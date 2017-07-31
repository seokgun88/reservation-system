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

import com.ys.reservation.dao.ImageDao;
import com.ys.reservation.domain.Image;
import com.ys.reservation.service.ImageService;

@RestController
@RequestMapping("/api/images")
@PropertySource("classpath:/application.properties")
public class ImageApiController {
	private ImageDao imageDao;
	private ImageService imageService;
	@Value("${file.basedir}")
	private String baseDir;

	@Autowired
	public ImageApiController(ImageService imageService, ImageDao imageDao) {
		this.imageService = imageService;
		this.imageDao = imageDao;
	}
	
	@DeleteMapping("/{id:[\\d]+}")
	public void delete(@PathVariable int id) {
		imageService.delete(id);
	}

	@GetMapping("/{id:[\\d]+}")
	public ModelAndView getFile(@PathVariable int id, HttpServletResponse response) {
		Image image = imageDao.select(id);
		return new ModelAndView("imageDownloadView", "image", image);
	}

	@PostMapping("/users/{id:[\\d]+}")
	public List<Integer> create(@PathVariable int id, @RequestBody MultipartFile[] files) {
		return imageService.create(id, files); 
	}
}
