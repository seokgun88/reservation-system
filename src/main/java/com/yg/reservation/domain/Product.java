package com.yg.reservation.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="products")
@Getter
@Setter
@ToString
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	@Column(name="category_id")
	private int categoryId;
	private String name;
	private String description;
	@Column(name="sales_start")
	@Temporal(TemporalType.TIMESTAMP)
	private Date salesStart;
	@Column(name="sales_end")
	@Temporal(TemporalType.TIMESTAMP)
	private Date salesEnd;
	@Column(name="sales_flag")
	private int salesFlag;
	private String event;
	@Column(name="review_total_score")
	private int reviewTotalScore;
	@Column(name="review_count")
	private int reviewCount;
	@OneToOne(mappedBy="product")
	private ProductDisplay productDisplay;
}
