<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>받은 메일함</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- css, js -->
<link rel="stylesheet" href="/css/mail/inBox.css">
<script src="/js/mail/trash.js"></script>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../commons/mailNaviBar.jsp" %>
		
		<div class="container__inBox">
			<div class="inBox__allCheck">
				<input type="checkbox" class="allCheck__checkbox" />
				<button type="submit" id="restoreMail" class="allCheck__buttons">메일함으로 이동</button>
				<button type="submit" id="perDeleteMail" class="allCheck__buttons">완전삭제</button>
			</div>
			<div class="inBox__mailListBox"></div>
			<hr>
			<div class="inBox__bottom">
				<div class="bottom__mailNum">편지 수 : </div>
				<div class="bottom__pageNavi"></div>
			</div>
		</div>
	</div>
</body>
</html>