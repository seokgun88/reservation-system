package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.ReviewImage;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Integer> {
	public List<ReviewImage> findByReview_idIn(List<Integer> reviewIds);
}
