package com.ys.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.service.UserCommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentApiController {
	@Autowired
	private UserCommentService userCommentService;
	
	@GetMapping("/{id}/images")
	public List<Integer> getImageIds(@PathVariable int id) {
		return userCommentService.getImgageIds(id);
	}
}
