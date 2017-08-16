package com.yg.reservation.vo;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewVo {
	private int id;
	private String review;
	private int score;
	private Date modifyDate;
	private String userEmail;
	private List<Integer> images;
}
