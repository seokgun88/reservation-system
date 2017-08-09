package com.yg.reservation.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Image {
	private int id;
	private int userId;
	private String fileName;
	private String saveFileName;
	private long fileLength;
	private String contentType;
	private int deleteFlag;
}
