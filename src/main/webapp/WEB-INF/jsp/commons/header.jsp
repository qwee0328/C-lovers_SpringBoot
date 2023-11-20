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
	
	// 데이터 부르기
	$(document).ready(function(){
		$.ajax({
			url:"/humanResources/headerProfile"
		}).done(function(resp){
			console.log(resp.profile_img);
			
			
			let img;
			
			let main_img = $("<img class='profileImg'/>");
				
			if(resp.profile_img == "" || resp.profile_img == null){
				img = "/assets/profile.png";
			}else{
				img = "/uploads/"+resp.profile_img;
			}
			
				main_img.attr("src",img);
			$(".mainHeader_profileBox").append(main_img);
			
			
			
			let profileImg = $("<img class='header__profileImage'>");
				profileImg.attr("src",img);
				$(".header__profileImageBox").append(profileImg);
				
			$(".profileName").html(resp.name);
			$(".profileEmail").html(resp.email);
					
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