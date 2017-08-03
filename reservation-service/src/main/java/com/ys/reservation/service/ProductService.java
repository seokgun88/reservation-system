package com.ys.reservation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.CategoryDao;
import com.ys.reservation.dao.ImageDao;
import com.ys.reservation.dao.PriceDao;
import com.ys.reservation.dao.ProductDao;
import com.ys.reservation.dao.ReservationDao;
import com.ys.reservation.dao.UserCommentDao;
import com.ys.reservation.domain.Image;
import com.ys.reservation.domain.Price;
import com.ys.reservation.domain.Product;
import com.ys.reservation.vo.CommentImageVo;
import com.ys.reservation.vo.CommentsSummaryVo;
import com.ys.reservation.vo.DisplayInfoVo;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductReservationVo;
import com.ys.reservation.vo.ProductVo;
import com.ys.reservation.vo.UserCommentVo;

@Service
public class ProductService {
	private ProductDao productDao;
	private CategoryDao categoryDao;
	private ImageDao imageDao;
	private UserCommentDao userCommentDao;
	private PriceDao priceDao;
	private ReservationDao reservationDao;
	
	@Autowired
	public ProductService(ProductDao productDao, CategoryDao categoryDao, ImageDao imageDao,
			UserCommentDao userCommentDao, PriceDao priceDao, ReservationDao reservationDao) {
		this.productDao = productDao;
		this.categoryDao = categoryDao;
		this.imageDao = imageDao;
		this.userCommentDao = userCommentDao;
		this.priceDao = priceDao;
		this.reservationDao = reservationDao;
	}

	public List<ProductVo> getAll() {
		return productDao.selectAll();
	}

	public List<ProductVo> getWithLimit(int page) {
		if(page < 1) {
			return null;
		}
		int offset = 10 * (page-1);
		return productDao.selectLimited(offset);
	}
	
	public Product get(int id) {
		if(id < 1) {
			return null;
		}
		return productDao.select(id);
	}
	
	public int getCount() {
		return productDao.countAll();
	}
	
	public List<ProductVo> getWithLimitByCategoryId(int categoryId, int page) {
		if(categoryId < 1 || page <= 0) {
			return null;
		}
		int offset = 10 * (page-1);
		return productDao.selectLimitedByCategoryId(categoryId, offset);
	}
	
	public int getCountByCategoryId(int categoryId) {
		if(categoryId < 1) {
			return -1;
		}
		return categoryDao.countProducts(categoryId);
	}
	
	public ProductDetailVo getDetailById(int id) {
		if(id < 1) {
			return null;
		}
		return productDao.selectDetail(id);
	}
	
	public List<Integer> getImageIds(int id, int type) {
		if(id < 1 || type < 0) {
			return null;
		}
		List<Integer> ids = new ArrayList<>();
		List<Image> images = imageDao.selectByProductId(id, type);
		if(images == null) {
			return null;
		}
		images.stream().forEach(image -> {
			ids.add(image.getId());
		});
		return ids;
	}
	
	public DisplayInfoVo getDisplayInfo(int id){ 
		if(id < 1) {
			return null;
		}
		return productDao.selectDisplayInfo(id);
	}
	
	public int getMainImageId(int id) {
		if(id < 1) {
			return -1;
		}
		return imageDao.selectMainImageId(id);
	}
	
	public int getSubImageId(int id) {
		if(id < 1) {
			return -1;
		}
		return imageDao.selectSubImage(id).getId();
	}
	
	public List<UserCommentVo> getLimitedUserComment(int id, int page, int limit) {
		if (id < 1 || page < 0 || (limit != 3 && limit != 10)) {
			return null;
		}

		int offset = (page-1) * limit;
		List<UserCommentVo> comments = userCommentDao.selectLimitedByProductId(id, offset, limit);
		if(comments == null || comments.size() == 0) {
			return null;
		}

		List<Integer> ids = comments.stream().map(c -> c.getId()).collect(Collectors.toList());
		List<CommentImageVo> commentImageList = imageDao.selectIdByCommentIds(ids);
		Map<Integer, List<Integer>> commentImageMap = commentImageList.stream()
				.collect(Collectors.groupingBy(CommentImageVo::getCommentId, 
						Collectors.mapping(CommentImageVo::getFileId, Collectors.toList())));
		for(UserCommentVo comment : comments) {
			List<Integer> imageList = commentImageMap.get(comment.getId());
			if(imageList != null) {
				comment.setImageId(imageList.get(0));
				comment.setImagesNum(imageList.size());
			}
		}

		return comments;
	}
	
	public CommentsSummaryVo getAvgCommentScore(int id) {
		if(id < 1) {
			return null;
		}
		return userCommentDao.selectAvgScoreByProductId(id);
	}
	
	public ProductReservationVo getReservation(int id) {
		if(id < 1) {
			return null;
		}
		return reservationDao.selectByProductId(id);
	}
	
	public List<Price> getPrices(int id) {
		if(id < 1) {
			return null;
		}
		return priceDao.selectByProductId(id);	
	}
}
