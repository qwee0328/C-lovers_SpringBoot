$(document).ready(function() {
	// 모든 문서 버튼 클릭했을 때
	$("#anchorApprovalForm").on("click", function(e) {
		e.preventDefault();
		// 드롭 메뉴바 보임
		$(".approvalForm__dropMenu").toggle();
	});
	
	// 전체 리스트 출력
	$.ajax({
		url: "/electronicsignature/temporaryList"
	}).done(function(resp){
		for(let i = 0; i < resp.length; i++) {
			let listDiv = $("<div>");
			listDiv.addClass("document__list");
			
			let titleDiv = $("<div>");
			titleDiv.addClass("documentTable__title");
			titleDiv.html(resp[i].title);
			
			let categoryDiv = $("<div>");
			categoryDiv.addClass("documentTable__documentCategory");
			categoryDiv.html(resp[i].document_type_id);
			
			listDiv.append(titleDiv).append(categoryDiv);
			$(".documentTable__body").append(listDiv);
		}
	})
})