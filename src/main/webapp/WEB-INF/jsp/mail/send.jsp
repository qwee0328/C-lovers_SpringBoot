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
			<form method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
				<div class="send__btns">
					<div class="submit__sendBox">
						<button type="submit" formaction="/mail/submitSend">보내기</button>
						<div id="reserve__dateBox">
							<span>( 예약시간 </span>
							<span id="reserve__date"></span>
							<input type="hidden" id="reserve__hidden__date" name="reserve_date" />
							<span> )</span>
						</div>	
					</div>			
					<button type="submit" formaction="/mail/tempSend">저장하기</button>
					<div class="btns__sendReserve">
						<button disabled>발송 예약</button>
						<i class="fa-solid fa-angle-down sendReserve__icon"></i>
						<div class="sendReserve__dropDown hide">
							<div class="dropDown__box">
								<div class="dropbox__title">발송 예약</div>
								<div class="dropbox__reserve">
									<input type="date" id="send_date">
									<select id="send_hour">
										<option value="00">0 시</option>
										<option value="01">1 시</option>
										<option value="02">2 시</option>
										<option value="03">3 시</option>
										<option value="04">4 시</option>
										<option value="05">5 시</option>
										<option value="06">6 시</option>
										<option value="07">7 시</option>
										<option value="08">8 시</option>
										<option value="09">9 시</option>
										<option value="10">10 시</option>
										<option value="11">11 시</option>
										<option value="12">12 시</option>
										<option value="13">13 시</option>
										<option value="14">14 시</option>
										<option value="15">15 시</option>
										<option value="16">16 시</option>
										<option value="17">17 시</option>
										<option value="18">18 시</option>
										<option value="19">19 시</option>
										<option value="20">20 시</option>
										<option value="21">21 시</option>
										<option value="22">22 시</option>
										<option value="23">23 시</option>
									</select>
									<select id="send_minute">
										<option value="00">0 분</option>
										<option value="05">5 분</option>
										<option value="10">10 분</option>
										<option value="15">15 분</option>
										<option value="20">20 분</option>
										<option value="25">25 분</option>
										<option value="30">30 분</option>
										<option value="35">35 분</option>
										<option value="40">40 분</option>
										<option value="45">45 분</option>
										<option value="50">50 분</option>
										<option value="55">55 분</option>
									</select>
								</div>
								<a href="#" class="reserve__btn">확인</a>
							</div>
						</div>
					</div>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">받는 사람</div> 
					<input type="text" id="receive_id" name="receive_id" value="${reply.send_id }" class="inputLine__input" placeholder="메일 주소 사이에 ,(콤마) Ehsms ;(세미콜론)으로 구분하여 입력하세요."/>
					<div id="autoComplete"></div>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">참조</div>
					<input type="text" name="reference_id" value="${reply.reference_id }" class="inputLine__input"/>
				</div>
				<div class="send__inputLine">
					<div class="inputLine_title">제목</div>
					<c:choose>
					    <c:when test="${not empty reply.title and reply.temporary == false}">
					        <input type="text" id="title" name="title" value="[RE:]${reply.title}" class="inputLine__input"/>
					    </c:when>
					    <c:otherwise>
					        <input type="text" id="title" name="title" value="${reply.title}" class="inputLine__input"/>
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
				// 예약 발송 드롭다운 버튼 눌렀을 때
				$(document).ready(function () {
			        $(".sendReserve__icon").on("click", function () {
			            // sendReserve__dropDown 클래스에 hide가 있으면 show로, show가 있으면 hide로 토글
			            $(".sendReserve__dropDown").toggleClass("hide show");
			
			            // sendReserve__dropDown의 하위 요소인 dropDown__box의 디스플레이 속성을 토글
			            $(".sendReserve__dropDown .dropDown__box").toggle();
			        });
			    });
				
				// input type=date에서 현재 날짜 이전은 선택 불가능
				 $(document).ready(function() {
		            // 현재 날짜 가져오기
		            var currentDate = new Date().toISOString().split("T")[0];
		            // input 요소의 min 속성 설정
		            $("#send_date").attr("min", currentDate);
		        });
				
				// 선택한 날짜가 현재 날짜보다 이후인지
				function isDateAfterNow(dateString) {
					let now = new Date();
					let inputDate = new Date(dateString);
					return inputDate > now;
				}
				
				// 예약 드롭다운 확인 버튼 눌렀을 때
				$(".reserve__btn").on("click", function(e) {
					e.preventDefault(); // a 태그 페이지 이동하지 않도록
					
					let sendDate = $("#send_date").val();
					let sendHour = $("#send_hour").val();
					let sendMinute = $("#send_minute").val();
					
					let reserveDate = sendDate + " " + sendHour + ":" + sendMinute + ":00";
					
					let result = isDateAfterNow(reserveDate);
					if(result) {			
						$("#reserve__dateBox").css("display", "block");
						$("#reserve__date").html(reserveDate);
						$("#reserve__hidden__date").val(reserveDate);
						
						$(".sendReserve__dropDown").toggleClass("hide show");
						$(".sendReserve__dropDown .dropDown__box").toggle();
					} else {
						alert("현재보다 이전의 시간은 선택할 수 없습니다.");
					}
					
					
				})
				
				// 받는 사람 입력할 때 자동완성
				/* $("#receive_id").on("keyup", function() {
					let inputId = $(this).val();
					
					$.ajax({
						url: "/mail/AutoComplete",
						data: { receive_id : inputId }
					}).done(function(resp) {
						$("#autoComplete").empty();
						if(resp.length > 0) {
							for(let i = 0; i < resp.length; i++) {
								item = $("<div>");
								item.append(`resp[i].name resp[i].email `);
							}
						}
					})
				}) */
			
				// 파일 리스트 삭제 버튼 눌렀을 때
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
				
				// 필수 입력값들이 존재하는지
				function validateForm() {
					// 받는 사람을 입력하지 않았을 경우
					if($("#receive_id").val() == "") {
						alert("받는 사람은 필수 입력 항목입니다.");
						return false;
					}
					
					// 제목을 입력하지 않았을 경우
					if($("#title").val() == "") {
						alert("제목은 필수 입력 항목입니다.");
						return false;
					}
				}
			</script>
		</div>
	</div>
</body>
</html>