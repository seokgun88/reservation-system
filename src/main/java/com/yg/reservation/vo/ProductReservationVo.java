package com.yg.reservation.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductReservationVo {
	private String name;
	private String placeName;
	private Date displayStart;
	private Date displayEnd;
	private String observationTime;	
	private int mainImageId;
}
