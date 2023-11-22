$(document).ready(function() {
	// 로고 클릭시 홈으로 이동
	$(document).on("click", ".headerLeft__logo", function() {
		location.href = "/";
	})
	
	$(".dropNavi__icon").on("click", function() {
		
	})
	
	// 프로필카드 데이터 부르기
	$(document).ready(function(){
		$.ajax({
			url:"/humanResources/headerProfile"
		}).done(function(resp){

			let img;
			
			let main_img = $("<img class='profileImg'/>");
				
			if(resp.profile_img == "" || resp.profile_img == null){
				img = "/assets/profile.png";
			}else{
				img = "/uploads/"+resp.profile_img;
			}
			
				main_img.attr("src",img);
			$(".mainHeader_profileBox").append(main_img);
			
			
			
			let profileImg = $("<img class='header__profileImage'>");
				profileImg.attr("src",img);
				$(".header__profileImageBox").append(profileImg);
				
			$(".profileName").html(resp.name);
			$(".profileEmail").html(resp.email);
					
		});
	})
	
	// 프로필 카드 바깥영역 클릭시
	$(document).on("click", function(e) {

		if($(e.target).parents().is(".mainHeader_profileBox") || $(e.target).hasClass("mainHeader_profileBox") ){
			if($(".header__userinfoBox").hasClass("active")){
				$(".header__userinfoBox").removeClass("active");
				
			}else{
				$(".header__userinfoBox").addClass("active");
			}
		}else if(!$(e.target).parents().is(".header__userinfoBox") && !$(e.target).hasClass("header__userinfoBox")){
			$(".header__userinfoBox").removeClass("active");
		}
	})
	
	
	// 채팅으로 이동
	$("#goChat").on("click",function(){
		let option ="height=700, width=400";
        let openUrl = "/chat/goMain";
        window.open(openUrl,"chatMain",option);
	})
	
	
	// 로고 옆에 클릭시 
	$(document).on("click",function(e){
		
		if($(e.target).parents().is(".dropNav_hover") || $(e.target).hasClass("dropNav_hover")){
			if($(".headerLeft__dropNavBox").hasClass("active")){
				$(".headerLeft__dropNavBox").removeClass("active");
			}else{
				$(".headerLeft__dropNavBox").addClass("active");
			}
		}else if(!$(e.target).parents().is(".headerLeft__dropNavBox") && !$(e.target).hasClass("headerLeft__dropNavBox")){
			$(".headerLeft__dropNavBox").removeClass("active");
		}
	})
	
	$(document).on("click",".dropNavBox__inner",function(e){
		console.log($(this));
		
		if($(this).find(".inner__title").html() == "메일"){
			location.href="/mail";
		}
		
		if($(this).find(".inner__title").html() == "일정"){
			location.href = "/schedule";
		}
		
		if($(this).find(".inner__title").html() == "전자결재"){
			location.href = "/electronicsignature";
		}
		
		if($(this).find(".inner__title").html() == "회계지원"){
			location.href = "/admin/accounting";
		}
		
		if($(this).find(".inner__title").html() == "주소록"){
			location.href = "/addressbook";
		}
		
		if($(this).find(".inner__title").html() == "인사"){
			location.href = "/humanResources";
		}
		
		if($(this).find(".inner__title").html() == "오피스 관리"){
			location.href = "/admin/office";
		}
		
		
		
	})
	
})


	// 관리자 권한 가져오기
	$(document).ready(function() {
		$.ajax({
			url: "/members/isAdmin"
		}).done(function(resp) {
			for (let i = 0; i < resp.length; i++) {
				console.log(resp[i]);
			// 회계 권한
			if (resp[i] == "회계" || resp[i] == "총괄") {
				$("#accountingController__small").css("display", "flex");
			}

			// 총괄 권한
			if (resp[i] == "총괄") {
				$("#officeController__small").css("display", "flex");
			}
		}
		})
	})