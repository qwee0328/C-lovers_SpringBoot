<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>임시저장</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- css, js -->
<link rel="stylesheet" href="/css/electronicsignature/temporary.css">
<script src="/js/electronicsignature/temporary.js"></script>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../electronicsignature/electronicsignatureNaviBar.jsp" %>
		
		<div class="container__wait">
			<div class="wrap__documentTable">
				<div class="documentTable__header">
					<div>제목</div>
					<div>문서 종류</div>
				</div>
				
				<div class="documentTable__body">
					
				</div>
			</div>
		</div>
	</div>
</body>
</html>