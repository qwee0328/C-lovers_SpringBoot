$(document).ready(function() {
	$(".naviBtn").on("click", function() {
		location.href = "/mail/" + $("#location").val();
	})
	
	$(document).on("click", ".naviConp", function() {
		let location = $(this).children(".naviLocation").val();
		window.location.href = "/mail/" + location;
	})

	 $(".naviConp__title").each(function() {
        if ($(this).text() === $("#currentMenu").val()) {
            $(this).parent().css("background-color", "#DCEDD4");
        }
    });
})