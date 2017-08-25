package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage, Integer> {
	public List<ProductImage> findByProduct_id(int productId);
}
