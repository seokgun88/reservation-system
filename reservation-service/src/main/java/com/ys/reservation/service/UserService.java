package com.ys.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.UserDao;
import com.ys.reservation.domain.NaverProfile;
import com.ys.reservation.domain.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	public User create(NaverProfile profile) {
		User user = userDao.select(profile.getEmail());
		if(user == null) {
			user = new User();
			user.setAdminFlag(0);
			user.setEmail(profile.getEmail());
			user.setNickname(profile.getNickname());
			user.setSnsId(profile.getEmail().substring(0, 4) + "****");
			user.setUsername(profile.getName());
			
			return userDao.insert(user);
		}
		return user;
	}
}
