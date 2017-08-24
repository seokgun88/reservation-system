package com.yg.reservation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="product_prices")
@Getter
@Setter
@ToString
public class ProductPrice {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	@Column(name="product_id")
	private int productId;
	@Column(name="price_type")
	private int priceType;
	private int price;
	@Column(name="discount_rate")
	private int discountRate;
}
