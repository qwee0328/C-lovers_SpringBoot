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
					<button type="submit" formaction="/mail/tempSend">저장하기</button>
					<div class="btns__sendReserve">
						<button type="submit" formaction="/mail/reserveSend">발송 예약</button>
						<i class="fa-solid fa-angle-down sendReserve__icon"></i>
					</div>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">받는 사람</div> 
					<input type="text" name="receive_id" value="${reply.send_id }" class="inputLine__input" placeholder="메일 주소 사이에 ,(콤마) Ehsms ;(세미콜론)으로 구분하여 입력하세요."/>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">참조</div>
					<input type="text" name="reference_id" value="${reply.reference_id }" class="inputLine__input"/>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">제목</div>
					<c:choose>
					    <c:when test="${not empty reply.title and reply.temporary == false}">
					        <input type="text" name="title" value="[RE:]${reply.title}" class="inputLine__input"/>
					    </c:when>
					    <c:otherwise>
					        <input type="text" name="title" value="${reply.title}" class="inputLine__input"/>
					    </c:otherwise>
					</c:choose>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">파일 첨부</div>
					<div class="inputLine__fileList">
						<input type="file" name="uploadFiles" multiple/>
					</div>
				</div>
				<c:choose>
					<c:when test="${not empty fileList }">
						<div id="fileList" class="send__inputLine">
							<div class="inputLine_title">파일 리스트</div>
							<div class="inputLine__fileList">
								<c:forEach var="i" items="${fileList }">
									<div class="fileList__deleteFile">
										<div>${i.ori_name }</div>
										<button type="button" sysName="${i.sys_name }" class="deleteFile__btn"><i class="fa-solid fa-xmark"></i></button>
									</div>
								</c:forEach>
							</div>
						</div>	
					</c:when>
				</c:choose>
				<input type="hidden" id="deleteFiles" name="sysName">
				
				<div class="send__contentBox">
					<textarea id="summernote" name="content" class="contentBox__content"></textarea>
					<c:choose>
						<c:when test="${empty isReply }">
							<script>
								$("#summernote").summernote({
								    height: 500,
								    disableResizeEditor: true
								});
							
								$("#summernote").summernote("code", `${reply.content}`);
							</script>
						</c:when>
						<c:otherwise>
							<script>
								let oriMailBox = $("<br><br><br><blockquote>");
								
								let lineBox = $("<p>");
								lineBox.html("------------Original Message------------");
								lineBox.css("font-size", "13px");
								
								let subjectBox = $("<p>");
								subjectBox.css("font-size", "13px");
								let subjectText = $("<span>");
								subjectText.html("Subject : ");
								subjectText.css("font-weight", "bold");
								let subjectContent = $("<span>");
								subjectContent.html(`${reply.title}`);
								subjectBox.append(subjectText).append(subjectContent);
								
								let dateBox = $("<p>");
								dateBox.css("font-size", "13px");
								let dateText = $("<span>");
								dateText.html("Date : ");
								dateText.css("font-weight", "bold");
								let dateContent = $("<span>");
								dateContent.html(`${reply.send_date}`);
								dateBox.append(dateText).append(dateContent);
								
								let fromBox = $("<p>");
								fromBox.css("font-size", "13px");
								let fromText = $("<span>");
								fromText.html("From : ");
								fromText.css("font-weight", "bold");
								let fromContent = $("<span>");
								fromContent.html(`${reply.send_id}`);
								fromBox.append(fromText).append(fromContent);
								
								let toBox = $("<p>");
								toBox.css("font-size", "13px");
								let toText = $("<span>");
								toText.html("To : ");
								toText.css("font-weight", "bold");
								let toContent = $("<span>");
								toContent.html(`${reply.receive_id}`);
								toBox.append(toText).append(toContent);
								
								let ccBox = $("<p>");
								ccBox.css("font-size", "13px");
								let ccText = $("<span>");
								ccText.html("Cc : ");
								ccText.css("font-weight", "bold");
								let ccContent = $("<span>");
								ccContent.html(`${reply.reference_id}`);
								ccBox.append(ccText).append(ccContent);
								
								let contentBox = $("<p>");
								contentBox.html(`${reply.content}`);
								
								oriMailBox.append(lineBox).append(subjectBox).append(dateBox).append(fromBox).append(toBox).append(ccBox).append(contentBox);
								$("#summernote").summernote("code", oriMailBox);
							</script>
						</c:otherwise>
					</c:choose>
				</div>
				<input type="hidden" name="send_id" value=${loginID } />
				<c:choose>
					<c:when test="${not empty reply.id }">
						<input type="hidden" name="id" value=${reply.id } />
						<input type="hidden" name="temporary" value=${reply.temporary } />
					</c:when>
				</c:choose>
				
			</form>
			
			<script>
				let deleteFileList = "";
				$(document).on("click", ".deleteFile__btn", function() {
					deleteFileList = deleteFileList.concat($(this).attr("sysName")+":");
					console.log(deleteFileList);
					
					$(this).parent().remove();
					$("#deleteFiles").val(deleteFileList);
				});
			
				$(document).ready(function() {
					$('#summernote').summernote({           // set editor height
						height: 500,  
						disableResizeEditor: true,          // set maximum height of editor
						  focus: true 
					});
				});
			</script>
		</div>
	</div>
</body>
</html>