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
	changeTab($(".activeType").attr("id"));
	$(".modalBody__input").val("");
	$(".modalBody__birthType").val("양력");
	$(".modalBody__numberType").val("휴대폰");
	$(".modalBody__addressType").val("회사");
	$(".modalBody__memo").html("");
	$(".activeType").removeClass("activeType");
	$("#personal").addClass("activeType");
}

function changeTab(tapName) { // 주소록 변경 시, 태그 새로 불러오기
	let is_share = 1;
	if (tapName == "personal") is_share = 0;

	$.ajax({
		url: "/addressbook/tagSelectByIsShare",
		data: { is_share: is_share },
		type: "post"
	}).done(function(resp) {
		$(".selectedTags>div").remove();
		$(".modalBody__tag option").remove();
		$(".modalBody__tag").append($("<option>").val("").text("선택").prop("selected", true).prop("disabled", true));

		for (let i = 0; i < resp.length; i++) {
			$(".modalBody__tag").append($("<option>").val(resp[i].id).text(resp[i].name));
		}
	});
}


$(document).on("click", ".modalBody__plusBtn>i", function() { // 태그 추가 모달
	$(".modalBody__tagName").val("");
	$(".addBookTagInsertModal").modal({
		showClose: false,
		closeExisting: false
	});
});

$(document).on("click", ".selectedTag__delete", function() { // 선택한 태그 취소 및 비활성화 취소
	let id = $(this).attr("selectId");
	$(`.modalBody__tag option[value=${id}]`).removeAttr("disabled");
	$(this).closest(".selectedTag").remove();
});


$(document).on("change", "select[name='modalBody__tag']", function() { // 태그 선택	
	if ($(".selectedTag").length >= 5) { // 태그는 최대 5개까지 선택가능
		Swal.fire({
			icon: "error",
			text: "태그는 최대 5개까지 선택이 가능합니다."
		});
		$(".modalBody__tag").val("");
		return false;
	}

	let selectedTag = $("<div>").attr("class", "selectedTag d-flex");
	let selectedTag__name = $("<div>").attr("class", "selectedTag__name align-center").text($("select[name='modalBody__tag'] option:selected").text());
	let selectedTag__delete = $("<div>").attr("class", "selectedTag__delete align-center").attr("selectId", $("select[name='modalBody__tag'] option:selected").val()).html(`<i class="fa-solid fa-xmark align-center"></i>`);
	$(".selectedTags").append(selectedTag.append(selectedTag__name).append(selectedTag__delete));
	$("select[name='modalBody__tag'] option:selected").prop("disabled", true); // 선택한 태그는 다시 선택할 수 없도록 비활성화
	$(".modalBody__tag").val(""); // select 선택 값 초기화
});

function reloadTags(callback) { // 태그 목록 불러와서 인덱스에 넣어주기
	$.ajax({
		url: "/addressbook/tagSelect",
		async:"false"
	}).done(function(tagList) {
		let naviConp__icon = $("<div>").attr("class", "naviConp__icon").html(`<i class="fa-solid fa-tag"></i>`);
		for (let i = 0; i < tagList.length; i++) {
			let naviConp__title = $("<div>").attr("class", "naviConp__title naviConp__titleMini").text(tagList[i].name)
			let toggleInner = $("<div>").attr("class", "naviConp toggleInner customMenu").val(tagList[i].id);
			toggleInner.append(naviConp__icon.clone()).append(naviConp__title).attr("data-title", tagList[i].name);
			if (tagList[i].is_share) { // 공유주소록
				$(".naviBar__sharedAddress").append(toggleInner);
			} else { // 개인주소록
				$(".naviBar__personalAddress").append(toggleInner);
			}
		}
		
		if (typeof callback === 'function') {
	        callback();
	    }
	});
}

