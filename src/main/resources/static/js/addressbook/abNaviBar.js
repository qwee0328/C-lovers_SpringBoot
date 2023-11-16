$(document).ready(function() {
	$(".personalToggle, .sharedToggle").on("click", function() {
		if ($(this).attr("toggleView") === "true") {
			$(this).attr("toggleView", "false")
			if ($(this).attr("class").includes("personalToggle")) {
				$(".toggleInner").css("display", "none")
			} else if ($(this).attr("class").includes("sharedToggle")) {
				$(".sharedInner").css("display", "none")
			}
			$(this).children(".naviConp__icon").html(`<i class="fa-solid fa-chevron-down"></i>`);
		} else {
			$(this).attr("toggleView", "true")
			if ($(this).attr("class").includes("regularToggle")) {
				$(".toggleInner").css("display", "flex")
			} else if ($(this).attr("class").includes("sharedToggle")) {
				$(".sharedInner").css("display", "flex")
			}
			$(this).children(".naviConp__icon").html(`<i class="fa-solid fa-chevron-up"></i>`);
		}
	});
	
	// 메뉴바 설정
	$(".naviConp__title").each(function(){
		//console.log($("#abCurrentMenu").val())
		if($("#abCurrentMenu").val()!==""&&$(this).text() == $("#abCurrentMenu").val()){
			$(this).parent().css("background-color", "#DCEDD4");
		}
	})
	
	// 주소 추가 모달창 띄우기
	$("#addressAddBtn").on("click",function(){
		modalInit();
		$(".addBookInsertModal").modal({
			showClose: false
		});
	})
	
	// 태그 불러와서 넣어주기
	$.ajax({
		url:"/addressbook/tagSelect"
	}).done(function(tagList){
        let naviConp__icon = $("<div>").attr("class","naviConp__icon").html(`<i class="fa-solid fa-tag"></i>`);
		for(let i=0; i< tagList.length; i++){
			
			let naviConp__title = $("<div>").attr("class","naviConp__title naviConp__titleMini").text(tagList[i].name);
			if(tagList[i].is_share){ // 공유주소록
				let sharedInner = $("<div>").attr("class","naviConp sharedInner");
				sharedInner.append(naviConp__icon.clone()).append(naviConp__title);
				$(".naviBar__sharedAddress").append(sharedInner);
			}else{ // 개인주소록
				let toggleInner = $("<div>").attr("class","naviConp toggleInner");
				toggleInner.append(naviConp__icon.clone()).append(naviConp__title);
				$(".naviBar__personalAddress").append(toggleInner);
			}
		}
	});
	
});