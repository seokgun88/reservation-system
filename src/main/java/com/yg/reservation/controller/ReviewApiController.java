package com.yg.reservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yg.reservation.domain.User;
import com.yg.reservation.security.AuthUser;
import com.yg.reservation.service.ReviewService;
import com.yg.reservation.vo.ReviewWriteVo;

@RestController
@RequestMapping("/api/reviews")
public class ReviewApiController {
	private ReviewService reviewService;

	@Autowired
	public ReviewApiController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping
	public boolean add(@RequestBody ReviewWriteVo reviewWriteVo,
			@AuthUser User user) {
		reviewWriteVo.getReview().setUserId(user.getId());
		return reviewService.addWithImageIds(reviewWriteVo);
	}

}
