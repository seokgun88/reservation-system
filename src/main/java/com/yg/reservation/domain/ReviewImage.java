package com.yg.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="reservation_user_review_images")
@Getter
@Setter
@ToString
public class ReviewImage {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "reservation_user_review_id")
	private Review review;
	@ManyToOne
	@JoinColumn(name="file_id")
	private Image image;
}
