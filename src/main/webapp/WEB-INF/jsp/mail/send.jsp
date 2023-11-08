<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>편지 쓰기</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- summernote cdn -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<!-- send.css -->
<link rel="stylesheet" href="/css/mail/send.css">
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../commons/naviBar.jsp" %>
		
		<div class="container__send">
			<form method="post" enctype="multipart/form-data">
				<div class="send__btns">
					<button type="submit" formaction="/mail/submitSend">보내기</button>
					<button type="submit" formaction="/mail/saveSend">저장하기</button>
					<div class="btns__sendReserve">
						<button type="submit" formaction="/mail/reserveSend">발송 예약</button>
						<i class="fa-solid fa-angle-down sendReserve__icon"></i>
					</div>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">받는 사람</div>
					<input type="text" name="receive_id" class="inputLine__input" placeholder="메일 주소 사이에 ,(콤마) Ehsms ;(세미콜론)으로 구분하여 입력하세요."/>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">참조</div>
					<input type="text" name="reference_id" class="inputLine__input"/>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">제목</div>
					<input type="text" name="title" class="inputLine__input"/>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">파일 첨부</div>
						<input type="file" name="files" multiple/>
					<div class="inputLine__fileList"></div>
				</div>
				<div class="send__contentBox">
					<textarea id="summernote" name="content" class="contentBox__content"></textarea>
				</div>
				<input type="hidden" name="send_id" value=${loginID } />
			</form>
			
			<script>
				$(document).ready(function() {
					$('#summernote').summernote({
						height: 500,                 // set editor height
						  minHeight: null,             // set minimum height of editor
						  maxHeight: null,             // set maximum height of editor
						  focus: true 
					});
				});
			</script>
		</div>
	</div>
</body>
</html>