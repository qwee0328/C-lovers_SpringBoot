$(document).ready(function() {
	$(document).on("click",".naviConp__addTag",function(e){ // 태그 추가 모달 (주소록 오른쪽 hover 버튼	
		e.stopPropagation(); // 삭제 버튼 누르면 페이지 이동하지 않음. (이벤트 중단)
		$(".modalBody__tagName").val("");
		$(".activeType").removeClass("activeType");
		if($(this).attr("data-isShare") == 0){
			$("#personal").addClass("activeType");
		}else{
			$("#shared").addClass("activeType");
		}
		
		$(".addBookTagInsertModal").modal({
			showClose: false,
			closeExisting: false
		});
	});
	
	$(document).on("click",".toggleMenu",function(){
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
	
	$("div[data-title='"+$("#abCurrentMenu").val()+"']").addClass("activeMenu")
	
	// 주소 추가 모달창 띄우기
	$("#addressAddBtn").on("click",function(){
		modalInit();
		$(".addBookInsertModal").modal({
			showClose: false
		});
	})
});