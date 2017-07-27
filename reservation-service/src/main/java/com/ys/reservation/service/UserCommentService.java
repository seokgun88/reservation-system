package com.ys.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.ImageDao;

@Service
public class UserCommentService {
	private ImageDao imageDao;

	@Autowired
	public UserCommentService(ImageDao imageDao) {
		this.imageDao = imageDao;
	}


	public List<Integer> getImgageIds(int id) {
		if(id < 1) {
			return null;
		}
		return imageDao.selectIdByCommentId(id);
	}
}
