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

<!-- css, js -->
<link rel="stylesheet" href="/css/commons/header.css">
<script src="/js/commons/header.js"></script>
</head>
<body>
	<div class="header">
		<div class="headerLeft">
			<div class="headerLeft__logo">C-lovers</div>
			<c:choose>
				<c:when test="${title != '오피스 홈' }">
					<div class="headerLeft__dropNav dropNav_hover" >
						${title }
						<i class="fa-solid fa-caret-down dropNavi__icon"></i>
					</div>
				</c:when>
				<c:otherwise>
					<div class="headerLeft__dropNav">${title }</div>
				</c:otherwise>
			</c:choose>
			
			<!-- 민경 -->
			<div class="headerLeft__dropNavBox">
				<div class="dropNavBox">
				
					<c:choose>
						<c:when test="${title eq '메일' }">
							<div class="dropNavBox__inner" style="background-color: #DCEDD4;">
								<div class="inner__icon">
									<i class="fa-regular fa-envelope"></i>
								</div>
								<div class="inner__title">메일</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="dropNavBox__inner">
							<div class="inner__icon">
								<i class="fa-regular fa-envelope"></i>
							</div>
							
							<div class="inner__title">메일</div>
							
						</div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${title eq '일정' }">
							<div class="dropNavBox__inner" style="background-color: #DCEDD4;">
								<div class="inner__icon">
									<i class="fa-regular fa-calendar"></i>
								</div>
								<div class="inner__title">일정</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="dropNavBox__inner">
							<div class="inner__icon">
								<i class="fa-regular fa-calendar"></i>
							</div>
							
							<div class="inner__title">일정</div>
							
						</div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${title eq '전자결재' }">
							<div class="dropNavBox__inner" style="background-color: #DCEDD4;">
								<div class="inner__icon">
									<i class="fa-regular fa-file-lines"></i>
								</div>
								<div class="inner__title">전자결재</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="dropNavBox__inner">
							<div class="inner__icon">
								<i class="fa-regular fa-file-lines"></i>
							</div>
							
							<div class="inner__title">전자결재</div>
							
						</div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${title eq '회계지원' }">
							<div class="dropNavBox__inner" style="background-color: #DCEDD4;">
								<div class="inner__icon">
									<i class="fa-solid fa-credit-card"></i>
								</div>
								<div class="inner__title">회계지원</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="dropNavBox__inner">
							<div class="inner__icon">
								<i class="fa-solid fa-credit-card"></i>
							</div>
							
							<div class="inner__title">회계지원</div>
							
						</div>
						</c:otherwise>
					</c:choose>

					
<!-- 					<div id="accountingController__small" class="dropNavBox__inner "> -->
<!-- 						<div class="inner__icon"> -->
<!-- 							<i class="fa-solid fa-credit-card"></i> -->
<!-- 						</div> -->
<!-- 						<div class="inner__title">회계지원</div> -->
<!-- 					</div> -->
				</div>
				
				
				<div class="dropNavBox">
				
					<div class="dropNavBox__inner">
						<div class="inner__icon">
							<i class="fa-regular fa-clipboard"></i>
						</div>
						<div class="inner__title">게시판</div>
					</div>
					
					<c:choose>
						<c:when test="${title eq '주소록' }">
							<div class="dropNavBox__inner" style="background-color: #DCEDD4;">
								<div class="inner__icon">
									<i class="fa-regular fa-address-book"></i>
								</div>
								<div class="inner__title">주소록</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="dropNavBox__inner">
							<div class="inner__icon">
								<i class="fa-regular fa-address-book"></i>
							</div>
							
							<div class="inner__title">주소록</div>
							
						</div>
						</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${title eq '인사' }">
							<div class="dropNavBox__inner" style="background-color: #DCEDD4;">
								<div class="inner__icon">
									<i class="fa-solid fa-sitemap"></i>
								</div>
								<div class="inner__title">인사</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="dropNavBox__inner">
							<div class="inner__icon">
								<i class="fa-solid fa-sitemap"></i>
							</div>
							
							<div class="inner__title">인사</div>
							
						</div>
						</c:otherwise>
					</c:choose>
					
					
					
					<div id="officeController__small" class="dropNavBox__inner">
						<div class="inner__icon">
							<i class="fa-solid fa-gear"></i>
						</div>
						<div class="inner__title">오피스 관리</div>
					</div>
					
				</div>
			</div>



		</div>
		<div class="headerRight">
			<div id="goChat">
				<i class="fa-regular fa-comment"></i>
			</div>
			<!-- 
			<div>
				<i class="fa-regular fa-bell"></i>
			</div>
			 -->
			<div class="mainHeader_profileBox">
<!-- 				<img src="/assets/profile.png" alt="" class="profileImg"/> -->
			</div>
		</div>
	</div>
	
	<!-- 민경 추가부분 -->
	<div class="header__userinfoBox">
			<div class="header__userinfo">
				<div class="header__myinfo">
	
					<div class="header__profileImageBox">
						<!-- 이미지 -->
<!-- 						<img src="/uploads/" alt="" class="header__profileImage"> -->
					</div>
	
					<div class="header__profileInfoBox">
						<div class="profileName">
						<!-- 이름 -->
						</div>
	
						<div class="profileEmail">
							<!-- 이메일 -->
						</div>
					</div>
	
				</div>
	
				<div class="header__myinfoBottom">
	
					<div class="header__setting">
	                    <a href="/humanResources/mypage">설정</a>
	                </div>
	
					<div class="header__logoutBtnBox">
						<button><a href="/members/logout">로그아웃</a></button>
					</div>
				</div>
			</div>
	</div>
</body>

<script>
	// 로고 클릭시 홈으로 이동
	$(document).on("click", ".headerLeft__logo", function() {
		location.href = "/";
	})
	
	$(".dropNavi__icon").on("click", function() {
		
	})
	
	// 로고옆에 클릭시
	$(".headerLeft__dropNav").on("click",function(){
		
	});
</script>
</html>