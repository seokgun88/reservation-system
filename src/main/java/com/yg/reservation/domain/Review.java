package com.yg.reservation.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Review {
	private String review;
	private int score;
	private Date modifyDate;
	private String userEmail;
}
