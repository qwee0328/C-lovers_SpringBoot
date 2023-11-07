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

<link rel="stylesheet" href="/css/mail/inBox.css">
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
		<%@ include file="../commons/naviBar.jsp" %>
		
		<div class="container__inBox">
			<div class="inBox__allCheck">
				<input type="checkbox" class="allCheck__checkbox" />
				<div class="allCheck__checkNum">1</div>
				<button type="submit" id="restoreMail" class="allCheck__buttons">메일함으로 이동</button>
				<button type="submit" class="allCheck__buttons">완전삭제</button>
			</div>
			<div class="inBox__mailListBox"></div>
			<hr>
			<div class="inBox__bottom">
				<div class="bottom__mailNum">편지 수 : 5</div>
				<div class="bottom__pageNavi">
					<div class="pageNavi__circle">1</div>
				</div>
			</div>
		</div>
	</div>
	
	<script>
		window.onload = function() {
			$.ajax({
				url: "/mail/trashList",
				type: 'POST'
			}).done(function(resp){
				console.log(resp);
				for(let i = 0; i < resp.length; i++) {
					let mailListDiv = $("<div>");
					mailListDiv.addClass("inBox__mailList");
					
					let checkboxDiv = $("<input type='checkbox'>");
					checkboxDiv.attr("name", "selectedMails");
					checkboxDiv.attr("value", resp[i].id);
					checkboxDiv.addClass("mailList__checkbox");
					
					let nameDiv = $("<div>");
					nameDiv.addClass("mailList__name");
					nameDiv.html(resp[i].send_id);
					
					
					let titleDiv = $("<div>");
					titleDiv.addClass("mailList__title");
					titleDiv.html(resp[i].title);
					
					let rightDiv = $("<div>");
					rightDiv.addClass("mailList__right");
					
					let fileIconDiv = $("<i>");
					$.ajax({
						url: "/mail/haveFile",
						data: { email_id : resp[i].id } 
					}).done(function(resp2){
						if(resp2 == true) {
							fileIconDiv.addClass("fa-solid");
							fileIconDiv.addClass("fa-paperclip");
							fileIconDiv.addClass("right__file");
						} 
					})
					
/* 					let fileIconDiv = $("<i>");
					fileIconDiv.addClass("fa-solid");
					fileIconDiv.addClass("fa-paperclip");
					fileIconDiv.addClass("right__file"); */
					
					let timeDiv = $("<div>");
					timeDiv.addClass("right__time");
					
					rightDiv.append(fileIconDiv).append(timeDiv);
					mailListDiv.append(checkboxDiv).append(nameDiv).append(titleDiv).append(rightDiv);
					
					$(".inBox__mailListBox").append(mailListDiv);
				}
			})
		}
	
		//	체크박스 전체 선택 클릭 시 
		$(document).on("change", ".allCheck__checkbox", function() {
			let checkAll = $(this).is(":checked");
			
			if(checkAll) {
				$(".mailList__checkbox").prop("checked", true);
				$(".mailList__checkbox").parent().css("background-color", "#DCEDD4");
			} else {
				$(".mailList__checkbox").prop("checked", false);
				$(".mailList__checkbox").parent().css("background-color", "");
			}
		})
		
		// 체크박스 개별 클릭 시
		$(document).on("change", ".mailList__checkbox", function() {
			let check = $(this).is(":checked");
			console.log("체크박스 클릭: " + check);
			if(check) {
				$(this).prop("checked", true);
				$(this).parent().css("background-color", "#DCEDD4");
			} else {
				$(this).prop("checked", false);
				$(this).parent().css("background-color", "");
			}
		})
		
		// 메일함으로 이동 버튼 클릭 시
		$("#restoreMail").on("click", function() {
			let selectedMails = [];
			$(".mailList__checkbox:checked").each(function() {
				selectedMails.push($(this).val());
			});
			
			if(selectedMails.length > 0) {
				$.ajax({
					type: "POST",
					url: "/mail/restoreMail",
					data: { selectedMails : selectedMails }
				}).done(function(){
					location.reload();
				});;
			} else {
				alert("이동할 메일을 선택해주세요.");
			}
		})
		
		// 완전 삭제 버튼 클릭 시
		$("#perDeleteMail").on("click", function() {
			let result = confirm("완전삭제 하시겠습니까? 삭제된 메일은 복구되지 않습니다.");
			if(result) {
				let selectedMails = [];
				$(".mailList__checkbox:checked").each(function() {
					selectedMails.push($(this).val());
				});
				
				if(selectedMails.length > 0) {
					$.ajax({
						type: "POST",
						url: "/mail/perDeleteMail",
						data: { selectedMails : selectedMails }
					}).done(function(){
						location.reload();
					}).done(function(){
						alert("선택한 메일이 완전삭제 되었습니다.");
					});
				} else {
					alert("완전삭제할 메일을 선택해주세요.");
				}
			}
		});
		
		
	</script>
</body>
</html>