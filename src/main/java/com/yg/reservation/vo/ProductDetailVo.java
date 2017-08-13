package com.yg.reservation.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductDetailVo {
	private String name;
	private List<Integer> images;
	private String description;
	private String event;
	private String content;
	private int subImage;
	private String placeName;
	private String placeLot;
	private String placeStreet;
	private String tel;
	private String homepage;
	private String email;
	private int reviewCount;
	private int reviewTotalScore;
	
}
