<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet" href="/css/humanresources/approvalLineModal.css">
<script type="text/javascript"
	src="/js/humanresources/approvalLineModal.js"></script>
</head>
<body>
	<input type="hidden" value="${loginID }" id="loginID">
	<div class="approvalLineModal">
		<div class="approvalLineModal__title">결재선 선택</div>
		<div class="search">
			<div class="search__prefix">
				<i class="fa-solid fa-magnifying-glass"></i>
			</div>
			<input id="searchInput" type="search" placeholder="이름 / 조직 검색">
		</div>
		<div class="approvalLineModal__body">

			<div class="approvalLineModal__bodyLine">
				<div class="approvalLineModal__groupBox">
					<div class="department">
						<div class="department__title">클로버산업</div>
						<div class="department__body">
							<div class="company pagePlus">
								<i class="fa-solid fa-minus"></i> <span>클로버 산업</span> <span id="officeEmpCount"></span>
							</div>
							<!-- 
							<div class="deptBox">
								<div class="dept pagePlus selected">
									<i class="fa-solid fa-minus"></i> <span>관리부</span> <span>(4)</span>
								</div>
								<div class="dept_task">
									<span>인사팀</span> <span>(1)</span>
								</div>
								<div class="dept_task">
									<span>구매총무팀</span> <span>(1)</span>
								</div>
								<div class="dept_task">
									<span>재무회계팀</span> <span>(1)</span>
								</div>
							</div>
							<div class="deptBox">
								<div class="dept pagePlus">
									<i class="fa-solid fa-minus"></i> <span>관리부</span> <span>(4)</span>
								</div>
								<div class="dept_task">
									<span>인사팀</span> <span>(1)</span>
								</div>
								<div class="dept_task">
									<span>구매총무팀</span> <span>(1)</span>
								</div>
								<div class="dept_task">
									<span>재무회계팀</span> <span>(1)</span>
								</div>
							</div>
							<div class="deptBox">
								<div class="dept">
									<i class="fa-solid fa-plus"></i> <span>관리부</span> <span>(4)</span>
								</div>
							</div>
							 -->
						</div>
					</div>
					<div class="employee">
						<div class="employee__List">
							<!-- 
							<div class="employee__check">
								<input type="checkbox" class="empChk" id="emp_chk"> 
								<label for="emp_chk"> 
									<span>최사장</span> <span>(인사팀)</span>
								</label>
							</div>
							<div class="employee__check">
								<input type="checkbox" class="empChk" id="emp_chk2"> 
								<label for="emp_chk2"> 
									<span>최사장</span> <span>(인사팀)</span>
								</label>
							</div>
							<div class="employee__check">
								<input type="checkbox" class="empChk" id="emp_chk3"> 
								<label for="emp_chk3"> 
									<span>최사장</span> <span>(인사팀)</span>
								</label>
							</div>
							 -->
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
						<button id="applyBtn">
							<i class="fa-solid fa-angle-right"></i>
						</button>
					</div>
					<div class="updateBtns__cancle">
						<button id="cancleApplyBtn">
							<i class="fa-solid fa-angle-left"></i>
						</button>
					</div>
				</div>
				<div class="updateBtns">
					<div class="updateBtns__add">
						<button id="processBtn">
							<i class="fa-solid fa-angle-right"></i>
						</button>
					</div>
					<div class="updateBtns__cancle">
						<button id="cnacleProcessBtn">
							<i class="fa-solid fa-angle-left"></i>
						</button>
					</div>
				</div>
			</div>
			<div class="approvalLineModal__bodyLine">
				<div class="approvalLine">
					<div class="approvalLine__title">
						신청<span>1</span>
					</div>
					<div class="approvalLine__applyEmployee"></div>
				</div>
				<div class="approvalLine">
					<div class="approvalLine__title process">
						처리<span>1</span>
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