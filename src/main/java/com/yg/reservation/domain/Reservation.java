package com.yg.reservation.domain;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Reservation {
	private int id;
	private int productId;
	private int userId;
	private int generalTicketCount;
	private int youthTicketCount;
	private int childTicketCount;
	private String reservationName;
	private String reservationTel;
	private String reservationEmail;
	private Date reservationDate;
	private int reservationType;
	private int totalPrice;

	public boolean hasRequiredValues() {
		int totalCount = generalTicketCount + youthTicketCount
				+ childTicketCount;
		if (productId < 1 || userId < 1 || totalCount < 1 || StringUtils
				.isAnyBlank(reservationName, reservationEmail, reservationTel)
				|| reservationDate == null) {
			return false;
		}
		return true;
	}

}
