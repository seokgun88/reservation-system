package com.yg.reservation.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.dao.ImageDao;
import com.yg.reservation.dao.ReviewDao;
import com.yg.reservation.dao.ReviewImageDao;
import com.yg.reservation.domain.ReviewImage;
import com.yg.reservation.vo.ReviewImageVo;
import com.yg.reservation.vo.ReviewVo;
import com.yg.reservation.vo.ReviewWriteVo;

@Service
public class ReviewService {
	private ReviewDao reviewDao;
	private ImageDao imageDao;
	private ReviewImageDao reviewImageDao;

	@Autowired
	public ReviewService(ReviewDao reviewDao, ImageDao imageDao,
			ReviewImageDao reviewImageDao) {
		this.reviewDao = reviewDao;
		this.imageDao = imageDao;
		this.reviewImageDao = reviewImageDao;
	}

	@Transactional(readOnly = true)
	public List<ReviewVo> getLimitedByProductId(int productId, int limit) {
		if (productId < 1 || limit < 1) {
			return null;
		}
		List<ReviewVo> reviews = reviewDao.selectLimitedByProductId(productId,
				limit);
		if (reviews == null || reviews.isEmpty()) {
			return null;
		}
		List<Integer> ids = reviews.stream().map(ReviewVo::getId)
				.collect(Collectors.toList());
		List<ReviewImageVo> imageIds = imageDao.selectIdsByReviewIds(ids);
		if (imageIds == null || imageIds.isEmpty()) {
			return null;
		}
		Map<Integer, List<Integer>> idToImageIds = imageIds.stream()
				.collect(Collectors.groupingBy(ReviewImageVo::getReviewId,
						Collectors.mapping(ReviewImageVo::getImageId,
								Collectors.toList())));
		for (ReviewVo review : reviews) {
			review.setImages(idToImageIds.getOrDefault(review.getId(), null));
		}
		return reviews;
	}

	@Transactional
	public boolean addWithImageIds(ReviewWriteVo reviewWriteVo) {
		if (reviewWriteVo == null || reviewWriteVo.getReview() == null
				|| !reviewWriteVo.getReview().hasRequiredValues()) {
			return false;
		}
		int reviewId = reviewDao.insert(reviewWriteVo.getReview());

		List<Integer> imageIds = reviewWriteVo.getImageIds();
		if (imageIds == null || imageIds.size() == 0) {
			return true;
		}

		ReviewImage reviewImage = new ReviewImage();
		for (int imageId : imageIds) {
			reviewImage.setReservationUserReviewId(reviewId);
			reviewImage.setFileId(imageId);
			reviewImageDao.insert(reviewImage);
		}

		imageDao.updateDeleteFlagTo0(imageIds);

		return true;
	}
}
