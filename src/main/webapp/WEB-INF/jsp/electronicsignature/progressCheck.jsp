<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>진행 중인 문서 확인</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- css, js -->
<link rel="stylesheet" href="/css/electronicsignature/progressCheck.css">
<script src="/js/electronicsignature/progressCheck.js"></script>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../electronicsignature/electronicsignatureNaviBar.jsp" %>
		
		<div class="container__wait">
			<div class="wait__checkboxList">
				<label>
					<input type="checkbox" class="checkboxList__checkbox"/>
				</label>
				<span>
					<a href="#" id="anchorApprovalForm">보기: 모든 문서</a>
					<i class="fa-solid fa-angle-down"></i>
				</span>
				<div class="approvalForm__dropMenu">
					<li><a value="0">모든 문서</a></li>
					<li><a value="1">지출 결의서</a></li>
					<li><a value="2">휴가 사유서</a></li>
				</div>
			</div>
			
			<div class="wrap__documentTable">
				<div class="documentTable__header">
					<div id="documentTable__Allcheckbox">
						<label><input type="checkbox"/></label>
					</div>
					<div>문서 번호</div>
					<div>제목</div>
					<div>기안자</div>
					<div>기안일</div>
					<div>구분</div>
					<div>결과</div>
				</div>
				
				<div class="documentTable__body">
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>