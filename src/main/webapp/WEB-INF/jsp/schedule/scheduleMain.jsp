<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제목</title>
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

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<link rel="stylesheet" href="/css/commons/naviBar.css">
<link rel="stylesheet" href="/css/schedule/scheduleModal.css">
<link rel="stylesheet" href="/css/schedule/scheduleMain.css">
<link rel="stylesheet" href="/css/schedule/scheduleCalendar.css">

<title>캘린더 테스트</title>
</head>

<body>
	<%@ include file="../commons/header.jsp"%>

	<div class="container d-flex">
		<div class="naviBar">
			<c:if test="${naviBtn != '' }">
				<div class="naviBtn">
					<i class="fa-solid fa-plus naviBtn__icon"></i>
					<div class="naviBtn__text">${naviBtn }</div>
					<input type="hidden" id="location" value="${naviBtnLocation }" />
				</div>
			</c:if>
			<c:forEach var="i" begin="0" end="${naviMenuLength - 1 }">
				<div class="naviConp">
					<div class="naviConp__icon">
						<i class="fa-solid ${naviIcon[0] }"></i>
					</div>
					<div class="naviConp__title">${naviMenu[i] }</div>
					<div class="naviConp__icon">
						<i class="fa-solid ${naviIcon[1] }"></i>
					</div>
				</div>
			</c:forEach>
			<input type="hidden" id="currentMenu" value="${currentMenu }" />
		</div>


		<div class="calenderBody">
			<div id="calendar"></div>
		</div>

		<!-- 일정 추가 모달창 -->
		<%@ include file="../schedule/scheduleInsertModal.jsp"%>
		
		<!-- 일정 확인 모달창 -->
		<%@ include file="../schedule/scheduleViewModal.jsp"%>

		<!-- 캘린더 추가 모달창 -->
		<%@ include file="../schedule/scheduleViewModal.jsp"%>
	</div>


</body>
</html>