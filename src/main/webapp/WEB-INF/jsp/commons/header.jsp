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
			<div class="mainHeader_profileBox">
				<img src="/assets/profile.png" alt="" class="profileImg"/>
			</div>
		</div>
	</div>
	
	<!-- 민경 추가부분 -->
	<div class="header__userinfoBox">
<!-- 		프로필 -->
	</div>
	
</body>

<script>
	// 로고 클릭시 홈으로 이동
	$(document).on("click", ".headerLeft__logo", function() {
		location.href = "/";
	})
	
	$(".dropNavi__icon").on("click", function() {
		
	})
	
	// 데이터 부르기
	$(document).ready(function(){
		$.ajax({
			url:"/humanResources/headerProfile"
		}).done(function(resp){
			console.log(resp);
			
				let header__userinfo = $("<div class='header__userinfo'>");
					let header_myinfo = $("<div class='header__myinfo'>");
					
						let header__profileImageBox = $("<div class='header__profileImageBox'>");
							let profileImg = $("<img class='header__profileImage'>");
								profileImg.attr("src","/uploads/"+resp.profile_img);
						header__profileImageBox.append(profileImg);
						
						let header__profileInfoBox = $("<div class='header__profileInfoBox'>");
							let profileName = $("<div class='profileName'>");
								profileName.html(resp.name);
							let profileEmail = $("<div class='profileEmail'>");
								profileEmail.html(resp.email);
								
						header__profileInfoBox.append(profileName).append(profileEmail);
						
					header_myinfo.append(header__profileImageBox).append(header__profileInfoBox);
					
					let header__myinfoBottom = $("<div class='header__myinfoBottom'>");
						
						let header__setting = $("<div class='header__setting'>");
							header__setting.append("<a href='/humanResources/mypage'>설정</a>");
							
						let header__logoutBtnBox = $("<div class='header__logoutBtnBox'>");
								let btn = $("<button><a href='/members/logout'>로그아웃</a></button>");
								
							header__logoutBtnBox.append(btn);
							
						header__myinfoBottom.append(header__setting).append(header__logoutBtnBox);
						
					header__userinfo.append(header_myinfo).append(header__myinfoBottom);
					
				$(".header__userinfoBox").append(header__userinfo);	
						
			
		});
	})
	
	// 민경 추가
	$(document).on("click",".mainHeader_profileBox",function(){
		if($(".header__userinfoBox").hasClass("active")){
			$(".header__userinfoBox").removeClass("active");

			
		}else{
			$(".header__userinfoBox").addClass("active");
		}
	});
	
</script>
</html>