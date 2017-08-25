package com.yg.reservation.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="product_details")
@Getter
@Setter
@ToString(exclude="product")
public class ProductDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="native")
	@GenericGenerator(name="native", strategy="native")
	private int id;
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="product_id")
	@JsonIgnore
	private Product product;
	private String content;
}
