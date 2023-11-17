$(document).ready(function() {
	$(".regularToggle, .shiftToggle, .org_ExeToggle, .hrToggle").on("click", function() {
		if ($(this).attr("toggleView") === "true") {
			$(this).attr("toggleView", "false")
			if ($(this).attr("class").includes("regularToggle")) {
				$(".toggleInner").css("display", "none")
			} else if ($(this).attr("class").includes("shiftToggle")) {
				$(".shiftInner").css("display", "none")
			} else if ($(this).attr("class").includes("org_ExeToggle")) {
				$(".org_ExeInner").css("display", "none")
			} else if ($(this).attr("class").includes("hrToggle")) {
				$(".hrInner").css("display", "none")
			}
			$(this).children(".naviConp__icon").html(`<i class="fa-solid fa-chevron-down"></i>`);
		} else {
			$(this).attr("toggleView", "true")
			if ($(this).attr("class").includes("regularToggle")) {
				$(".toggleInner").css("display", "flex")
			} else if ($(this).attr("class").includes("shiftToggle")) {
				$(".shiftInner").css("display", "flex")
			} else if ($(this).attr("class").includes("org_ExeToggle")) {
				$(".org_ExeInner").css("display", "flex")
			} else if ($(this).attr("class").includes("hrToggle")) {
				$(".hrInner").css("display", "flex")
			}
			$(this).children(".naviConp__icon").html(`<i class="fa-solid fa-chevron-up"></i>`);
		}
	})
	
	// 메뉴바 설정
	$(".naviConp__title").each(function(){
		console.log($("#hrCurrentMenu").val())
		if($("#hrCurrentMenu").val()!==""&&$(this).text() == $("#hrCurrentMenu").val()){
			$(this).parent().css("background-color", "#DCEDD4");
		}
	})
	
	// 휴가 신청 이동
	$("#vacationAppBtn").on("click",function(){
		location.href = "/humanResources/showVacationApp";
	})
	
	$("#vacationWork").on("click", function(){
		location.href = "/humanResources";
	})
	$("#profileSettings").on("click",function(){
		location.href = "/humanResources/mypage";
	})
	
	
	 // 임직원 관리 이동
   	$("#employeeInfo").on("click",function(){
   		location.href = "/humanResources/employeeInfo";
   	})
});