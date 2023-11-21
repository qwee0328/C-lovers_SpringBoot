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
			url : "/electronicsignature/getExpenceInfo",
			data : { document_id : document_id }
		}).done(function(resp){
			
		})
	// 업무연락
	} else if($("#document_type").t() == '휴가 신청서') {
		$.ajax({
			url : "/electronicsignature/getVacationInfo",
			data : { document_id : document_id }
		}).done(function(resp){
			
		})
	}
})