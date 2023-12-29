$(document).ready(function() {
	let document_id = $("#document_id").text();
	let drafter = $("#drafter").text();
	
	// 휴가 신청서
	if($("#document_type").text() == '휴가 신청서') {
		$.ajax({
			url : "/electronicsignature/getVacationInfo",
			data : { 
				document_id : document_id,
				drafter : drafter
				}
		}).done(function(resp){	
			for(let i = 0; i < resp.length; i++) {
				let vacationDateDiv = $("<div>");
				vacationDateDiv.text(resp[i].vacation_date + " / " + resp[i].rest_reason_type);
				$("#vacation_date").append(vacationDateDiv);
			}
			
			$("#vacation_reason").text(resp[0].vacation_reason);
		})
	// 지출 결의서
	} else if($("#document_type").text() == '지출 결의서') {
		$.ajax({
			url : "/electronicsignature/getExpenseInfo",
			data : { document_id : document_id }
		}).done(function(resp){
			
			$("#division").text(resp.expense_category);
			$("#account_base_month").text(resp.expense_date);
			$("#spender_id").text(resp.spender_name);
			$("#account_info").text(resp.account.bank + " / " + resp.account.id);
			$("#executive_summary").text(resp.summary);
	
			$.ajax({
				url:"/electronicsignature/fileList",
				data:{ document_id : document_id }
			}).done(function(resp){
				if(resp.length!==0){
					let tr = $("<tr>");
					let th =$("<th>").html("첨부파일");
					let td=$("<td>").attr("id","file_info");
					
					for(let i=0;i<resp.length;i++){
						let fileList=$("<div>")
						let fileHref = $("<a>").attr("href","/electronicsignature/downloadFile?sysname="+resp[i].sys_name+"&oriname="+resp[i].ori_name).html(resp[i].ori_name);
						fileList.append(fileHref);
						td.append(fileList);
					}
					tr.append(th).append(td);
					$(".expense__table>tbody").append(tr);
				}
			})
		})
	// 업무 연락
	} else if($("#document_type").text() == '업무 연락') {
		$.ajax({
			url : "/electronicsignature/getBusinessInfo",
			data : { document_id : document_id }
		}).done(function(resp){
			$("#business_title").text(resp.title);
			$("#business_content").text(resp.content);
			
			$.ajax({
				url:"/electronicsignature/fileList",
				data:{ document_id : document_id }
			}).done(function(resp){
				if(resp.length!==0){
					let tr = $("<tr>");
					let th =$("<th>").html("첨부파일");
					let td=$("<td>").attr("id","file_info");
					
					for(let i=0;i<resp.length;i++){
						let fileList=$("<div>")
						let fileHref = $("<a>").attr("href","/electronicsignature/downloadFile?sysname="+resp[i].sys_name+"&oriname="+resp[i].ori_name).html(resp[i].ori_name);
						fileList.append(fileHref);
						td.append(fileList);
					}
					tr.append(th).append(td);
					$(".business__table>tbody").append(tr);
				}
			})
		})
	}
	
	// 승인, 반려 버튼 눌렀을 경우
	$(".approvalBtn").on("click", "button", function(){
		let dicision = $(this).text();
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
	
	// 삭제 버튼 눌렀을 경우
	$("#delete").on("click", function(){
		let result = confirm("결재를 삭제하시겠습니까?");
		
		if(result) {
			$.ajax({
				url: "/electronicsignature/deleteApproval",
				data : { 
					document_id : document_id
					}
			}).done(function(resp){
				alert("삭제가 완료되었습니다.");
				location.href = "/electronicsignature";
			})
		}
	})
})