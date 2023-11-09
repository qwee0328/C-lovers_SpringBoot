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
				<div class="read__time">
					${mail.send_date }
				</div>
				<div class="read__idInfo">
					<p>보낸 사람 :<p><p class="idInfo__Value">${mail.send_id }</p>
				</div>
				<div class="read__idInfo">
					<p>받는 사람 :</p><p class="idInfo__Value">${mail.receive_id }</p>
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
				
				// 삭제 버튼 클릭 시
				$("#deleteMail").on("click", function() {
					let result = confirm("메일을 삭제하시겠습니까? 삭제한 메일은 휴지통으로 이동합니다.");
					if(result) {
						window.location.href = "/mail/read/delete?id=${mail.id }";
					}
				})
				
				// 완전삭제 버튼 클릭 시
				$("#perDeleteMail").on("click", function() {
					let result = confirm("메일을 완전삭제하시겠습니까? 삭제된 메일은 복구되지 않습니다.");
					if(result) {
						window.location.href = "/mail/read/perDelete?id=${mail.id }";
					}
				})
				
				
			</script>
		</div>
	</div>
</body>
</html>