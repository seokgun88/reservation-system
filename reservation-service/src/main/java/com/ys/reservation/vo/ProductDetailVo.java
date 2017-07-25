package com.ys.reservation.vo;

import java.util.Date;

public class ProductDetailVo {
	private int id;
	private String name;
	private String description;
	private String event;
	private Date salesEnd;
	private int salesFlag;
	private String content;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Date getSalesEnd() {
		return salesEnd;
	}
	public void setSalesEnd(Date salesEnd) {
		this.salesEnd = salesEnd;
	}
	public int getSalesFlag() {
		return salesFlag;
	}
	public void setSalesFlag(int salesFlag) {
		this.salesFlag = salesFlag;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
