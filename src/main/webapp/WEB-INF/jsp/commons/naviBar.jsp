<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>NaviBar</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<link rel="stylesheet" href="/css/commons/naviBar.css">

</head>
<body>
	<div class="naviBar">
		<div class="naviConp">
			<div class="naviConp__icon">
				<i class="fa-solid fa-inbox"></i>
			</div>
			<div class="naviConp__title">받은 편지함</div>
		</div>
		<div class="naviConp">
			<div class="naviConp__icon">
				<i class="fa-solid fa-paper-plane"></i>
			</div>
			<div class="naviConp__title">보낸 편지함</div>
		</div>
		<div class="naviConp">
			<div class="naviConp__icon">
				<i class="fa-solid fa-box-archive"></i>
			</div>
			<div class="naviConp__title">임시 편지함</div>
		</div>
		<div class="naviConp">
			<div class="naviConp__icon">
				<i class="fa-solid fa-clock"></i>
			</div>
			<div class="naviConp__title">보낼 편지함</div>
		</div>
		<div class="naviConp">
			<div class="naviConp__icon">
				<i class="fa-solid fa-trash"></i>
			</div>
			<div class="naviConp__title">휴지통</div>
		</div>
	</div>
</body>
</html>