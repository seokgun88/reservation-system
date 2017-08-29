package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yg.reservation.domain.ProductPrice;

@Repository
public interface ProductPriceRepository
		extends JpaRepository<ProductPrice, Integer> {
	public List<ProductPrice> findByProductId(int productId);
}
