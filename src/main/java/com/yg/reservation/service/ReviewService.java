package com.yg.reservation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yg.reservation.dao.ReviewDao;
import com.yg.reservation.domain.Review;

@Service
public class ReviewService {
	private ReviewDao reviewDao;
	
	@Autowired	
	public ReviewService(ReviewDao reviewDao) {
		this.reviewDao = reviewDao;
	}

	public List<Review> getLimitedByProductId(int productId ,int limit) {
		return reviewDao.selectLimitedByProductId(productId, limit);
	}

}
