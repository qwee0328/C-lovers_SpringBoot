$(document).ready(function() {
	// 휴가 신청서
	if($("#document_type").val() == '휴가 신청서') {
		$.ajax({
			url : "/electronicsignature/getVacationInfo",
			data : { document_id : $("#document_id").val() }
		}).done(function(resp){
			
		})
	// 지출 결의서
	} else if($("#document_type").val() == '지출 결의서') {
		$.ajax({
			url : "/electronicsignature/getExpenceInfo",
			data : { document_id : $("#document_id").val() }
		}).done(function(resp){
			
		})
	// 업무연락
	} else if($("#document_type").val() == '휴가 신청서') {
		$.ajax({
			url : "/electronicsignature/getVacationInfo",
			data : { document_id : $("#document_id").val() }
		}).done(function(resp){
			
		})
	}
})