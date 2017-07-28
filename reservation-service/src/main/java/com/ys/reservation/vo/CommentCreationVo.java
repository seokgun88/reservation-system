package com.ys.reservation.vo;

import java.util.List;

public class CommentCreationVo {
	private float score;
	private String comment;
	private List<Integer> fileIds;
	
	public float getScore() {
		return score;
	}
	public void setScore(float score) {
		this.score = score;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public List<Integer> getFileIds() {
		return fileIds;
	}
	public void setFileIds(List<Integer> fileIds) {
		this.fileIds = fileIds;
	}
}
