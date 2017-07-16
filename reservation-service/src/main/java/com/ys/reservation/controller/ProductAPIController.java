package com.ys.reservation.controller;

import java.io.FileInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ys.reservation.domain.FileDomain;
import com.ys.reservation.domain.Product;
import com.ys.reservation.service.ProductService;
import com.ys.reservation.vo.ProductDetailVo;
import com.ys.reservation.vo.ProductVo;

@RestController
@RequestMapping("/api/products")
public class ProductAPIController {
	@Autowired
	private ProductService productService;
	
	@GetMapping
	public List<ProductVo> getAll() {
		return productService.getAllProducts();
	}

	@GetMapping("/pages/{page}")
	public List<ProductVo> getByPage(@PathVariable int page) {
		return productService.limitedGet(page);
	}
	
	@GetMapping
	@RequestMapping("/{id}")
	public Product getById(@PathVariable int id) {
		return productService.getById(id);
	}
	
	@GetMapping
	@RequestMapping("/count")
	public int getCount() {
		return productService.getCount();
	}
	
	@GetMapping
	@RequestMapping("/categories/{categoryId}/pages/{page}")
	public List<ProductVo> limitedGetByPageAndCategoryId(@PathVariable int categoryId, @PathVariable int page) {
		return productService.limitedGetByCategoryId(categoryId, page);
	}
	
	@GetMapping
	@RequestMapping("/categories/{categoryId}/count")
	public int getCountByCategoryId(@PathVariable int categoryId) {
		return productService.getCountByCategoryId(categoryId);
	}
	
	@GetMapping
	@RequestMapping("/{id}/detail")
	public ProductDetailVo getDetailById(@PathVariable int id) {
		return productService.getDetailById(id);
	}
	
	@GetMapping
	@RequestMapping("/{id}/files")
	public List<FileDomain> getProductFiles(@PathVariable int id, @RequestParam int type, HttpServletResponse response) {
		List<FileDomain> files = productService.getFiles(id, type);
		return files;
//		for(FileDomain file : files){
//			System.out.println(file.getSaveFileName());
//			String originalFileName = file.getFileName();
//			String contentType = file.getContentType();
//			long fileSize = file.getFileLength();
//			String saveFileName = file.getSaveFileName();
//			
//			response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFileName + "\";");
//	        response.setHeader("Content-Transfer-Encoding", "binary");
//	        response.setHeader("Content-Type", contentType);
//	        response.setHeader("Content-Length", ""+ fileSize);
//	        response.setHeader("Pragma", "no-cache;");
//	        response.setHeader("Expires", "-1;");
//
//	        java.io.File readFile = new java.io.File(saveFileName);
//	        if(!readFile.exists()){ // 파일이 존재하지 않다면
//	            throw new RuntimeException("file not found");
//	        }
//
//	        try(FileInputStream fis = new FileInputStream(readFile)){
//	            FileCopyUtils.copy(fis, response.getOutputStream()); // 파일을 저장할때도 사용할 수 있다.
//	            response.getOutputStream().flush();
//	        }catch(Exception ex){
//	            throw new RuntimeException(ex);
//	        }
//		}
	}
}
