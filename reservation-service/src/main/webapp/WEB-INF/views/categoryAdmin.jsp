<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="/resources/css/categoryAdmin.css">
<title>카테고리 관리자 페이지</title>
</head>
<body>
<div id="main_div">
	<h1>카테고리 관리</h1>
	<form method="post" action="/admin/categories">
	    <input type="text" name="name" id="name" placeholder="새로운 카테고리를 입력하세요." size="30" autofocus>
	</form>
	<ul id="category_list">
		<c:forEach items="${categories}" var="category">
			<li class="category" data-id=${category.id}>
				<input class="categoryName" type="text" value="${category.name}" readonly>
				<button type="button" class="modifyBtn">수정</button>
				<button type="button" class="modifyConfirmBtn">확인</button>
				<button type="button" class="modifyCancleBtn">취소</button>
				<button type="button" class="removeBtn">삭제</button>
			</li>
		</c:forEach>
	</ul>
</div>
<script data-main="/resources/js/categoryAdmin.js" src="/resources/js/node_modules/requirejs/require.js"></script>
</body>
</html>
