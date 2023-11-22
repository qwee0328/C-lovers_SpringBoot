$(document).ready(function() {
	let document_id = $("#document_id").text();
	let drafter = $("#drafter").text();
	
	// 휴가 신청서
	if($("#document_type").text() == '휴가 신청서') {
		console.log("휴가 신청서");
		$.ajax({
			url : "/electronicsignature/getVacationInfo",
			data : { 
				document_id : document_id,
				drafter : drafter
				}
		}).done(function(resp){	
			for(let i = 0; i < resp.vacation_info.length; i++) {
				let vacationDateDiv = $("<div>");
				vacationDateDiv.text(resp.vacation_info[i].vacation_date + " / " + resp.vacation_info[i].rest_reason_type);
				$("#vacation_date").append(vacationDateDiv);
			}
			
			$("#vacation_reason").text(resp.vacation_info[0].vacation_reason);
			
			if(resp.isApprovalTurn) {
				$(".approvalBtn").css('display', 'flex');
			}
		})
	// 지출 결의서
	} else if($("#document_type").text() == '지출 결의서') {
		$.ajax({
			url : "/electronicsignature/getExpenseInfo",
			data : { document_id : document_id }
		}).done(function(resp){
			console.log(resp);
			
			$("#division").text(resp.expense_info.expense_category);
			$("#account_base_month").text(resp.expense_info.expense_date);
			$("#spender_id").text(resp.expense_info.spender_name);
			$("#account_info").text(resp.account.bank + " / " + resp.account.id);
			$("#executive_summary").text(resp.expense_info.summary);
			
			if(resp.isApprovalTurn) {
				$(".approvalBtn").css('display', 'flex');
			}
			
			// 파일 출력
			if(resp.fileList.length > 0) {
				for(let i = 0; i < resp.fileList.length; i++) {
					let fileDiv = $("<div>");
					fileDiv.html(resp.fileList.ori_name);
					
					$("#business_file").append(fileDiv);
				}
			}

		})
	// 업무 연락
	} else if($("#document_type").text() == '업무 연락') {
		$.ajax({
			url : "/electronicsignature/getBusinessInfo",
			data : { document_id : document_id }
		}).done(function(resp){
			$("#business_title").text(resp.business_info.title);
			$("#business_content").text(resp.business_info.content);
			
			console.log(resp.isApprovalTurn);
			
			if(resp.isApprovalTurn) {
				$(".approvalBtn").css('display', 'flex');
			}
		})
		
		
	}
	
	// 승인, 반려 버튼 눌렀을 경우
	$(".approvalBtn").on("click", "button", function(){
		let dicision = $(this).text();
		console.log(dicision);
		let result = confirm("결재를 " + dicision + "하시겠습니까?");
		
		if(result) {
			$.ajax({
				url: "/electronicsignature/submitApproval",
				data : { 
					document_id : document_id,
					approval : dicision
					}
			}).done(function(resp){
				alert(dicision + "이 완료되었습니다.");
				location.reload();
			})
		}
	})
})