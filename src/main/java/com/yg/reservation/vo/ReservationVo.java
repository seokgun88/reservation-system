package com.yg.reservation.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReservationVo {
	private int id;
	private int productId;
	private String productName;
	private int generalTicketCount;
	private int youthTicketCount;
	private int childTicketCount;
	private int reservationType;
	private Date displayStart;
	private Date displayEnd;
	private int totalPrice;
}
