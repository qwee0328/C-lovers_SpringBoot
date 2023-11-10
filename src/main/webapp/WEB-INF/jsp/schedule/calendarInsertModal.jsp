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
		<div class="calendarModal__title">공유 캘린더</div>
		<div class="calendarModal__body">
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle w-15">캘린더 이름</div>
				<div class="calendarModal__bodyContent w-60">
					<input type="text">
				</div>
			</div>
			<div class="calendarModal__bodyLine d-flex">
				<div class="calendarModal__bodyTitle w-15">색깔</div>
				<div class="calendarModal__bodyContent w-60">
					<input type="color" class="colorInput">
				</div>
			</div>
			<div class="calendarModal__bodyLine">
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
						<input type="text">
					</div>
					<button class="bodyContentShare__searchBtn">검색</button>
				</div>
			</div>


			<div class="calendarModal__bodyBottom d-flex">
				<div class="calendarModal__deptList">
					클로버 산업
					<div class="calendarModal__dept">
						<div class="dept__deptName">관리부</div>
						<div class="dept__team">
							<div class="team__teamName">인사팀</div>
							<div class="team__teamName">구매총무팀</div>
							<div class="team__teamName">재무회계팀</div>
						</div>
						<div class="dept__deptName">생산부</div>
						<div class="dept__team">
							<div class="team__teamName">생산1팀</div>
							<div class="team__teamName">생산2팀</div>
							<div class="team__teamName">품질관리팀</div>
						</div>
					</div>

				</div>
				<div class="calendarModal__empAllList">
					<div class="empAllList__empList">
						<div class="empList__emp">강과장 &lt;영업2팀&gt;</div>
						<div class="empList__emp">김이사 &lt;생산1팀&gt;</div>
					</div>
					<div class="empAllList__bottomBtn">
						<button class="empAllList__selectAll">전체선택</button>
						<button class="empAllList__cancelAll">선택취소</button>
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
						조회/등록 권한
						<div class="empSelectList__cnt">&nbsp;2</div>
					</div>
					<div class="empSelectList__empList">
						<div class="empSelectList__emp">강과장</div>
						<div class="empSelectList__emp">대표이사</div>
					</div>
				</div>

			</div>
		</div>
		<div class="calendarModal__buttons align-center">
			<button class="calendarModal__cancel">취소</button>
			<div class="buttons__margin"></div>
			<button class="calendarModal__save">저장</button>
		</div>
	</div>
</body>
</html>