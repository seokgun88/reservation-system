package com.yg.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.dao.UserDao;
import com.yg.reservation.domain.NaverProfile;
import com.yg.reservation.domain.User;

@Service
public class UserService {
	private UserDao userDao;

	@Autowired
	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}

	public User add(NaverProfile profile) {
		User user = userDao.select(profile.getId());
		if(user == null) {
			user = new User();
			user.setAdminFlag(0);
			user.setEmail(profile.getEmail());
			user.setNickname(profile.getNickname());
			user.setSnsId(profile.getId());
			user.setUsername(profile.getName());
			
			return userDao.insert(user);
		}
		return user;
	}
}
