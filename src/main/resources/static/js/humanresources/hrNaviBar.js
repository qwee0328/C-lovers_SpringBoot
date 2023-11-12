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
});