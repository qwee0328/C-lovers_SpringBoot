$(document).ready(function() {
	$(".naviBtn").on("click", function() {
		location.href = "/electronicsignature/" + $("#location").val();
	})
	
	$(document).on("click", ".naviConp", function() {
		let title = $(this).children(".naviConp__title").html();
		let icon = $(this).children(".naviConp__icon").children("i");
		let location = $(this).children(".naviLocation").val();

		if(title === "진행 중인 문서") {
			$(icon.toggleClass("fa-chevron-up fa-chevron-down"));
			$(".progressDocument").toggle();
		} else if(title === "문서함") {
			$(icon.toggleClass("fa-chevron-up fa-chevron-down"));
			$(".document").toggle();
		} else {
			window.location.href = "/electronicsignature/" + location;
		}
	})

	 $(".naviConp__title").each(function() {
		 // 진행 중인 문서의 전체 네비일 경우
        if($("#currentMenu").val() === "진행전체") {
			$(".progressTotal").css("background-color", "#DCEDD4");
		// 문서함의 전체 네비일 경우
		} else if($("#currentMenu").val() === "문서전체") {
			$(".documentTotal").css("background-color", "#DCEDD4");
		} else if ($(this).text() === $("#currentMenu").val()) {
            $(this).parent().css("background-color", "#DCEDD4");
        }
    });
    
    $("#electronicSignatureWriteBtn").on("click",function(){
		location.href = "/electronicsignature/electronicSignatureWrite";
	})
    
})