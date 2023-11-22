<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${title}</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<script
	src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.9/index.global.js'></script>
<script
	src='https://cdn.jsdelivr.net/npm/@fullcalendar/google-calendar@6.1.9/index.global.min.js'></script>
<script
	src='https://cdn.jsdelivr.net/npm/rrule@2.6.4/dist/es5/rrule.min.js'></script>
<script
	src='https://cdn.jsdelivr.net/npm/@fullcalendar/rrule@6.1.9/index.global.min.js'></script>
<script
	src='https://cdn.jsdelivr.net/npm/moment@2.27.0/min/moment.min.js'></script>
<script
	src='https://cdn.jsdelivr.net/npm/@fullcalendar/moment@6.1.9/index.global.min.js'></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script type="text/javascript" src="/js/schedule/schedule.js"></script>
<script type="text/javascript" src="/js/schedule/scheduleSearch.js"></script>

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<link rel="stylesheet" href="/css/schedule/scheduleModal.css">
<link rel="stylesheet" href="/css/schedule/scheduleMain.css">
<link rel="stylesheet" href="/css/schedule/scheduleCalendar.css">
<link rel="stylesheet" href="/css/schedule/scheduleSearch.css">


<title>캘린더 테스트</title>
</head>

<body>
	<input type="hidden" id="calIds" value="${calIds}">
	<%@ include file="../commons/header.jsp"%>

	<div class="container d-flex">
		<%@ include file="../schedule/schNaviBar.jsp"%>
		
		<div class="calenderBody">
			<div class="covers">
				<div id="calendarHide"></div>
				<div id="calendar"></div>
				<div id="calendarSearch"></div>
				<div class="inputSearchDate d-flex">
					<div><label>시작일 <input type="date" data-type="start" class="inputSearchDate_start" value="2023-08-11"></label></div>
					<div class="inputSearchDate__margin">-</div>
					<div><label>종료일 <input type="date" data-type="end" class="inputSearchDate_end" value="2023-12-12"></label></div>
				</div>
			</div>
		</div>
		<div id="searchTag">
			<div class="addBook__header d-flex">
			<div class="header__searchBar d-flex">
				<div class="searchBar__inputCover d-flex">
					<input type="text" class="searchBar__input" placeholder="제목, 내용으로 검색">
				</div>
				<div class="searchBar__icon searchBar__search">
					<i class="fa-solid fa-magnifying-glass align-center"></i>
				</div>
			</div>
			<div class="header__tagName align-center"></div>
		</div>
		
		

		<!-- 일정 추가 모달창 -->
		<%@ include file="../schedule/scheduleInsertModal.jsp"%>
		
		<!-- 일정 확인 모달창 -->
		<%@ include file="../schedule/scheduleViewModal.jsp"%>

		<!-- 캘린더 추가 모달창 -->
		<%@ include file="../schedule/calendarInsertModal.jsp"%>
	</div>


</body>
</html>