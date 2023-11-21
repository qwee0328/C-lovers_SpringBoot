<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>전자문서 작성하기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- summernote cdn -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />

<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet"
	href="/css/electronicsignature/electronicSignatureApplication.css">
<script type="text/javascript"
	src="/js/electronicsignature/electronicSignatureApplication.js"></script>
<script type="text/javascript"
	src="/js/electronicsignature/summernote_editor.js"></script>
<style type="text/css">
.container {
	width: 100%;
	margin: 0;
	padding: 0;
}
</style>

</head>
<body>
	<input type="hidden" value="전자결재" id="modalType">
	<input type="hidden" id="applicationEmployeeList">
	<input type="hidden" id="processEmployeeList">
	
	<%@ include file="../commons/header.jsp"%>
	<div class="container" style="margin: 0; padding: 0; width: 100%;">
		<%@ include
			file="../electronicsignature/electronicsignatureNaviBar.jsp"%>
		<div class="electronicsSignatureApp">
		<form method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
			<input type="hidden" name="applicationEmployeeIDList" id="applicationEmployeeIDList">
			<input type="hidden" name="processEmployeeIDList" id="processEmployeeIDList">
			<input type="hidden" name="esDocumentType" id="esDocumentType">
			<input type="hidden" name="esPreservationPeriod" id="esPreservationPeriod">
			<input type="hidden" name="esSecurityLevel" id="esSecurityLevel">
			<input type="hidden" name="esSpender" id="esSpender">
			<input type="hidden" name="temporary" value="false" id="temporary">
			<input type="hidden" name="expense_category" id="expense_category">
			<input type="hidden" name="expenseYear" id="expenseYear">
			<input type="hidden" name="expenseMonth" id="expenseMonth">
			<div class="draftingBtns">
				<button class="noneBackgroundBtn" type="submit" formaction="/electronicsignature/insertDocument">기안하기</button>
				<button class="noneBackgroundBtn" type="submit" id="temporaryBtn" formaction="/electronicsignature/insertDocument">임시저장</button>
			</div>
			<div class="electronicsSignatureApp__body">
				<div class="basicSetting">
					<div class="title">기본설정</div>
					<div class="basicSetting__table">
						<div class="table__row">
							<div class="table__header">문서 종류</div>
							<div class="table__td">
								<div class="selectorType">
									<div class="typeName" id="documentType">선택</div>
									<div class="selectorArrow">
										<i class="fa-solid fa-chevron-down"></i>
									</div>
								</div>
								<div class="selector__option">
									<div class="option__item">선택</div>
									<div class="option__item">지출 결의서</div>
									<div class="option__item">업무 연락</div>
								</div>
							</div>
							<div class="table__header">작성자</div>
							<div class="table__td">하이웍스 대표이사</div>
						</div>
						<div class="table__row">
							<div class="table__header">보존 연한</div>
							<div class="table__td" id="preservationPeriod">
								<div class="selectorType">
									<div class="typeName">5년</div>
									<div class="selectorArrow">
										<i class="fa-solid fa-chevron-down"></i>
									</div>
								</div>
								<div class="selector__option">
									<div class="option__item">1년</div>
									<div class="option__item">3년</div>
									<div class="option__item">5년</div>
									<div class="option__item">영구보관</div>
								</div>
								<div class="explanation" id="period">
									<i class="fa-solid fa-question"></i>
								</div>
								<div class="tooltip" id="period__tooltip" style="z-index: 1;">
									<p>ㆍ1년: 경미한 연결 문서 및 일시적인 사용 또는 처리에 그치는 문서</p>
									<p>ㆍ3년: 사무의 수행상 1년 이상에 걸쳐 참고 또는 이용해야 할 문서 및 법률상 3년간 보존을 요하는
										문서</p>
									<p>ㆍ5년: 사무의 수행상 3년 이상에 걸쳐 참고 또는 이용해야 할 문서 및 법률상 5년간 보존을 요하는
										문서</p>
									<p>ㆍ10년: 사무의 수행상 장기간 참고 또는 이용해야 할 문서 및 법률상 10년간 보존을 요하는 문서</p>
									<p>ㆍ영구: 회사의 존속에 필요 불가결한 문서 및 역사적 또는 기타 사유로 중요한 문서</p>
								</div>
							</div>
							<div class="table__header">보안등급</div>
							<div class="table__td" id="securityLevel">
								<div class="selectorType">
									<div class="typeName">A등급</div>
									<div class="selectorArrow">
										<i class="fa-solid fa-chevron-down"></i>
									</div>
								</div>
								<div class="selector__option">
									<div class="option__item">S등급</div>
									<div class="option__item">A등급</div>
									<div class="option__item">B등급</div>
									<div class="option__item">C등급</div>
								</div>
								<div class="explanation" id="level">
									<i class="fa-solid fa-question"></i>
								</div>
								<div class="tooltip" id="level__tooltip" style="z-index: 1;">
									<p>ㆍS등급: 기안 상에 설정된 관련자들만 문서를 볼 수 있음</p>
									<p>ㆍA등급: 기안 상에 설정된 관련자들과 관리자가 설정한 5등급(부장)등급 이상인 사람이 문서를 볼 수
										있음.</p>
									<p>ㆍB등급: 기안 상에 설정된 관련자들과 관리자가 설정한 7등급(과장)등급 이상인 사람이 문서를 볼 수
										있음.</p>
									<p>ㆍC등급: 모든 임직원이 문서를 열람할 수 있음</p>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="approvalLine">
					<div class="title">
						결재선
						<input type="button" class="noneBackgroundBtn" id="approvalLineBtn" value="결재선 설정">
					</div>
					<div class="approvalLine__table">
						<div class="table__row">
							<div class="table__header">신청</div>
							<div class="table__applyLine"></div>
						</div>
						<div class="table__row">
							<div class="table__header">처리</div>
							<div class="table__approvalLine"></div>
						</div>
					</div>
					<div class="infoDiv">문서 종류 선택 시 결재선이 노출됩니다.</div>
				</div>
				<div class="detailDiv">
					<div class="title">상세입력</div>
					<div class="infoDiv">문서 종류 선택 시 상세 입력이 노출됩니다.</div>
				</div>
				<div class="esTitle">
					<div class="title">제목</div>
					<div class="titleInputBox">
						<input type="text" name="documentTitle" class="titleInput">
					</div>
				</div>
				<div class="mainBox">
					<div class="title">본문</div>
					<div class="spendingResolution__table">
						<div class="table__row">
							<div class="table__header">구분</div>
							<div class="table__srLine">
								<input type="radio" id="individual" name="type" value="개인"
									checked style="margin: 0 5px 0 0;" /> <label for="individual">개인</label>
								<input type="radio" id="corporation" name="type" value="법인" />
								<label for="corporation">법인</label>
							</div>
						</div>
						<div class="table__row">
							<div class="table__header">회계 기준월</div>
							<div class="table__srLine">
								<div class="dateSelectBox" id="year">
									<div class="selectorType">
										<div class="typeName"></div>
										<div class="selectorArrow">
											<i class="fa-solid fa-chevron-down"></i>
										</div>
									</div>
									<div class="selector__option"></div>
								</div>
								<div>&nbsp;&nbsp;&nbsp;년&nbsp;&nbsp;&nbsp;</div>
								<div class="dateSelectBox" id="month">
									<div class="selectorType">
										<div class="typeName"></div>
										<div class="selectorArrow">
											<i class="fa-solid fa-chevron-down"></i>
										</div>
									</div>
									<div class="selector__option"></div>
								</div>
								<div>&nbsp;&nbsp;&nbsp;월</div>
							</div>
						</div>
						<div class="table__row" id="spender">
							<div class="table__header">지출자</div>
							<div class="table__srLine">
								<input type="text" id="searchUser">
								<div id="autoComplete"></div>
							</div>
						</div>
						<div class="table__row" id="accountInfo">
							<div class="table__header">계좌 정보</div>
							<div class="table__srLine"></div>
						</div>
						<div class="table__row" id="corporationCard">
							<div class="table__header">법인 카드</div>
							<div class="table__srLine"></div>
						</div>
						<div class="table__row">
							<div class="table__header">총괄적요</div>
							<div class="table__srLine">
								<textarea name="summary" id="summary" rows="5"></textarea>
							</div>
						</div>
					</div>
					<div class="businessContact__table">
						<textarea id="summernote" name="content"
							class="contentBox__content"></textarea>
					</div>
				</div>
				<div class="fileListBox">
					<div class="title">파일첨부</div>
					<div class="inputLine__fileList">
						<input type="file" name="uploadFiles" id="uploadFiles" multiple />
					</div>
				</div>
			</div>
			</form>
		</div>
		<!-- 결제선 설정 모달창 -->
		<%@ include file="../commons/approvalLineModal.jsp"%>
	</div>
</body>
</html>