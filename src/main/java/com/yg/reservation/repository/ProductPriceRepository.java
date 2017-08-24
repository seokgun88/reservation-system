package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.ProductPrice;

public interface ProductPriceRepository
		extends JpaRepository<ProductPrice, Integer> {
	public List<ProductPrice> findByProductId(int productId);
}
