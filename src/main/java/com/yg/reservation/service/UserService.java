package com.yg.reservation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.domain.NaverProfile;
import com.yg.reservation.domain.User;
import com.yg.reservation.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User add(NaverProfile profile) {
		User user = userRepository.findBySnsId(profile.getId());
		if (user == null) {
			user = new User();
			user.setAdminFlag(0);
			user.setEmail(profile.getEmail());
			user.setNickname(profile.getNickname());
			user.setSnsId(profile.getId());
			user.setUsername(profile.getName());

			return userRepository.save(user);
		}
		return user;
	}
}
