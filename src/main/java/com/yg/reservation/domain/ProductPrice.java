package com.yg.reservation.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductPrice {
	private int productId;
	private int priceType;
	private int price;
	private float discountRate;
}
