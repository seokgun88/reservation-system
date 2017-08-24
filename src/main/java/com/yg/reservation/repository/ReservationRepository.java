package com.yg.reservation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yg.reservation.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	public List<Reservation> findByUser_id(int userId); 
}
