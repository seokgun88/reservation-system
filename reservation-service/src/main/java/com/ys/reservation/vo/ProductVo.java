package com.ys.reservation.vo;

public class ProductVo {
	private int id;
	private String name;
	private String description;
	private String placeName;
	
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
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	@Override
	public String toString() {
		return "ProductVo [id=" + id + ", name=" + name + ", description=" + description + ", placeName=" + placeName
				+ "]";
	}
}
