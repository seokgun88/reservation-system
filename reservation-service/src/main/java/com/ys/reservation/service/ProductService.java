package com.ys.reservation.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ys.reservation.dao.FileDao;
import com.ys.reservation.dao.PriceDao;
import com.ys.reservation.dao.ProductDao;
import com.ys.reservation.dao.UserCommentDao;
import com.ys.reservation.domain.FileDomain;
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
	private FileDao fileDao;
	private UserCommentDao userCommentDao;
	private PriceDao priceDao;
	
	@Autowired
	public ProductService(ProductDao productDao, FileDao fileDao, UserCommentDao userCommentDao, PriceDao priceDao) {
		this.productDao = productDao;
		this.fileDao = fileDao;
		this.userCommentDao = userCommentDao;
		this.priceDao = priceDao;
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
		return productDao.countByCategoryId(categoryId);
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
		for(FileDomain fileDomain : productDao.selectFiles(id, type)) {
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
		List<UserCommentVo> comments = productDao.selectUserComment(id);
		for(UserCommentVo comment : comments) {
			List<FileDomain> files = userCommentDao.selectFiles(comment.getId());
			if(files.size() > 0) {
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
		return productDao.selectAvgCommentScore(id);
	}
	
	public ProductReservationInfoVo getReservationInfo(int id) {
		if(id < 1) {
			return null;
		}
		return productDao.selectReservationInfo(id);
	}
	
	public List<Price> getPrices(int id) {
		if(id < 1) {
			return null;
		}
		return priceDao.selectByProductId(id);	
	}
}
