package com.ys.reservation.vo;

import java.util.Date;

public class ProductReservationVo {
	private String name;
	private String placeName;
	private Date displayStart;
	private Date displayEnd;
	private String observationTime;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public Date getDisplayStart() {
		return displayStart;
	}
	public void setDisplayStart(Date displayStart) {
		this.displayStart = displayStart;
	}
	public Date getDisplayEnd() {
		return displayEnd;
	}
	public void setDisplayEnd(Date displayEnd) {
		this.displayEnd = displayEnd;
	}
	public String getObservationTime() {
		return observationTime;
	}
	public void setObservationTime(String observationTime) {
		this.observationTime = observationTime;
	}
}
