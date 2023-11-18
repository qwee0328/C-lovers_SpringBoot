$(document).ready(function() {
	// 로고 클릭시 홈으로 이동
	$(document).on("click", ".headerLeft__logo", function() {
		location.href = "/";
	})
	
	$(".dropNavi__icon").on("click", function() {
		
	})
	
	// 민경 추가
	// 프로필 이미지 누르면 창 뜨게 바깥 누르면 닫힘
	$(document).click(function (e) {
		if (e.target.className == "profileImg") {
			$(".header__userinfoBox").toggle(
				function () {
					$(".header__userinfoBox").addClass("show");
				},
				function () {
					$(".header__userinfoBox").addClass("hide");
				}
			)
		}else if($(".header__userinfoBox").has(e.target).length>0){
			return true;
		}else{
			$(".header__userinfoBox").css("display", "none");
		}
	})
	
	// 채팅으로 이동
	$("#goChat").on("click",function(){
		let option ="height=700, width=400";
        let openUrl = "/chat/goMain";
        window.open(openUrl,"chatMain",option);
	})
})