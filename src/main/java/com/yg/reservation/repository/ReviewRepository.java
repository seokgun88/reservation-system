package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
	public List<Review> findByProductId(int productId, Pageable pageable);
}
