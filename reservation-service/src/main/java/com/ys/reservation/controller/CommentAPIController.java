package com.ys.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.UserComment;
import com.ys.reservation.service.UserCommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentAPIController {
	private UserCommentService userCommentService;

	@Autowired
	public CommentAPIController(UserCommentService userCommentService) {
		this.userCommentService = userCommentService;
	}
	
	@PutMapping("/{id:[\\d]+}")
	public void update(@PathVariable int id, @RequestBody UserComment userComment) {
		userComment.setId(id);
		userCommentService.update(userComment);
	}
	
	@GetMapping("/{id:[\\d]+}/images")
	public List<Integer> getImageIds(@PathVariable int id) {
		return userCommentService.getImgageIds(id);
	}
}
