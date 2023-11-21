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
	<div class="scheduleInsertModal">
		<div class="calendarModal__title modalName">일정 추가</div>
		<div class="calendarModal__body">
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">캘린더</div>
				<div class="calendarModal__bodyContent">
					<select class="calendarModal__calNameList" name="calendarNameList"></select>
				</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">일정 제목</div>
				<div class="calendarModal__bodyContent">
					<input type="text" class="insertSchedule__title">
				</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">시작</div>
				<div
					class="calendarModal__bodyContent calendarModal__dateInfo d-flex">
					<input type="date" class="insertSchedule__startDate"><input
						type="time" class="insertSchedule__startTime"><label><input
						type="checkbox" class="insertSchedule__allDay">
						<div class="checkbox__content">종일</div> </label>
				</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">종료</div>
				<div
					class="calendarModal__bodyContent calendarModal__dateInfo d-flex">
					<input type="date" class="insertSchedule__endDate"><input
						type="time" class="insertSchedule__endTime"><label><input
						type="checkbox" class="insertSchedule__repeat">
						<div class="checkbox__content">반복</div> </label>
				</div>
			</div>
			<div class="calendarModal__bodyLine d-none bodyLine__repeat">
				<div class="calendarModal__bodyTitle">반복 빈도</div>
				<div class="calendarModal__bodyContent">
					<div class="insertSchedule__frequency d-flex">
						<select class="frequency__when">
							<option value="daily">매일</option>
							<option value="weekly">매주</option>
							<option value="weekDay" class="weekDayOption">매주 월-금</option>
							<option value="monthly">매월</option>
							<option value="yearly">매년</option>
						</select> <select class="frequency__every"></select>
						<div class="frequency__txt"></div>
					</div>
					<div class="insertSchedule__repeatChk d-flex">
						<input type="checkbox" name="week" id="repeatChk__mon" value=0><label
							for="repeatChk__mon">월</label> <input type="checkbox" name="week"
							id="repeatChk__tue" value=1><label for="repeatChk__tue">화</label>
						<input type="checkbox" name="week" id="repeatChk__wed" value=2><label
							for="repeatChk__wed">수</label> <input type="checkbox" name="week"
							id="repeatChk__thu" value=3><label for="repeatChk__thu">목</label>
						<input type="checkbox" name="week" id="repeatChk__fri" value=4><label
							for="repeatChk__fri">금</label> <input type="checkbox" name="week"
							id="repeatChk__sat" value=5><label for="repeatChk__sat">토</label>
						<input type="checkbox" name="week" id="repeatChk__sun" value=6><label
							for="repeatChk__sun">일</label>
					</div>
				</div>
			</div>
			<div class="calendarModal__bodyLine d-none bodyLine__repeat">
				<div class="calendarModal__bodyTitle">반복 범위</div>
				<div class="calendarModal__bodyContent">
					<div class="insertSchedule__range d-flex">
						<div class="h-center">
							<input type="radio" name="period" value="count" id="count">
							<label for="count"><input type="text"
								class="range__count" value="1">번</label>
						</div>
						<div class="h-center ml-15">
							<input type="radio" name="period" value="until" id="endDate">
							<label for="endDate">반복 종료일<input type="date"
								class="range__endDate"></label>
						</div>
					</div>
				</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle">내용</div>
				<div class="calendarModal__bodyContent insertSchedule__content"
					contenteditable="true">dd</div>
				<textarea class="d-none" id="insertSchedule__content"></textarea>
			</div>
		</div>
		<div class="calendarModal__buttons align-center">
			
			<button class="calendarModal__save">저장</button>
			<div class="buttons__margin"></div>
			<button class="calendarModal__updateSave">수정</button>
			<div class="buttons__margin"></div>
			<button class="calendarModal__cancel">취소</button>
			
		</div>
	</div>

</body>
</html>