<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<link rel="stylesheet" href="/css/humanresources/employeeInfo.css">
<script src="/js/humanresources/employeeInfo.js"></script>
<title>임직원정보</title>
</head>
<body>
<%@ include file="../commons/header.jsp" %>
	<div class="employeeInfo__Container">
		<div class="naviBar">
			<%@ include file="../humanresources/hrNaviBar.jsp" %>
		</div>
		<div class="employeeInfo__chart">
			<div class="chart__searchBox">
				<div class="searchBox">
					<div>
						<i class="fa-solid fa-magnifying-glass"></i>
					</div>
					<input type="text" class="em_search" placeholder="임직원 검색">
				</div>
				<div class="myPageBtnBox">
					<button type="button" class="myPageBtn"><a href="/humanResources/mypage">내 정보 보기</a></button>
				</div>
			</div>
			<div class="chart_content">
				 <div class="chart_content_left">
                    <div class="left__innerBox">
                    	<!-- 부서정보 -->
                    </div>
                </div>
				<div class="chart_content_right">
					<div class="org__members">
						<!-- 직원정보 -->
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>