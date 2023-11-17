<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>편지 읽기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- summernote cdn -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<!-- css, js -->
<link rel="stylesheet" href="/css/mail/read.css">
<script src="/js/mail/read.js"></script>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../commons/mailNaviBar.jsp" %>
		
		<div class="container__read">
			<form method="post" enctype="multipart/form-data">
				<div class="read__btns">
					<c:choose>
						<c:when test="${mail.temporary }">
							<button type="submit" formaction="/mail/send/rewrite">작성하기</button>
						</c:when>
						<c:otherwise>
							<button type="submit" formaction="/mail/send/reply">답장</button>
						</c:otherwise>
					</c:choose>
					<button type="button" id="deleteMail">삭제</button>
					<button type="button" id="perDeleteMail">완전삭제</button>
				</div>
				<input type="hidden" name="id" id="id" value="${mail.id }" />
				<div class="read__title">
					${mail.title}
				</div>
				<c:choose>
					<c:when test="${not empty mail.reservation_date }">
						<div class="read__time">
							예약시간 ${mail.reservation_date }
						</div>
					</c:when>
					<c:otherwise>
						<div class="read__time">
							${mail.send_date }
						</div>
					</c:otherwise>
				</c:choose>
				<div class="read__idInfo">
					<p>보낸 사람 :<p><p class="idInfo__Value">${mail.sender_name }</p><p>[${mail.sender_task_name }]</p>
				</div>
				<div class="read__idInfo">
					<p>받는 사람 :</p><p id="receiver_name" class="idInfo__Value">${mail.receiver_name }</p><p>[${mail.receiver_task_name }]</p>
				</div>
				<c:if test="${mail.reference_id != '' }">
					<div class="read__idInfo">
						<p>참조:</p><p class="idInfo__Value">${mail.reference_id }</p>
					</div>
				</c:if>
				<hr>
				<div class="read__file"></div>
				<div class="read__content">
					${mail.content }
				</div>
			</form>
			<input type="hidden" id="receive_id" value="${mail.receive_id }" />
		</div>
	</div>
</body>
</html>