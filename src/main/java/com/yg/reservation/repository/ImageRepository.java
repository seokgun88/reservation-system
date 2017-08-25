package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.Image;

public interface ImageRepository extends JpaRepository<Image, Integer> {
	public List<Image> findByIdIn(List<Integer> ids); 
}
