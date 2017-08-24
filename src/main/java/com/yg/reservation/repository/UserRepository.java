package com.yg.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findBySnsId(String snsId);
}
