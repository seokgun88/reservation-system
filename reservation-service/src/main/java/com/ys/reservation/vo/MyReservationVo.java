package com.ys.reservation.vo;

import java.util.List;
import java.util.Map;

public class MyReservationVo {
	private int totalReservationCount;
	private int scheduledReservationCount;
	private int completedReservationCount;
	private int canceledReservationCount;
	private Map<Integer, List<ReservationVo>> reservations;
	
	public MyReservationVo() {
	}
	public MyReservationVo(int totalReservationCount, int scheduledReservationCount, int completedReservationCount,
			int canceledReservationCount, Map<Integer, List<ReservationVo>> reservations) {
		this.totalReservationCount = totalReservationCount;
		this.scheduledReservationCount = scheduledReservationCount;
		this.completedReservationCount = completedReservationCount;
		this.canceledReservationCount = canceledReservationCount;
		this.reservations = reservations;
	}
	public int getTotalReservationCount() {
		return totalReservationCount;
	}
	public void setTotalReservationCount(int totalReservationCount) {
		this.totalReservationCount = totalReservationCount;
	}
	public int getScheduledReservationCount() {
		return scheduledReservationCount;
	}
	public void setScheduledReservationCount(int scheduledReservationCount) {
		this.scheduledReservationCount = scheduledReservationCount;
	}
	public int getCompletedReservationCount() {
		return completedReservationCount;
	}
	public void setCompletedReservationCount(int completedReservationCount) {
		this.completedReservationCount = completedReservationCount;
	}
	public int getCanceledReservationCount() {
		return canceledReservationCount;
	}
	public void setCanceledReservationCount(int canceledReservationCount) {
		this.canceledReservationCount = canceledReservationCount;
	}
	public Map<Integer, List<ReservationVo>> getReservations() {
		return reservations;
	}
	public void setReservations(Map<Integer, List<ReservationVo>> reservations) {
		this.reservations = reservations;
	}
	
	
}
