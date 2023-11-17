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
        if ($(this).text() === $("#currentMenu").val()) {
            $(this).parent().css("background-color", "#DCEDD4");
        }
    });
    
})