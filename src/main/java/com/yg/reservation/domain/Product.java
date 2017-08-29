package com.yg.reservation.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString(exclude="category")
public class Product {
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private int id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonIgnore
	private Category category;
	private String name;
	private String description;
	@Column(name = "sales_start")
	@Temporal(TemporalType.TIMESTAMP)
	private Date salesStart;
	@Column(name = "sales_end")
	@Temporal(TemporalType.TIMESTAMP)
	private Date salesEnd;
	@Column(name = "sales_flag")
	private int salesFlag;
	private String event;
	@Column(name = "review_total_score")
	private int reviewTotalScore;
	@Column(name = "review_count")
	private int reviewCount;
	@Column(name = "create_date", nullable = false, updatable = false, insertable = false, 
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	@Column(name = "modify_date", nullable = false, updatable = false, insertable = false, 
			columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;
	@OneToOne(mappedBy = "product")
	private ProductDisplay productDisplay;
	@OneToOne(mappedBy = "product")
	private ProductDetail productDetail;
	@OneToMany(mappedBy = "product", fetch=FetchType.EAGER)
	private List<ProductImage> productImages;
}
