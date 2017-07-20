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

import com.ys.reservation.dao.FileDao;
import com.ys.reservation.domain.FileDomain;

@RestController
@RequestMapping("/api/files")
public class FileApiController {
	private FileDao fileDao;

	@Autowired
	public FileApiController(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	@GetMapping("/{id}")
	public void getFile(@PathVariable int id, HttpServletResponse response) {
		FileDomain fileDomain = fileDao.select(id);
		response.setContentLengthLong(fileDomain.getFileLength());
		response.setContentType(fileDomain.getContentType());
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileDomain.getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		File f = new File(fileDomain.getSaveFileName());
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
