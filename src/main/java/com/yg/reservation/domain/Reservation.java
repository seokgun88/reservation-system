package com.yg.reservation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@ToString(exclude="user")
public class Reservation {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
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
	private Integer reservationType;
	@Column(name = "total_price")
	private int totalPrice;
	@Column(name = "create_date", nullable = false, updatable = false, insertable = false, 
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Column(name = "modify_date", nullable = false, updatable = false, insertable = false, 
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

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
