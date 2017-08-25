package com.yg.reservation.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yg.reservation.domain.Image;
import com.yg.reservation.domain.User;
import com.yg.reservation.repository.ImageRepository;

@Service
@PropertySource("classpath:/application.properties")
public class ImageService {
	private ImageRepository imageRepository;
	@Value("${file.basedir}")
	private String baseDir;

	@Autowired
	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public Image get(int id) {
		if (id < 1) {
			return null;
		}
		return imageRepository.findOne(id);
	}

	public List<Integer> add(User user, MultipartFile[] files)
			throws IllegalStateException, IOException {
		if (files == null || files.length < 1) {
			return null;
		}
		List<Integer> ids = new ArrayList<>();
		String formattedDate = baseDir + File.separator
				+ new SimpleDateFormat(
						"yyyy" + File.separator + "MM" + File.separator + "dd")
								.format(new Date());
		File directory = new File(formattedDate);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		for (MultipartFile multipartFile : files) {
			String contentType = multipartFile.getContentType();
			String originalFilename = multipartFile.getOriginalFilename();
			long size = multipartFile.getSize();

			String uuid = UUID.randomUUID().toString();

			File f = new File(directory, uuid);
			multipartFile.transferTo(f);

			Image image = new Image();
			image.setFileName(originalFilename);
			image.setUser(user);
			image.setSaveFileName(f.getCanonicalPath());
			image.setFileLength(size);
			image.setContentType(contentType);
			image.setDeleteFlag(1);
			imageRepository.save(image);
			ids.add(image.getId());
		}
		return ids;
	}
}
