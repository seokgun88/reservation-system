package com.ys.reservation.domain;

public class UserCommentImage {
	private int id;
	private int reservationUserCommentId;
	private int fileId;
	
	public UserCommentImage(int reservationUserCommentId, int fileId) {
		this.reservationUserCommentId = reservationUserCommentId;
		this.fileId = fileId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReservationUserCommentId() {
		return reservationUserCommentId;
	}
	public void setReservationUserCommentId(int reservationUserCommentId) {
		this.reservationUserCommentId = reservationUserCommentId;
	}
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	
}
