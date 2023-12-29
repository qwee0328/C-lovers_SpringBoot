<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재선 설정 모달</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- summernote cdn -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>


<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet" href="/css/commons/approvalLineModal.css">
<script type="text/javascript"
	src="/js/commons/approvalLineModal.js"></script>
</head>
<body>
	<input type="hidden" value="${loginID }" id="loginID">
	<div class="approvalLineModal">
		<div class="approvalLineModal__title">결재선 선택</div>
		<div class="search">
			<div class="search__prefix">
				<i class="fa-solid fa-magnifying-glass"></i>
			</div>
			<input id="searchInput" type="search" placeholder="이름 / ID 검색">
		</div>
		<div class="approvalLineModal__body">

			<div class="approvalLineModal__bodyLine">
				<div class="approvalLineModal__groupBox">
					<div class="department">
						<div class="department__title"></div>
						<div class="department__body">
							<div class="company pagePlus">
								<i class="fa-solid fa-minus"></i> <span id="officeName"></span> <span id="officeEmpCount"></span>
							</div>
						</div>
					</div>
					<div class="employee">
						<div class="employee__List">
						</div>
						<div class="employee__bottom">
							<div class="allCheck">전체</div>
							<div class="allNonCheck">선택안함</div>
						</div>
					</div>
				</div>
			</div>
			<div class="approvalLineModal__bodyLine">
				<div class="updateBtns">
					<div class="updateBtns__add">
						<button id="applyBtn" class="disabled">
							<i class="fa-solid fa-angle-right"></i>
						</button>
					</div>
					<div class="updateBtns__cancle">
						<button id="cancleApplyBtn" class="disabled">
							<i class="fa-solid fa-angle-left"></i>
						</button>
					</div>
				</div>
				<div class="updateBtns">
					<div class="updateBtns__add">
						<button id="processBtn" class="disabled">
							<i class="fa-solid fa-angle-right"></i>
						</button>
					</div>
					<div class="updateBtns__cancle">
						<button id="cnacleProcessBtn" class="disabled">
							<i class="fa-solid fa-angle-left"></i>
						</button>
					</div>
				</div>
			</div>
			<div class="approvalLineModal__bodyLine">
				<div class="approvalLine">
					<div class="approvalLine__title application">
						신청<span></span>
					</div>
					<div class="approvalLine__applyEmployee"></div>
				</div>
				<div class="approvalLine">
					<div class="approvalLine__title process">
						처리<span></span>
					</div>
					<div class="approvalLine__processEmployee"></div>
				</div>
			</div>
		</div>
		<div class="approvalLineModal__bottom">
			<button class="cancleBtn" id="approvalLineModal__cancle">취소</button>
			<button class="saveBnt disabled">확인</button>
		</div>
	</div>
</body>
</html>