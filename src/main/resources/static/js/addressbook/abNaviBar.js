$(document).ready(function() {
	$(".personalToggle, .sharedToggle").on("click", function() {
		if ($(this).attr("toggleView") === "true") {
			$(this).attr("toggleView", "false")
			$(this).parent().find(".toggleInner").css("display", "none");
			$(this).children(".naviConp__icon").html(`<i class="fa-solid fa-chevron-down"></i>`);
		} else {
			$(this).attr("toggleView", "true")
			$(this).parent().find(".toggleInner").css("display", "flex");
			$(this).children(".naviConp__icon").html(`<i class="fa-solid fa-chevron-up"></i>`);
		}
	});
	
	/*// 메뉴바 설정
	$(".naviConp__title").each(function(){
		if($("#abCurrentMenu").val()!==""&&$(this).text() == $("#abCurrentMenu").val()){
			$(this).parent().css("background-color", "#DCEDD4");
		}
	})*/
	$("div[data-title='"+$("#abCurrentMenu").val()+"']").css("background-color", "#DCEDD4");
	
	// 주소 추가 모달창 띄우기
	$("#addressAddBtn").on("click",function(){
		modalInit();
		$(".addBookInsertModal").modal({
			showClose: false
		});
	})
});