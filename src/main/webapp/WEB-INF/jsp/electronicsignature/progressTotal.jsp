<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>진행 중인 문서 전체</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- css, js -->
<link rel="stylesheet" href="/css/electronicsignature/progressTotal.css">
<script src="/js/electronicsignature/progressTotal.js"></script>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../electronicsignature/electronicsignatureNaviBar.jsp" %>
		
		<div class="container__wait">	
			<div class="wrap__documentTable">
				<div class="documentTable__header">
					<div>문서 번호</div>
					<div>제목</div>
					<div>기안자</div>
					<div>기안일</div>
					<div>구분</div>
					<div>상태</div>
				</div>
				
				<div class="documentTable__body">
					
				</div>
				
				<div class="bottom__pageNavi"></div>
			</div>
		</div>
	</div>
</body>
</html>