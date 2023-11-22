
	
	// 휴가 현황 이동 버튼
	$(".detail__colortitle").on("click",function(){
		location.href="/humanResources/workStatus";
		
		$("ul.tabs li:first-child").removeClass("current");
		$(".tab-content").removeClass("current");
		
		$("ul.tabs li:nth-child(2)").addClass("current");
		$("#vacationDetails").addClass("current");
	})
