package com.yg.reservation.vo;

import java.util.List;

import com.yg.reservation.domain.Review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReviewWriteVo {
	private Review review;
	private int productId;
	private List<Integer> imageIds;
}
