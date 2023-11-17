<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>휴가 신청</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<script type="text/javascript"
	src="/js/humanresources/vacationApplication.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />

<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet"
	href="/css/humanresources/vacationApplication.css">
<script type="text/javascript" src="/js/humanresources/flatpickr.js"></script>

</head>
<body>
	<input type="hidden" value="휴가신청" id="modalType">
	<input type="hidden" id="processEmployeeList">
	<input type="hidden" id="processEmployeeIDList">
	<%@ include file="../commons/header.jsp"%>
	<div class="container">
		<%@ include file="../humanresources/hrNaviBar.jsp"%>
		<div class="vacationApp">
			<div class="draftingBtn">
				<button class="noneBackgroundBtn" id="vacationdraftingBtn">기안하기</button>
			</div>
			<div class="vacationApp__body">
				<div class="basicSetting">
					<div class="title">기본설정</div>
					<div class="basicSetting__table">
						<div class="table__row">
							<div class="table__header">문서 종류</div>
							<div class="table__td">휴가 신청서</div>
							<div class="table__header">작성자</div>
							<div class="table__td">하이웍스 대표이사</div>
						</div>
						<div class="table__row">
							<div class="table__header">보존 연한</div>
							<div class="table__td">3년</div>
							<div class="table__header">보안등급</div>
							<div class="table__td">B등급</div>
						</div>
					</div>
				</div>
				<div class="approvalLine">
					<div class="title">
						결재선
						<button class="noneBackgroundBtn" id="approvalLineBtn">결재선
							설정</button>
					</div>
					<div class="approvalLine__table">
						<div class="table__row">
							<div class="table__header">처리</div>
							<div class="table__approvalLine"></div>
						</div>
					</div>
				</div>
				<div class="vacationStatus">
					<div class="title">휴가 현황 및 휴가 신청</div>
					<div class="vacationInfo">
						<div class="vacationInfo__main">
							잔여휴가 <span>20일</span>(연차 20일)
						</div>
						<div class="vacationInfo__sub">
							<div>
								아래 캘린더에서 휴가낼 날짜의 일자 또는 휴가선택 영역을 클릭하여 휴가를 신청할 수 있습니다.<br>
								또는, 사용된 휴가 영역을 클릭하여 이미 신청된 휴가를 취소할 수 있습니다.
							</div>
							<div class="sub__label">
								<div class="usedVacation"></div>
								사용한 휴가
								<div class="selectedVacation"></div>
								선택한 날짜
							</div>
						</div>
					</div>
					<div class="vacationScahdule">
						<input type="text" id="date_selector">
						<div id="flatpickr_div"></div>
						<div class="appStatus">
							<div class="appStatus__line"></div>
						</div>
					</div>
				</div>
				<div class="vacationReason">
					<div class="title">휴가 사유</div>
					<textarea name="" rows="5" id="vacationReason"></textarea>
				</div>
			</div>

		</div>
		<!-- 결제선 설정 모달창 -->
		<%@ include file="../humanresources/approvalLineModal.jsp"%>
	</div>
</body>
</html>