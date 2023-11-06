<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<!-- <script src="https://code.jquery.com/jquery-3.7.1.js"></script> -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<link rel="stylesheet" href="/css/commons/header.css">
</head>
<body>
	<div class="header">
		<div class="headerLeft">
			<div class="headerLeft__logo">C-lovers</div>
			<c:choose>
				<c:when test="${title != '오피스 홈' }">
					<div class="headerLeft__dropNav">
						${title }
						<i class="fa-solid fa-caret-down dropNavi__icon"></i>
					</div>
				</c:when>
				<c:otherwise>
					<div class="headerLeft__dropNav">${title }</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="headerRight">
			<div>
				<i class="fa-regular fa-comment"></i>
			</div>
			<div>
				<i class="fa-regular fa-bell"></i>
			</div>
			<div>
				<img src="/assets/profile.png" alt="" class="profileImg"/>
			</div>
		</div>
	</div>
</body>

<script>
	$(".dropNavi__icon").on("click", function() {
		
	})
</script>
</html>