package com.yg.reservation.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.dao.ImageDao;
import com.yg.reservation.dao.ReviewDao;
import com.yg.reservation.domain.Review;
import com.yg.reservation.vo.ReviewImageVo;

@Service
public class ReviewService {
	private ReviewDao reviewDao;
	private ImageDao imageDao;
	
	@Autowired	
	public ReviewService(ReviewDao reviewDao, ImageDao imageDao) {
		this.reviewDao = reviewDao;
		this.imageDao = imageDao;
	}

	@Transactional(readOnly=true)
	public List<Review> getLimitedByProductId(int productId, int limit) {
		if(productId < 1 || limit < 1) {
			return null;
		}
		List<Review> reviews = reviewDao.selectLimitedByProductId(productId, limit);
		if(reviews == null || reviews.isEmpty()) {
			return null;
		}
		List<Integer> ids = reviews.stream().map(Review::getId).collect(Collectors.toList());
		List<ReviewImageVo> imageIds = imageDao.selectIdsByReviewIds(ids);
		if(imageIds == null || imageIds.isEmpty()) {
			return null;
		}
		Map<Integer, List<Integer>> idToImageIds = imageIds.stream().collect(
				Collectors.groupingBy(ReviewImageVo::getReviewId, 
						Collectors.mapping(ReviewImageVo::getImageId, Collectors.toList())));
		for(Review review : reviews) {
			review.setImages(idToImageIds.getOrDefault(review.getId(), null));
		}		
		return reviews;
	}

}
