package com.yg.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
}
