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

<!-- read.css -->
<link rel="stylesheet" href="/css/mail/read.css">
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../commons/naviBar.jsp" %>
		
		<div class="container__read">
			<form method="post" enctype="multipart/form-data">
				<div class="read__btns">
					<button type="submit" formaction="">답장</button>
					<button type="submit" formaction="">전체답장</button>
					<button type="submit" formaction="">전달</button>
					<button type="submit" formaction="">삭제</button>
					<button type="submit" formaction="">완전삭제</button>
				</div>
				<input type="hidden" id="id" value="${mail.id }" />
				<div class="read__title">
					${mail.title}
				</div>
				<div class="read__time">
					보낸 시간
					<%-- ${mail.time } --%>
				</div>
				<div class="read__idInfo">
					<p>보낸 사람 : ${mail.send_id }
				</div>
				<div class="read__idInfo">
					<p>받는 사람 : ${mail.receive_id }
				</div>
				<div class="read__idInfo">
					<p>참조: </p>
				</div>
				<hr>
				<div class="read__file"></div>
				<div class="read__content">
					${mail.content }
				</div>
			</form>
			
			<script>
				$(document).ready(function() {
					$.ajax({
						url: "/mail/fileList",
						data: { email_id : $("#id").val() } 
					}).done(function(resp) {						
						if(resp.length > 0) {
							let fileCount = $("<div>");
							fileCount.addClass("file__count");
							fileCount.html("첨부파일 : " + resp.length + "개");
							$(".read__file").append(fileCount);
							
							for(let i = 0; i < resp.length; i++) {
								let fileInfoBox = $("<div>");
								fileInfoBox.addClass("file__name");
								fileInfoBox.html(resp[i].ori_name);	
								
								$(".read__file").append(fileInfoBox);
							}
							
							$(".read__file").append($("<hr>"));
							
						}
					})
				})
			</script>
		</div>
	</div>
</body>
</html>