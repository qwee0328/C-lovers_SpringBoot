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
	
	// 읽음 여부 확인
	$.ajax({
		url: "/mail/confirmation",
		data:{
			id : $("#id").val(),
			receive_id : $("#receive_id").val()
			}
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
})
