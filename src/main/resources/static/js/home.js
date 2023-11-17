$(document).ready(function() {
	$(document).on("click", ".naviItem__itemCurcle", function() {
		if($(this).siblings().html() == "메일") {
			location.href = "/mail";
		}
		if($(this).siblings().html() == "인사") {
			location.href = "/humanResources";
		}
		
		if($(this).siblings().html() == "일정") {
			location.href = "/schedule";
		}
		
		if($(this).siblings().html() == "주소록") {
			location.href = "/addressbook";
		}
		
		if($(this).siblings().html() == "전자결재") {
			location.href = "/electronicsignature";
		}
	})
	
	// 관리자 권한 가져오기
	$(document).ready(function() {
		$.ajax({
			url: "/members/isAdmin"
		}).done(function(resp) {
			for(let i = 0; i < resp.length; i++) {			
				// 회계 권한
				if(resp[i] == "회계" || resp[i] == "총괄") {
					$("#accountingController").css("display", "block");
				}
				
				// 총괄 권한
				if(resp[i] == "총괄") {
					$("#officeController").css("display", "block");
				}
			}
		})
	})
})