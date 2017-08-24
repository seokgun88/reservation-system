package com.yg.reservation.vo;

import com.yg.reservation.domain.Reservation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationVo {
	private int productId;
	private Reservation reservation;
}
