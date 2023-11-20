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
	<div class="calendarInsertModal">
		<div class="calendarModal__title calendarInsertModalTite">공유 캘린더</div>
		<div class="calendarModal__body">
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle w-15 privateCalendarEl1">캘린더 이름</div>
				<div class="calendarModal__bodyContent w-60 privateCalendarEl2">
					<input type="text" class="calendarInsertName">
				</div>
			</div>
			<div class="calendarModal__bodyLine d-flex deleteWhenUpdate">
				<div class="calendarModal__bodyTitle w-15 privateCalendarEl1">색깔</div>
				<div class="calendarModal__bodyContent w-60 privateCalendarEl2">
					<input type="color" class="colorInput">
				</div>
			</div>
			<div class="calendarModal__bodyLine shareCalendarEl shareCalendarEl1">
				<div class="calendarModal__bodyTitle w-15">공유 대상</div>
				<div class="calendarModal__bodyContentShare d-flex">
					<div class="calendarModal__bodyTitle w-15 d-flex">
						<label class="align-center"><input type="radio"
							name="share" value="name">
							<div>이름</div> </label> <label class="align-center"><input
							type="radio" name="share" value="group">
							<div>조직</div> </label>
					</div>

					<div class="calendarModal__bodyContent w-60">
						<input type="text" class="empSearchKeyword">
					</div>
					<button class="bodyContentShare__searchBtn">검색</button>
				</div>
			</div>


			<div class="calendarModal__bodyBottom shareCalendarEl shareCalendarEl2 d-flex">
				<div class="calendarModal__deptList">
					<div class="deptList__title">클로버 산업</div>
					<div class="calendarModal__dept">
						<div class="deptList__companyName selectToggle">
							<i class="fa-solid fa-minus"></i> <span>클로버 산업</span> <span id="officeEmpCount"></span>
						</div>
						<div class="deptList__cover"></div>
						
					</div>

				</div>
				<div class="calendarModal__empAllList">
					<div class="empAllList__empList"></div>
					<div class="empAllList__bottomBtn">
						<button class="empAllList__selectAll">전체</button>
						<button class="empAllList__cancelAll">선택안함</button>
					</div>
				</div>
				<div class="calendarModal__selectIcon align-center">
					<div>
						<div class="selectIcon__select">
							<i class="fa-solid fa-angle-right"></i>
						</div>
						<div class="selectIcon__margin"></div>
						<div class="selectIcon__cancel">
							<i class="fa-solid fa-angle-left"></i>
						</div>
					</div>
				</div>
				<div class="calendarModal__empSelectList">
					<div class="empSelectList__title d-flex">
						조회/등록 권한&nbsp;
						<div class="empSelectList__cnt">1</div>
					</div>
					<div class="empSelectList__empList"></div>
				</div>

			</div>
		</div>
		<div class="calendarModal__buttons align-center">
			<button class="calendarInsertModal__delete">삭제</button>
			<button class="calendarInsertModal__save">저장</button>
			<button class="calendarInsertModal__restore">복원</button>
			<div class="buttons__margin"></div>
			<button class="calendarModal__cancel">취소</button>
		</div>
	</div>
</body>
</html>