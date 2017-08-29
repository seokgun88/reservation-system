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

import com.yg.reservation.domain.Image;
import com.yg.reservation.domain.Product;
import com.yg.reservation.domain.Review;
import com.yg.reservation.domain.ReviewImage;
import com.yg.reservation.repository.ImageRepository;
import com.yg.reservation.repository.ProductRepository;
import com.yg.reservation.repository.ReviewImageRepository;
import com.yg.reservation.repository.ReviewRepository;
import com.yg.reservation.vo.ReviewVo;
import com.yg.reservation.vo.ReviewWriteVo;

@Service
public class ReviewService {
	private ReviewRepository reviewRepository;
	private ReviewImageRepository reviewImageRepository;
	private ImageRepository imageRepository;
	private ProductRepository productRepository;

	@Autowired
	public ReviewService(ReviewRepository reviewRepository,
			ReviewImageRepository reviewImageRepository,
			ImageRepository imageRepository,
			ProductRepository productRepository) {
		this.reviewRepository = reviewRepository;
		this.reviewImageRepository = reviewImageRepository;
		this.imageRepository = imageRepository;
		this.productRepository = productRepository;
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
		List<ReviewImage> imageIds = reviewImageRepository
				.findByReviewIdIn(ids);
		if (imageIds == null || imageIds.isEmpty()) {
			return null;
		}
		Map<Integer, List<Integer>> idToImageIds = imageIds.stream()
				.collect(Collectors.groupingBy(ReviewImage::getId,
						Collectors.mapping(
								(ReviewImage ri) -> ri.getImage().getId(),
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
		Product product = productRepository
				.findOne(reviewWriteVo.getProductId());
		review.setProduct(product);
		reviewRepository.save(review);

		List<Integer> imageIds = reviewWriteVo.getImageIds();
		if (imageIds == null || imageIds.size() == 0) {
			return true;
		}

		ReviewImage reviewImage = new ReviewImage();
		for (int imageId : imageIds) {
			reviewImage.setReview(reviewRepository.findOne(review.getId()));
			reviewImage.setImage(imageRepository.findOne(imageId));
			reviewImageRepository.save(reviewImage);
		}

		List<Image> images = imageRepository.findByIdIn(imageIds);
		for (Image image : images) {
			image.setDeleteFlag(0);
		}

		return true;
	}
}
