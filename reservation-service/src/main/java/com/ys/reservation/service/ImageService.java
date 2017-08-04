package com.ys.reservation.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ys.reservation.dao.ImageDao;
import com.ys.reservation.domain.Image;

@Service
@PropertySource("classpath:/application.properties")
public class ImageService {
	private ImageDao imageDao;
	@Value("${file.basedir}")
	private String baseDir;
	private static Logger logger = LoggerFactory.getLogger(ImageService.class);
	
	@Autowired
	public ImageService(ImageDao imageDao) {
		this.imageDao = imageDao;
	}
	
	public Image get(int id) {
		if(id < 1) {
			return null;
		}
		return imageDao.select(id);
	}

	public List<Integer> create(int userId, MultipartFile[] files){
		if (files != null && files.length > 0) {
			List<Integer> ids = new ArrayList<>();
			String formattedDate = baseDir + File.separator
					+ new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd")
							.format(new Date());
			File f = new File(formattedDate);
			if (!f.exists()) {
				f.mkdirs();
			}
			for (MultipartFile file : files) {
				String contentType = file.getContentType();
				String originalFilename = file.getOriginalFilename();
				long size = file.getSize();

				String uuid = UUID.randomUUID().toString();
				String saveFileName = formattedDate + File.separator + uuid;

				try (InputStream in = file.getInputStream();
						FileOutputStream fos = new FileOutputStream(saveFileName);) {
					int readCnt = 0;
					byte[] buffer = new byte[1024];
					while ((readCnt = in.read(buffer)) != -1) {
						fos.write(buffer, 0, readCnt);
					}
				} catch (Exception e) {
	            	logger.error("File IO error!", e);
				}
				Image image = new Image();
				image.setFileName(originalFilename);
				image.setUserId(userId);
				image.setSaveFileName(saveFileName);
				image.setFileLength(size);
				image.setContentType(contentType);
				image.setDeleteFlag(1);
				ids.add(imageDao.insert(image));
			}
			return ids;
		}
		return null;
	}
	
	public int delete(int id) {
		if(id<1) {
			return -1;
		}
		return imageDao.delete(id);
	}

}
