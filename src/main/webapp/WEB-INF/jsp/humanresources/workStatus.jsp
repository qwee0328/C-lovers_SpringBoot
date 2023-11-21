<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>근무 현황</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet" href="/css/humanresources/workStatus.css">
<script type="text/javascript" src="/js/humanresources/workStatus.js"></script>
</head>
<body>
	<%@ include file="../commons/header.jsp"%>
	<div class="container">
		<%@ include file="../humanresources/hrNaviBar.jsp"%>
		<div class="workStatusBox">
			<ul class="tabs">
				<li class="tab-link current" data-tab="workStatus">근무현황</li>
				<li class="tab-link" data-tab="vacationDetails">휴가내역</li>
				<li class="tab-link" data-tab="companyVacation">전사 휴가 캘린더</li>
			</ul>

			<div id="workStatus" class="tab-content current">tab content1</div>
			<div id="vacationDetails" class="tab-content">
				<input type="hidden" id="totalCountHidden">
				<input type="hidden" id="usedCountHidden">
				<div class="settings">
					<div class="settings__left">
						<div class="arrow" id="leftArrow">
							<i class="fa-solid fa-chevron-left"></i>
						</div>
						<div class="yearInfo">2023-01-01 ~ 2023-12-31</div>
						<div class="arrow" id="rightArrow">
							<i class="fa-solid fa-chevron-right"></i>
						</div>
					</div>
					<div class="settings__right">
						<button class="whiteBtn" id="vacationAppSmallBtn">휴가신청</button>
					</div>
				</div>
				<div class="vacationCreation">
					<div class="summaryInfo">
						<div class="summaryInfo__title">휴가 현황</div>
						<div class="summaryInfo__conf">
							<div id="totalAnnaul">총 휴가 : 20일</div>
							<div id="usedAnnaul">사용 : 1일</div>
							<div id="remainingCnt">잔여 : 19일</div>
						</div>
					</div>
					<div class="vacationCreation__table">
						<div class="table__header">
							<div class="header__conf">생성일</div>
							<div class="header__conf">
								<div>생성내역</div>
								<div>
									<div>발생</div>
									<div>최종</div>
								</div>
							</div>
							<div class="header__conf">내용</div>
							<div class="header__conf">비고</div>
						</div>
						<div class="table__body">
							<div class="table__line">
								<!-- 
								<div class="body__conf" id="insertDate"></div>
								<div class="body__conf">
									<div>1</div>
									<div>1</div>
								</div>
								<div class="body__conf"></div>
								<div class="body__conf"></div>
								 -->
							</div>

						</div>
					</div>
				</div>
				<div class="applicationDetails">
					<div class="summaryInfo">
						<div class="summaryInfo__title">휴가 신청 내역</div>
						<div class="summaryInfo__conf">
							<div>
								<button id="detailAll">전체</button>
							</div>
							<div>
								<button id="detailYear">1년</button>
							</div>
							<div>
								<button id="detailMonth">한달</button>
							</div>
							<div>
								<button id="detailWeek">1주일</button>
							</div>
						</div>
					</div>
					<div class="applicationDetails__table">
						<div class="details__header">
							<div class="header__td">번호</div>
							<div class="header__td">신청자</div>
							<div class="header__td">휴가종류</div>
							<div class="header__td">일수</div>
							<div class="header__td">기간</div>
							<div class="header__td">상태</div>
							<div class="header__td">상세</div>
						</div>
						<div class="detailes__body">
							<div class="body__line">
								<div class="body__td">1</div>
								<div class="body__td">대표이사</div>
								<div class="body__td">연차</div>
								<div class="body__td">1일</div>
								<div class="body__td periodTd">기간</div>
								<div class="body__td">결재완료</div>
								<div class="body__td">상세</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div id="companyVacation" class="tab-content">tab content3</div>
		</div>
	</div>
</body>
</html>