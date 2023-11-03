<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>받은 메일함</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<link rel="stylesheet" href="/css/mail/inBox.css">
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../commons/naviBar.jsp" %>
		<div class="container__inBox">
			<div class="inBox__allCheck">
				<input type="checkbox" class="allCheck__checkbox" />
				<div class="allCheck__checkNum">1</div>
				<div class="allCheck__delete">삭제</div>
				<div class="allCheck__delete">완전삭제</div>
			</div>
			<div class="inBox__mailList">
				<input type="checkbox" class="mailList__checkbox"/>
				<div class="mailList__name">박대표</div>
				<div class="mailList__title">메일 제목</div>
				<div class="mailList__right">
					<i class="fa-solid fa-paperclip right__file"></i>
					<div class="right__time">발송시간</div>
				</div>
			</div>
			<div class="inBox__mailList">
				<input type="checkbox" class="mailList__checkbox"/>
				<div class="mailList__name">박대표</div>
				<div class="mailList__title">메일 제목</div>
				<div class="mailList__right">
					<i class="fa-solid fa-paperclip right__file"></i>
					<div class="right__time">발송시간</div>
				</div>
			</div>
			<div class="inBox__mailList">
				<input type="checkbox" class="mailList__checkbox"/>
				<div class="mailList__name">박대표</div>
				<div class="mailList__title">메일 제목</div>
				<div class="mailList__right">
					<i class="fa-solid fa-paperclip right__file"></i>
					<div class="right__time">발송시간</div>
				</div>
			</div>
			<div class="inBox__mailList">
				<input type="checkbox" class="mailList__checkbox"/>
				<div class="mailList__name">박대표</div>
				<div class="mailList__title">메일 제목</div>
				<div class="mailList__right">
					<i class="fa-solid fa-paperclip right__file"></i>
					<div class="right__time">발송시간</div>
				</div>
			</div>
			<div class="inBox__mailList">
				<input type="checkbox" class="mailList__checkbox"/>
				<div class="mailList__name">박대표</div>
				<div class="mailList__title">메일 제목</div>
				<div class="mailList__right">
					<i class="fa-solid fa-paperclip right__file"></i>
					<div class="right__time">발송시간</div>
				</div>
			</div>
			<hr>
			<div class="inBox__bottom">
				<div class="bottom__mailNum">편지 수 : 5</div>
				<div class="bottom__pageNavi">
					<div class="pageNavi__circle">1</div>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		//	체크박스 전체 선택 클릭 시 
		$(document).on("change", ".allCheck__checkbox", function() {
			let checkAll = $(this).is(":checked");
			if(checkAll) {
				$(".mailList__checkbox").prop("checked", true);
				$(".mailList__checkbox").parent().css("background-color", "#DCEDD4");
			} else {
				$(".mailList__checkbox").prop("checked", false);
				$(".mailList__checkbox").parent().css("background-color", "#FFFFFF");
			}
		})
		
		// 체크박스 개별 클릭 시
		$(document).on("change", ".mailList__checkbox", function() {
			let check = $(this).is(":checked");
			if(check) {
				$(this).prop("checked", true);
				$(this).parent().css("background-color", "#DCEDD4");
			} else {
				$(this).prop("checked", false);
				$(this).parent().css("background-color", "#FFFFFF");
			}
		})
	</script>
</body>
</html>