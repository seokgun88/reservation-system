package com.ys.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ys.reservation.dao.ImageDao;
import com.ys.reservation.dao.UserCommentDao;
import com.ys.reservation.domain.UserComment;
import com.ys.reservation.domain.UserCommentImage;

@Service
public class UserCommentService {
	private UserCommentDao userCommentDao;
	private ImageDao imageDao;

	@Autowired
	public UserCommentService(UserCommentDao userCommentDao, ImageDao imageDao) {
		this.userCommentDao = userCommentDao;
		this.imageDao = imageDao;
	}


	public List<Integer> getImgageIds(int id) {
		if(id < 1) {
			return null;
		}
		return imageDao.selectIdByCommentId(id);
	}
	
	@Transactional
	public void create(UserComment userComment, List<Integer> fileIds) {
		if(userComment == null
				|| userComment.getProductId() < 1
				|| userComment.getUserId() < 1
				|| userComment.getScore() < 0 || userComment.getScore() > 5
				|| StringUtils.isBlank(userComment.getComment())) {
			return;
		}
		int commentId = userCommentDao.insert(userComment);
		List<UserCommentImage> commentImages = new ArrayList<UserCommentImage>();
		
		if(fileIds == null) {
			return;
		}
		for(int fileId : fileIds) {
			UserCommentImage image = new UserCommentImage(commentId, fileId);
			commentImages.add(image);
		}
		imageDao.update(fileIds);
		userCommentDao.insertImages(commentImages);
	}
	
	public void update(UserComment userComment) {
		if(userComment == null
				|| userComment.getId() < 1
				|| userComment.getScore() < 0 || userComment.getScore() > 5
				|| StringUtils.isBlank(userComment.getComment())) {
			return;
		}
		userCommentDao.update(userComment);
	}
}
