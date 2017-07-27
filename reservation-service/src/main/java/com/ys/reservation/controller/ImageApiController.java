package com.ys.reservation.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.dao.ImageDao;
import com.ys.reservation.domain.Image;

@RestController
@RequestMapping("/api/images")
public class ImageApiController {
	private ImageDao imageDao;

	@Autowired
	public ImageApiController(ImageDao imageDao) {
		this.imageDao = imageDao;
	}

	@GetMapping("/{id}")
	public void getFile(@PathVariable int id, HttpServletResponse response) {
		Image image = imageDao.select(id);
		response.setContentLengthLong(image.getFileLength());
		response.setContentType(image.getContentType());
		response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		File f = new File(image.getSaveFileName());
		if(!f.exists()) {
			throw new RuntimeException("file not found");
		}
		try(FileInputStream fis = new FileInputStream(f)) {
			FileCopyUtils.copy(fis, response.getOutputStream());
			response.getOutputStream().flush();
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
