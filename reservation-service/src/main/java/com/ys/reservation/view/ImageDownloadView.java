package com.ys.reservation.view;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import com.ys.reservation.domain.Image;

public class ImageDownloadView extends AbstractView {
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Image image = (Image) model.get("image");
		if(image == null) {
			return;
		}
		response.setContentLengthLong(image.getFileLength());
		response.setContentType(image.getContentType());
		response.setHeader("Content-Disposition", "inline; filename=\"" + image.getFileName() + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		
		File f = new File(image.getSaveFileName());
		if (!f.exists()) {
			throw new RuntimeException("file not found");
		}
		try (FileInputStream fis = new FileInputStream(f)) {
			FileCopyUtils.copy(fis, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
