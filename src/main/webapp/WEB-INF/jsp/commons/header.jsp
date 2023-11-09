<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Header</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
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
	
	<!-- 민경 추가부분 -->
	<div class="header__userinfoBox">
		<div class="header__userinfo">
			<div class="header__myinfo">

				<div class="header__profileImageBox">
					<img src="/assets/profile.png" alt="" class="header__profileImage">
				</div>

				<div class="header__profileInfoBox">
					<div class="profileName">
						이사원
					</div>

					<div class="profileEmail">
						lee@clovers.com
					</div>
				</div>

			</div>

			<div class="header__myinfoBottom">

				<div class="header__setting"><a href="/humanResources/mypage">설정</a></div>

				<div class="header__logoutBtnBox">
					<button><a href="/members/logout">로그아웃</a></button>
				</div>
			</div>
		</div>
	</div>
	
</body>

<script>
	$(".dropNavi__icon").on("click", function() {
		
	})
	
	// 민경 추가
	// 프로필 이미지 누르면 창 뜨게 바깥 누르면 닫힘
	$(document).click(function (e) {
		if (e.target.className == "profileImg") {
			$(".header__userinfoBox").toggle(
				function () {
					$(".header__userinfoBox").addClass("show");
				},
				function () {
					$(".header__userinfoBox").addClass("hide");
				}
			)
		}else if($(".header__userinfoBox").has(e.target).length>0){
			return true;
		}else{
			$(".header__userinfoBox").css("display", "none");
		}
	})
</script>
</html>