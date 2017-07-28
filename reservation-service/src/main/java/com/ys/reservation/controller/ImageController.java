package com.ys.reservation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ys.reservation.dao.ImageDao;
import com.ys.reservation.domain.Image;

@Controller
@RequestMapping("/admin/images")
@PropertySource("classpath:/application.properties")
public class ImageController {
	private ImageDao imageDao;
	@Value("${file.basedir}")
	private String baseDir;

	@Autowired
	public ImageController(ImageDao fileDao) {
		this.imageDao = fileDao;
	}

	@GetMapping
	public String imageAdmin() {
		return "imageAdmin";
	}
	
	@PostMapping
	public String create(
			@RequestParam String relation,
			@RequestParam int id,
			@RequestParam MultipartFile file) {
		if(file != null) {
			String formattedDate = baseDir + File.separator + new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd").format(new Date());
			File f = new File(formattedDate);
			if(!f.exists()) {
				f.mkdirs();
			}
			
			String contentType = file.getContentType();
			String originalFilename = file.getOriginalFilename();
			long size = file.getSize();
			
			String uuid = UUID.randomUUID().toString();
			String saveFileName = formattedDate + File.separator + uuid;
            
            try(
            		InputStream in = file.getInputStream();
            		FileOutputStream fos = new FileOutputStream(saveFileName);){
            	int readCnt = 0;
            	byte[] buffer = new byte[1024];
            	while((readCnt = in.read(buffer)) != -1){
            		fos.write(buffer, 0, readCnt);
            	}
            } catch(Exception e){
            	e.printStackTrace();
            }
            Image image = new Image();
            image.setFileName(originalFilename);
            image.setUserId(id);
            image.setSaveFileName(saveFileName);
            image.setFileLength(size);
            image.setContentType(contentType);
            image.setDeleteFlag(0);
            imageDao.insert(image);
		}
		
		return "redirect:/admin/images";
	}
}