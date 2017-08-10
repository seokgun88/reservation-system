package com.yg.reservation.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductSummaryVo {
	private int id;
	private String name;
	private String description;
	private String placeName;
	private int mainImageId;
}
