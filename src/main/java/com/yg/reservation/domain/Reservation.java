package com.yg.reservation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@ToString
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "general_ticket_count")
	private int generalTicketCount;
	@Column(name = "youth_ticket_count")
	private int youthTicketCount;
	@Column(name = "child_ticket_count")
	private int childTicketCount;
	@Column(name = "reservation_name")
	private String reservationName;
	@Column(name = "reservation_tel")
	private String reservationTel;
	@Column(name = "reservation_email")
	private String reservationEmail;
	@Column(name = "reservation_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date reservationDate;
	@Column(name = "reservation_type")
	private int reservationType;
	@Column(name = "total_price")
	private int totalPrice;

	public boolean hasRequiredValues() {
		int totalCount = generalTicketCount + youthTicketCount
				+ childTicketCount;
		if (totalCount < 1 || StringUtils.isAnyBlank(reservationName,
				reservationEmail, reservationTel) || reservationDate == null) {
			return false;
		}
		return true;
	}

}
