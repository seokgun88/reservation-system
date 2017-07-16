package com.ys.reservation.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/files")
public class FileController {
	private String baseDir = "c:" + File.separator + "Users" + File.separator + "YS" + File.separator + "git" + File.separator + "temp_img";
	
	@GetMapping
	public String imageAdmin() {
		return "imageAdmin";
	}
	
	@PostMapping
	public String create(
			@RequestParam String relation,
			@RequestParam String id,
			@RequestParam MultipartFile file) {
		if(file != null) {
			String formattedDate = baseDir + new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd").format(new Date());
			File f = new File(formattedDate);
			if(!f.exists()) {
				f.mkdirs();
			}
			
			String contentType = file.getContentType();
			String name = file.getName();
			String originalFilename = file.getOriginalFilename();
			long size = file.getSize();
			
			String uuid = UUID.randomUUID().toString();
			String saveFileName = formattedDate + File.separator + uuid;
			
            System.out.println("contentType :" + contentType);
            System.out.println("name :" + name);
            System.out.println("originalFilename : " + originalFilename);
            System.out.println("size : " + size);
            System.out.println("saveFileName : " + saveFileName);
            
            
		}
		
		return "redirect:/admin/files";
	}
}
