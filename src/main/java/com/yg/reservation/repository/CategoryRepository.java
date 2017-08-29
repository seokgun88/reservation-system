package com.yg.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yg.reservation.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
