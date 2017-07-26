package com.ys.reservation.service;

import java.util.ArrayList;
import java.util.List;

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
import com.ys.reservation.vo.CommentsSummaryVo;
import com.ys.reservation.vo.DisplayInfoVo;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductReservationInfoVo;
import com.ys.reservation.vo.ProductVo;
import com.ys.reservation.vo.UserCommentVo;

@Service
public class ProductService {
	private ProductDao productDao;
	private CategoryDao categoryDao;
	private ImageDao fileDao;
	private UserCommentDao userCommentDao;
	private PriceDao priceDao;
	private ReservationDao reservationDao;
	
	@Autowired
	public ProductService(ProductDao productDao, CategoryDao categoryDao, ImageDao fileDao,
			UserCommentDao userCommentDao, PriceDao priceDao, ReservationDao reservationDao) {
		this.productDao = productDao;
		this.categoryDao = categoryDao;
		this.fileDao = fileDao;
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
	
	public Product getById(int id) {
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
	
	public List<Integer> getFileIds(int id, int type) {
		if(id < 1 || type < 0) {
			return null;
		}
		List<Integer> ids = new ArrayList<>();
		List<Image> files = fileDao.selectByProductId(id, type);
		if(files == null) {
			return null;
		}
		for(Image fileDomain : files) {
			ids.add(fileDomain.getId());
		}
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
		return fileDao.selectMainImageId(id);
	}
	
	public int getSubImageId(int id) {
		if(id < 1) {
			return -1;
		}
		return fileDao.selectSubImage(id).getId();
	}
	
	public List<UserCommentVo> getUserComment(int id) {
		if(id < 1) {
			return null;
		}
		List<UserCommentVo> comments = userCommentDao.selectByProductId(id);
		if(comments == null) {
			return null;
		}
		for(UserCommentVo comment : comments) {
			List<Image> files = userCommentDao.selectFiles(comment.getId());
			if(files != null && files.size() > 0) {
				comment.setFileId(files.get(0).getId());
				comment.setFilesNum(files.size());
			}
			
		}
		if(comments.size() > 3) {
			return comments.subList(0, 3);
		}
		return comments;
	}
	
	public CommentsSummaryVo getAvgCommentScore(int id) {
		if(id < 1) {
			return null;
		}
		return userCommentDao.selectAvgScoreByProductId(id);
	}
	
	public ProductReservationInfoVo getReservationInfo(int id) {
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
