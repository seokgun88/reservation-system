package com.yg.reservation.vo;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MyReservationVo {
	private Map<Integer, Long> typeCounts;
	private Map<Integer, List<ReservationVo>> reservations;
}
