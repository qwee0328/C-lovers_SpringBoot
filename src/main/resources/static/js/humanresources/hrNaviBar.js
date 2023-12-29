$(document).ready(function() {
	$.ajax({
		url: "/members/isAdmin"
	}).done(function(resp) {
		if(resp.includes("총괄")||resp.includes("인사")){
			$(".naviBar__managerMenu").css("display", "block");
		}else{
			$(".naviBar__managerMenu").css("display", "none");
		}
	});

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
	$(".naviConp__title").each(function() {
		if ($("#hrCurrentMenu").val() !== "" && $(this).text() == $("#hrCurrentMenu").val()) {
			$(this).parent().css("background-color", "#DCEDD4");
		}
	})

	// 휴가 신청 이동
	$("#vacationAppBtn").on("click", function() {
		location.href = "/humanResources/showVacationApp";
	})

	// 휴가/근무 이동
	$("#vacationWork").on("click", function() {
		location.href = "/humanResources";
	})

	// 프로필 설정 이동
	$("#profileSettings").on("click", function() {
		location.href = "/humanResources/mypage";
	})

	// 근무 현황 이동
	$("#workStatus").on("click", function() {
		location.href = "/humanResources/workStatus";
	})
	// 임직원 관리 이동
	$("#employeeInfo").on("click", function() {
		location.href = "/humanResources/employeeInfo";
	})

	// 조직관리 이동
	$("#orgManagement").on("click", function() {
		location.href = "/admin/office/organization";
	})
	
	// 사용자관리 이동
	$("#userManagement").on("click", function() {
		location.href = "/admin/office/user";
	})
	
	// 직위/직무 설정 이동
	$("#positionManagement").on("click", function() {
		location.href = "/admin/office/positionduty";
	})
	
	// 인사 관리자 설정 이동
	$("#hrManager").on("click", function() {
		location.href = "/admin/office/administrator";
	})
});