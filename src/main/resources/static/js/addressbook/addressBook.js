/*$(document).on("click",".modalBody__plusBtn>i", function() {
	if ($(this).closest(".modalBody__line").find(".modalBody__input").val() == "" || $(this).closest(".modalBody__line").find("select option:selected").val() == "") {
		Swal.fire({
			icon: "error",
			text: "내용을 먼저 채워주세요."
		});
		return false;
	}
});*/

function modalInit() { //insert modal 창 내용 초기화
	$(".modalBody__input").val("");
	$(".modalBody__tag").val("");
	$(".modalBody__birthType").val("양력");
	$(".modalBody__numberType").val("휴대폰");
	$(".modalBody__addressType").val("회사");
	$(".modalBody__memo").html("");
	$(".activeType").removeClass("activeType");
	$(".personal").addClass("activeType");
}

$(document).on("click", ".modalBody__plusBtn>i", function() { // 태그 추가
	$(".addBookTagInsertModal").modal({
		showClose: false,
		closeExisting: false
	});
});

$(document).on("click",".selectedTag__delete",function(){ // 선택한 태그 취소 및 비활성화 취소
	let id = $(this).attr("selectId");
	$(`.modalBody__tag option[value=${id}]`).removeAttr("disabled");
	$(this).closest(".selectedTag").remove();
});


$(document).on("change","select[name='modalBody__tag']",function(){ // 태그 선택	
	if($(".selectedTag").length >=5){ // 태그는 최대 5개까지 선택가능
		Swal.fire({
			icon: "error",
			text: "태그는 최대 5개까지 선택이 가능합니다."
		});
		$(".modalBody__tag").val("");
		return false;
	}
	
	let selectedTag = $("<div>").attr("class","selectedTag d-flex");
	let selectedTag__name = $("<div>").attr("class","selectedTag__name align-center").text($("select[name='modalBody__tag'] option:selected").text());
	let selectedTag__delete = $("<div>").attr("class","selectedTag__delete align-center").attr("selectId",$("select[name='modalBody__tag'] option:selected").val()).html(`<i class="fa-solid fa-xmark align-center"></i>`);
	$(".selectedTags").append(selectedTag.append(selectedTag__name).append(selectedTag__delete));
	$("select[name='modalBody__tag'] option:selected").prop("disabled",true); // 선택한 태그는 다시 선택할 수 없도록 비활성화
	$(".modalBody__tag").val(""); // select 선택 값 초기화
});

$(document).ready(function() {
	$(".modalBody__addBookType>div").on("click", function(){ // 주소록 선택 (개인 주소록 / 공유 주소록)
		$(".activeType").removeClass("activeType");
		$(this).addClass("activeType");
	});


	$(".addBookModal__cancelBtn").on("click", function() { // 모달창 닫기
		$.modal.close();
	})
	
	$("#addressBookInsert").on("click",function(){ // 주소록 저장
		console.log(new Date($(".modalBody__birth").val()));
		$.ajax({
			url:"/addressbook/insert",
			data:{
				name : $(".modalBody__name").val(),
				is_share : $(".activeType").text()=="공유 주소록"?true:false,
				email :$(".modalBody__email").val(),
				numberType: $(".modalBody__numberType option:selected").val(),
				number : $(".modalBody__number").val(),
				company_name : $(".modalBody__company").val(),
				dept_name : $(".modalBody__dept").val(),
				job_name : $(".modalBody__job").val(),
				addressType: $(".modalBody__addressType option:selected").val(),
				address : $(".modalBody__address").val(),
				birthType: $(".modalBody__birthType option:selected").val(),
				birth: $(".modalBody__birth").val(),
				memo: $(".modalBody__memo").text()
			},
			type:"post"
		}).done(function(resp){
			if(resp==1) $.modal.close();
		});
	});
	
	$("#addressBookTagInsert").on("click",function(){ // 태그 저장
		$.ajax({
			url:"/addressbook/tagInsert",
			data: {
				is_share : $(".activeType").text()=="공유 주소록"?true:false,
				name : $(".modalBody__tagName").val()
			},
			type:"post"
		}).done(function(resp){
			if(resp==1) $.modal.close();
		});
	});
});


$(document).on("click", ".addList__addessLine .favorites__icon", function() { // 즐겨찾기
	if ($(this).hasClass("chk")) {
		$(this).removeClass("chk");
	} else {
		$(this).addClass("chk");
	}
});


$(document).on("change", ".addessLine__chkBox", function() { // 주소록 home 선택 toggle
	if ($(this).is(":checked")) {
		$(this).closest(".addList__addessLine").css("backgroundColor", "#DCEDD4");
	} else {
		$(this).closest(".addList__addessLine").css("backgroundColor", "transparent");
	}

	if ($(".addListHeader__chkBox").is(":checked")) $(".addListHeader__chkBox").prop("checked", false);
});

$(document).on("change", ".addListHeader__chkBox", function() { // 주소록 home 전체 선택 toggle
	if ($(this).is(":checked") == true) {
		$(".addList__addessLine").css("backgroundColor", "#DCEDD4");
		$(".addessLine__chkBox").prop("checked", true);
	} else {
		$(".addList__addessLine").css("backgroundColor", "transparent");
		$(".addessLine__chkBox").prop("checked", false);
	}
});


