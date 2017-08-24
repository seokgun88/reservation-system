package com.yg.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yg.reservation.dao.ImageDao;
import com.yg.reservation.dao.ReviewImageDao;
import com.yg.reservation.domain.Review;
import com.yg.reservation.domain.ReviewImage;
import com.yg.reservation.repository.ReviewRepository;
import com.yg.reservation.vo.ReviewImageVo;
import com.yg.reservation.vo.ReviewVo;
import com.yg.reservation.vo.ReviewWriteVo;

@Service
public class ReviewService {
	private ReviewRepository reviewRepository;
	private ImageDao imageDao;
	private ReviewImageDao reviewImageDao;

	@Autowired
	public ReviewService(ReviewRepository reviewRepository, ImageDao imageDao,
			ReviewImageDao reviewImageDao) {
		this.reviewRepository = reviewRepository;
		this.imageDao = imageDao;
		this.reviewImageDao = reviewImageDao;
	}

	@Transactional(readOnly = true)
	public List<ReviewVo> getLimitedByProductId(int productId, int limit) {
		if (productId < 1 || limit < 1) {
			return null;
		}
		List<Review> reviews = reviewRepository.findByProductId(productId,
				new PageRequest(0, limit,
						new Sort(Direction.DESC, "modifyDate")));
		if (reviews == null || reviews.isEmpty()) {
			return null;
		}
		List<Integer> ids = reviews.stream().map(Review::getId)
				.collect(Collectors.toList());
		List<ReviewImageVo> imageIds = imageDao.selectIdsByReviewIds(ids);
		if (imageIds == null || imageIds.isEmpty()) {
			return null;
		}
		Map<Integer, List<Integer>> idToImageIds = imageIds.stream()
				.collect(Collectors.groupingBy(ReviewImageVo::getReviewId,
						Collectors.mapping(ReviewImageVo::getImageId,
								Collectors.toList())));
		List<ReviewVo> reviewVos = new ArrayList<>();
		for (Review review : reviews) {
			ReviewVo reviewVo = new ReviewVo();
			reviewVo.setId(review.getId());
			reviewVo.setReview(review.getReview());
			reviewVo.setScore(review.getScore());
			reviewVo.setModifyDate(review.getModifyDate());
			reviewVo.setUserEmail(review.getUser().getEmail());
			reviewVo.setImages(idToImageIds.getOrDefault(review.getId(), null));
			reviewVos.add(reviewVo);
		}
		return reviewVos;
	}

	@Transactional
	public boolean addWithImageIds(ReviewWriteVo reviewWriteVo) {
		if (reviewWriteVo == null || reviewWriteVo.getReview() == null
				|| !reviewWriteVo.getReview().hasRequiredValues()) {
			return false;
		}
		Review review = reviewWriteVo.getReview();
		reviewRepository.save(review);

		List<Integer> imageIds = reviewWriteVo.getImageIds();
		if (imageIds == null || imageIds.size() == 0) {
			return true;
		}

		ReviewImage reviewImage = new ReviewImage();
		for (int imageId : imageIds) {
			reviewImage.setReservationUserReviewId(review.getId());
			reviewImage.setFileId(imageId);
			reviewImageDao.insert(reviewImage);
		}

		imageDao.updateDeleteFlagTo0(imageIds);

		return true;
	}
}
