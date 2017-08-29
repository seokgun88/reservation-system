package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yg.reservation.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public List<Product> findTop5ByOrderByIdDesc();
	public List<Product> findByCategoryId(int categoryId, Pageable pageable);
}
