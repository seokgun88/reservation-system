package com.ys.reservation.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ys.reservation.dao.FileDao;
import com.ys.reservation.domain.FileDomain;

@Controller
@RequestMapping("/admin/files")
public class FileController {
	private FileDao fileDao;
	private String baseDir = "c:" + File.separator + "temp_img";

	@Autowired
	public FileController(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	public FileController(FileDao fileDao, String baseDir) {
		this.fileDao = fileDao;
		this.baseDir = baseDir;
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
			
//            System.out.println("contentType :" + contentType);
//            System.out.println("name :" + name);
//            System.out.println("originalFilename : " + originalFilename);
//            System.out.println("size : " + size);
//            System.out.println("saveFileName : " + saveFileName);
            
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
            FileDomain fileDomain = new FileDomain();
            fileDomain.setFileName(originalFilename);
            fileDomain.setUserId(id);
            fileDomain.setSaveFileName(saveFileName);
            fileDomain.setFileLength(size);
            fileDomain.setContentType(contentType);
            fileDomain.setDeleteFlag(0);
            fileDao.insert(fileDomain);
		}
		
		return "redirect:/admin/files";
	}
}
