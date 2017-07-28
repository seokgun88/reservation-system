package com.ys.reservation.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ys.reservation.dao.ImageDao;
import com.ys.reservation.domain.Image;
import com.ys.reservation.service.ImageService;

@RestController
@RequestMapping("/api/images")

public class ImageApiController {
	private ImageDao imageDao;
	private ImageService imageService;
	
	@Autowired
	public ImageApiController(ImageDao imageDao, ImageService imageService) {
		super();
		this.imageDao = imageDao;
		this.imageService = imageService;
	}

	@DeleteMapping("/{id:[\\d]+}")
	public void delete(@PathVariable int id) {
		imageService.delete(id);
	}
	
	@GetMapping("/{id:[\\d]+}")
	public void getFile(@PathVariable int id, HttpServletResponse response) {
		Image image = imageDao.select(id);
		response.setContentLengthLong(image.getFileLength());
		response.setContentType(image.getContentType());
		response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");

		File f = new File(image.getSaveFileName());
		if (!f.exists()) {
			throw new RuntimeException("file not found");
		}
		try (FileInputStream fis = new FileInputStream(f)) {
			FileCopyUtils.copy(fis, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@PostMapping("/users/{id:[\\d]+}")
	public List<Integer> create(@PathVariable int id, @RequestBody MultipartFile[] files) {
		return imageService.create(id, files); 
	}
}
