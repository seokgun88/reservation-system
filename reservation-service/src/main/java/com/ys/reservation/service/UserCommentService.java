package com.ys.reservation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.UserCommentDao;
import com.ys.reservation.domain.FileDomain;

@Service
public class UserCommentService {
	private UserCommentDao userCommentDao;

	@Autowired
	public UserCommentService(UserCommentDao userCommentDao) {
		this.userCommentDao = userCommentDao;
	}

	public List<Integer> getImgageIds(int id) {
		if(id < 1) {
			return null;
		}
		List<FileDomain> files = userCommentDao.selectFiles(id);
		if(files == null) {
			return null;
		}
		List<Integer> ids = files.stream().map(f -> f.getId()).collect(Collectors.toList());
		return ids;
	}
}
