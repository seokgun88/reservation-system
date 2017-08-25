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
@Table(name = "reservation_user_reviews")
@Getter
@Setter
@ToString
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	private int score;
	private String review;
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Column(name = "modify_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

	public boolean hasRequiredValues() {
		if ((score > 50 || score < 0) || StringUtils.isBlank(review)) {
			return false;
		}
		return true;
	}
}
