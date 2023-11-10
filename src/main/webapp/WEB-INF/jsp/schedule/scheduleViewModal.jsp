<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제목</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
</head>
<body>
	<div class="scheduleViewModal">
		<div class="calendarModal__title">일정 내용</div>
		<input type="hidden" id="eventId">
		<div class="calendarModal__body">
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">캘린더</div>
				<div class="calendarModal__bodyContent calendarModal__calName">나의
					프로젝트</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">일정 제목</div>
				<div class="calendarModal__bodyContent calendarModal__scheTitle">오전
					회의</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">일정 시간</div>
				<div class="calendarModal__bodyContent calendarModal__scheDate">2023-10-27
					오전 10:00 ~ 오전 10:30</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">최초 등록</div>
				<div class="calendarModal__bodyContent calendarModal__scheReg">대표이사
					(2023-10-24 오후 4:00)</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">반복</div>
				<div class="calendarModal__bodyContent calendarModal__repeatYN">반복
					없음</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">내용</div>
				<div class="calendarModal__bodyContent calendarModal__scheContent">회의하기
					시렁</div>
			</div>
		</div>
		<div class="calendarModal__buttons align-center">
			<button class="calendarModal__delete">삭제</button>
			<div class="buttons__margin"></div>
			<button class="calendarModal__update">수정</button>
			<div class="buttons__margin"></div>
			<button class="calendarModal__close">닫기</button>
		</div>
	</div>

</body>
</html>