function reloadAddressBook(authorityOrTagId, tagName) {
	console.log(`authOrId: ${authorityOrTagId}, name: ${tagName}`);

	let key, value;

	if (typeof authorityOrTagId == "string") {
		key = "is_share";
		if (authorityOrTagId == "personal") value = 0;
		else value = 1;
	} else {
		key = "id";
		value = authorityOrTagId;
	}


	$.ajax({
		url: "/addressbook/select",
		data: {
			key: key,
			value: value,
			currentMenu: tagName
		},
		type: "post"
	}).done(function(resp) {
		$(".body__addList>*").remove();
		for (let i = 0; i < resp.length; i++) {
			let addList__addessLine = $("<div>").attr("class", "addList__addessLine d-flex");
			let addessLine__chkBoxCover = $("<div>").attr("class", "addessLine__chkBoxCover align-center");
			let addessLine__chkBox = $("<input type='checkbox'>").attr("class", "addessLine__chkBox")
			addessLine__chkBoxCover.append(addessLine__chkBox);

			let addessLint__favorites = $("<div>").attr("class", "addessLint__favorites align-center").html(`<i class="fa-regular fa-star align-center favorites__icon"></i>`);
			let addessLine__name = $("<div>").attr("class", "addessLine__name").text(resp[i].name);
			let addessLine__email = $("<div>").attr("class", "addessLine__email").text(resp[i].email);
			let addessLine__phone = $("<div>").attr("class", "addessLine__phone").text(resp[i].number);
			let addessLine__company = $("<div>").attr("class", "addessLine__company").text(resp[i].company_name);
			let addessLine__tag = $("<div>").attr("class", "addessLine__tag d-flex");
			let addBook__tag = $("<div>").attr("class", "addBook__tag align-center").text("ceo"); //추후에 태그 불러와 넣기
			addessLine__tag.append(addBook__tag);

			addList__addessLine.append(addessLine__chkBoxCover).append(addessLint__favorites).append(addessLine__name).append(addessLine__email).append(addessLine__phone).append(addessLine__company).append(addessLine__tag)
			$(".body__addList").append(addList__addessLine);
		}
	});
}

$(document).ready(function() {
	reloadTags(function(){
		indexSelect($("div[data-title='" + $("#abCurrentMenu").val() + "']"));	
	});
	
	

	$(".modalBody__addBookType>div").on("click", function() { // 주소록 선택 (개인 주소록 / 공유 주소록)
		$(".activeType").removeClass("activeType");
		$(this).addClass("activeType");
		changeTab($(".activeType").attr("id"));
	});


	$(".addBookModal__cancelBtn").on("click", function() { // 모달창 닫기
		$.modal.close();
	})

	$("#addressBookInsert").on("click", function() { // 주소록 저장
		let selectedTagArray = [];
		for (let i = 0; i < $(".selectedTag").length; i++) { // 선택한 태그 값 불러오기
			selectedTagArray.push(parseInt($($(".selectedTag")[i]).find(".selectedTag__delete").attr("selectid")));
		}

		$.ajax({
			url: "/addressbook/insert",
			data: {
				name: $(".modalBody__name").val(),
				is_share: $(".activeType").text() == "공유 주소록" ? 1 : 0,
				email: $(".modalBody__email").val(),
				numberType: $(".modalBody__numberType option:selected").val(),
				number: $(".modalBody__number").val(),
				company_name: $(".modalBody__company").val(),
				dept_name: $(".modalBody__dept").val(),
				job_name: $(".modalBody__job").val(),
				addressType: $(".modalBody__addressType option:selected").val(),
				address: $(".modalBody__address").val(),
				birthType: $(".modalBody__birthType option:selected").val(),
				birth: $(".modalBody__birth").val(),
				memo: $(".modalBody__memo").text(),
				selectedTagArray: selectedTagArray
			},
			type: "post"

		}).done(function(resp) {
			if (resp >= 1) {
				$.modal.close();
				indexSelect($("div[data-title='" + $("#abCurrentMenu").val() + "']"));	
			}
		});
	});

	$("#addressBookTagInsert").on("click", function() { // 태그 저장
		$.ajax({
			url: "/addressbook/tagInsert",
			data: {
				is_share: $(".activeType").text() == "공유 주소록" ? 1 : 0,
				name: $(".modalBody__tagName").val()
			},
			type: "post",
			async: "false"
		}).done(function(resp) { // 현재 선택한 태그 이름 가져와서 선택해주어야함
			if (resp > 0) {
				$(".customMenu").remove();
				reloadTags(function(){indexSelect($("div[data-title='" + $("#abCurrentMenu").val() + "']"));});
				$.modal.close();
				$(".modalBody__tag").append($("<option>").val(resp).text($(".modalBody__tagName").val()));
			}
		});
	});



});


// 주소록 보기 변경 (인덱스 선택)
$(document).on("click", ".toggleInner", function() {
	indexSelect($(this));
});

function indexSelect(target){
	if ($(target).attr("authority") == "personal" || $(target).attr("authority") == "shared")
		reloadAddressBook($(target).attr("authority"), $(target).attr("data-title"));
	else reloadAddressBook(parseInt($(target).val()), $(target).attr("data-title"));

	$("div[data-title='" + $("#abCurrentMenu").val() + "']").css("background-color", "transparent");
	$("#abCurrentMenu").val($(target).attr("data-title"));
	$("div[data-title='" + $("#abCurrentMenu").val() + "']").css("background-color", "#DCEDD4");
}


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


