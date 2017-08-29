package com.yg.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yg.reservation.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	public User findBySnsId(String snsId);
}
