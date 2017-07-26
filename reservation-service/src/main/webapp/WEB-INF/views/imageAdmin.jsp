<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="/admin/images" enctype="multipart/form-data">
		<input type="radio" name="relation" value="production" checked>상품 대표이미지
		<input type="radio" name="relation" value="production_sub">상품 부가이미지
		<input type="radio" name="relation" value="review">리뷰 이미지
		<br>
		<input type="number" name="id" placeholder="id" value="1">
		<br>
		<input type="file" name="file">
		<br>
		<input type="submit" value="등록">
	</form>
</body>
</html>
