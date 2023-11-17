<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA=="
	crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="/css/humanresources/employeeInfo.css">
<script src="/js/humanresources/employeeInfo.js"></script>
<title>임직원정보</title>
</head>
<body>
<%@ include file="../commons/header.jsp" %>
	<div class="employeeInfo__Container">
			<%@ include file="../humanresources/hrNaviBar.jsp" %>
		<div class="employeeInfo__chart">
			<div class="chart__searchBox">
				<div class="searchBox">
					<div>
						<i class="fa-solid fa-magnifying-glass"></i>
					</div>
					<input type="text" class="em_search" placeholder="임직원 검색">
				</div>
				<div class="myPageBtnBox">
					<button class="myPageBtn">내 정보 보기</button>
				</div>
			</div>
			<div class="chart_content">
				<div class="chart_content_left">
					<div class="left__innerBox">
						<div class="innerBox__title cloverEmployee">클로버 산업</div>
						<div class="innerBox">
							<div class="manageBox">
								<div class="manageTitle Box__title manageToggle"
									toggleView="false">
									<div class="naviIcon">
										<i class="fa-solid fa-plus"></i>
									</div>
									<div class="Title">관리부</div>
								</div>
								<div class="ContentBox manageInner">
									<div class="manageContent">인사팀</div>
									<div class="manageContent">구매총무팀</div>
									<div class="manageContent">재무회계팀</div>
								</div>
							</div>
							<div class="productBox">
								<div class="productTitle Box__title productToggle"
									toggleView="false">
									<div class="naviIcon">
										<i class="fa-solid fa-plus"></i>
									</div>
									<div class="Title">생산부</div>
								</div>
								<div class="ContentBox productInner">
									<div class="productContent">생산1팀</div>
									<div class="productContent">생산2팀</div>
									<div class="productContent">품질관리팀</div>
								</div>
							</div>
							<div class="saleBox">
								<div class="saleTitle Box__title saleToggle" toggleView="false">
									<div class="naviIcon">
										<i class="fa-solid fa-plus"></i>
									</div>
									<div class="Title">영업부</div>
								</div>
								<div class="ContentBox saleInner">
									<div class="saleContent">영업1팀</div>
									<div class="saleContent">영업2팀</div>
									<div class="saleContent">고객지원팀</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="chart_content_right">
					<div class="org__members">
						<div class="members__Title">
							<h4>하이웍스산업</h4>
						</div>
						<div class="members__profile">
							<div class="member__unitBox">
								<div class="memberPhoto">
									<img src="../image.jpg" alt="">
								</div>
								<div class="memberText">
									<div class="member__name">최사장</div>
									<div class="member__depart">인사팀</div>
									<div class="member__grade">사장</div>
								</div>
							</div>

							<div class="member__unitBox">
								<div class="memberPhoto">
									<img src="../image.jpg" alt="">
								</div>
								<div class="memberText">
									<div class="member__name">최사장</div>
									<div class="member__depart">인사팀</div>
									<div class="member__grade">사장</div>
								</div>
							</div>


						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>