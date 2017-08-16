package com.yg.reservation.domain;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Review {
	private int id;
	private int productId;
	private int userId;
	private int score;
	private String review;

	public boolean hasRequiredValues() {
		if (productId < 1 || userId < 1 || (score > 50 || score < 0)
				|| StringUtils.isBlank(review)) {
			return false;
		}
		return true;
	}
}
