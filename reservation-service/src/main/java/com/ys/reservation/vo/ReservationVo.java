package com.ys.reservation.vo;

import java.util.Date;

public class ReservationVo {
	private int id;
	private int userId;
	private int productId;
	private String productName;
	private int generalTicketCount;
	private int youthTicketCount;
	private int childTicketCount;
	private int reservationType;
	private Date displayStart;
	private Date displayEnd;
	private int totalPrice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getGeneralTicketCount() {
		return generalTicketCount;
	}
	public void setGeneralTicketCount(int generalTicketCount) {
		this.generalTicketCount = generalTicketCount;
	}
	public int getYouthTicketCount() {
		return youthTicketCount;
	}
	public void setYouthTicketCount(int youthTicketCount) {
		this.youthTicketCount = youthTicketCount;
	}
	public int getChildTicketCount() {
		return childTicketCount;
	}
	public void setChildTicketCount(int childTicketCount) {
		this.childTicketCount = childTicketCount;
	}
	public int getReservationType() {
		return reservationType;
	}
	public void setReservationType(int reservationType) {
		this.reservationType = reservationType;
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
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int generalPrice, int youthPrice, int childPrice) {
		this.totalPrice = generalPrice + youthPrice + childPrice;
	}
	@Override
	public String toString() {
		return "ReservationVo [userId=" + userId + ", productId=" + productId + ", productName=" + productName
				+ ", generalTicketCount=" + generalTicketCount + ", youthTicketCount=" + youthTicketCount
				+ ", childTicketCount=" + childTicketCount + ", reservationType=" + reservationType + ", displayStart="
				+ displayStart + ", displayEnd=" + displayEnd + ", totalPrice=" + totalPrice + "]";
	}
	
	
}
