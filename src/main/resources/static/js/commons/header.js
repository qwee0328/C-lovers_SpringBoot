$(document).ready(function() {
	// 로고 클릭시 홈으로 이동
	$(document).on("click", ".headerLeft__logo", function() {
		location.href = "/";
	})
	
	$(".dropNavi__icon").on("click", function() {
		
	})
	
	// 데이터 부르기
	$(document).ready(function(){
		$.ajax({
			url:"/humanResources/headerProfile"
		}).done(function(resp){
			console.log(resp.profile_img);
			
			
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
	
	
	
	// 민경 추가
	$(document).on("click",".mainHeader_profileBox",function(){
		if($(".header__userinfoBox").hasClass("active")){
			$(".header__userinfoBox").removeClass("active");
			
		}else{
			$(".header__userinfoBox").addClass("active");
		}
	});
	
	// 채팅으로 이동
	$("#goChat").on("click",function(){
		let option ="height=700, width=400";
        let openUrl = "/chat/goMain";
        window.open(openUrl,"chatMain",option);
	})
})