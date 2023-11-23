// insert modal 창 내용 초기화
function modalInit() { 
	$(".addBookInsertModal__title").text("주소 추가");
	$(".modalBody__addBookType").css("display","flex");
	$("#addressBookUpdate").attr("id","addressBookInsert");
	$("#addressBookUpdate").removeAttr("data-id");
	$(".modalBody__input").val("");
	$(".modalBody__birthType").val("양력");
	$(".modalBody__numberType").val("휴대폰");
	$(".modalBody__addressType").val("회사");
	$(".modalBody__memo").html("");
	$(".activeType").removeClass("activeType");
	$("#personal").addClass("activeType");
	changeTab($(".activeType").attr("id"));
}


// 주소록 변경 시, 태그 새로 불러오기 
function changeTab(tapName, callback) { 
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
		
		if (typeof callback === 'function') {
	        callback();
	    }
	});
}



// 주소 추가 모달 내에서 태그 추가 모달 띄우기
$(document).on("click", ".modalBody__plusBtn>i", function() {
	$(".modalBody__tagName").val("");
	$(".addBookTagInsertModal").modal({
		showClose: false,
		closeExisting: false
	});
});


// 선택한 태그 선택 취소 및 태그 선택 비활성화 해제 (다시 선택 가능하도록)
$(document).on("click", ".selectedTag__delete", function() { 
	let id = $(this).attr("selectId");
	$(`.modalBody__tag option[value=${id}]`).removeAttr("disabled");
	$(this).closest(".selectedTag").remove();
});


// 태그 선택
$(document).on("change", "select[name='modalBody__tag']", function() { 
	tagSelect();
});

function tagSelect(){
	if ($(".selectedTag").length >= 4) { // 태그는 최대 4개까지 선택가능
		Swal.fire({
			icon: "error",
			text: "태그는 최대 4개까지 선택이 가능합니다."
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
}



// 태그 목록 불러와서 인덱스에 넣어주기
function reloadTags(callback) { 
	$.ajax({
		url: "/addressbook/tagSelect",
		async:"false"
	}).done(function(tagList) {
		
		let flag = false;
		
		$.ajax({
			url: "/members/isAdmin",
			async:"false"
		}).done(function(resp) {
			console.log(resp);
			for (let i = 0; i < resp.length; i++) {
				if (resp[i] == "총괄" || resp[i] == "인사") {
					flag = true; 
					console.log("1 "+flag);
					break;
				}
			}
			
			
			$(".customMenu").remove();
			let naviConp__icon = $("<div>").attr("class", "naviConp__icon").html(`<i class="fa-solid fa-tag"></i>`);
			for (let i = 0; i < tagList.length; i++) {
				let naviConp__title = $("<div>").attr("class", "naviConp__title naviConp__titleMini").text(tagList[i].name)
				let toggleInner = $("<div>").attr("class", "naviConp toggleInner customMenu").attr("data-id",tagList[i].id);
				toggleInner.append(naviConp__icon.clone()).append(naviConp__title).attr("data-title", tagList[i].name);
				
				if(flag){
					let removeNavi = $("<div>").attr("class","removeNavi").html('<i class="fa-solid fa-minus"></i>').attr("data-id",tagList[i].id);
					toggleInner.append(removeNavi);
				}
				
				if (tagList[i].is_share) { // 공유주소록
					$(".naviBar__sharedAddress").append(toggleInner);
				} else { // 개인주소록
					$(".naviBar__personalAddress").append(toggleInner);
				}
			}
			
			if($(".naviBar__sharedAddress").find(".toggleMenu").attr("toggleView") == "false"){
				$(".naviBar__sharedAddress .toggleInner").css("display","none");
			}
			if($(".naviBar__personalAddress").find(".toggleMenu").attr("toggleView") == "false"){
				$(".naviBar__personalAddress .toggleInner").css("display","none");
			}
			
			if (typeof callback === 'function') {
		        callback();
		    }
			
			
			
		});
					
					
		
	});
}



//태그 삭제
$(document).on("click",".removeNavi",function(e){
	e.stopPropagation(); // 삭제 버튼 누르면 페이지 이동하지 않음. (이벤트 중단)
	let id = $(this).attr("data-id");
	
	Swal.fire({
	    text: `주소록 [${$(this).closest(".customMenu").attr("data-title")}]을(를) 정말 삭제하시겠습니까?`,
	    showCancelButton: true,
	    allowOutsideClick: false,
	  }).then(function (result) {
	    if (result.isConfirmed) {
	     	$.ajax({
				url:"/addressbook/tagDelete",
				data:{id:id},
				type:"post"
			}).done(function(){
				reloadTags(function(){
					indexSelect($("div[data-id='" + $("#abCurrentMenu").val() + "']"));	
				});
			});
	    } else if (result.isDismissed) {
	      return;
	    }
	  });	
});



// 주소록 출력
function reloadAddressBook(authorityOrTagId, tagId, keyword) {

	let key, value;

	if (typeof authorityOrTagId == "string") {
		key = "is_share";
		if (authorityOrTagId == "personal") value = 0;
		else if (authorityOrTagId == "shared")value = -1;
		else if (authorityOrTagId == "favorite")value = -2;
		else if (authorityOrTagId == "trash")value = -3;
	} else {
		key = "id";
		value = authorityOrTagId;
	}

	$.ajax({
		url: "/addressbook/select",
		data: {
			key: key,
			value: value,
			currentMenu: tagId,
			keyword: keyword
		},
		type: "post"
	}).done(function(resp) {		
		$(".addListHeader__chkBox").prop("checked", false);
		$(".addListHeader__default").css("display","flex");
		$(".addListHeader__select").attr("style","display: none !important");
		$(".addListHeader__selectInTrash").attr("style","display: none !important");
		if(resp.length>0){
			if(resp[0].deleteTag === undefined){ // 삭제된 태그이면 개인 전체 선택되도록
				$("#abCurrentMenu").val(value);
				$(".body__addList>*").remove();
				$(".header__tagName").html($(".toggleInner[data-id='"+value+"']").text()+":&nbsp;<span class='addressCnt'>"+resp.length+"</span>개");
				for (let i = 0; i < resp.length; i++) {
					let addList__addessLine = $("<div>").attr("class", "addList__addessLine d-flex").attr("data-id",resp[i].id).attr("data-regisger",resp[i].emp_id);
					let addessLine__chkBoxCover = $("<div>").attr("class", "addessLine__chkBoxCover align-center");
					let addessLine__chkBox = $("<input type='checkbox'>").attr("class", "addessLine__chkBox")
					addessLine__chkBoxCover.append(addessLine__chkBox);
		
					let addessLint__favorites = $("<div>").attr("class", "addessLint__favorites align-center");
					if(value != -3){ // 휴지통이면 즐겨찾기 출력하지 않음.
						let favorites__icon = $("<i>").attr("class","fa-regular fa-star align-center favorites__icon")
						addessLint__favorites.append(favorites__icon);
						
						if(resp[i].existFavorite == resp[i].id){
							favorites__icon.addClass("chk");
						}
						$(".body__emptyTrash").css("display","none");
					}else{
						// 휴지통 안내 문구 상단에 추가
						$(".body__emptyTrash").css("display","block");
						$(addList__addessLine).attr("data-is_share",resp[i].is_share)
					}
					let addessLine__name = $("<div>").attr("class", "addessLine__name").text(resp[i].name);
					let addessLine__email = $("<div>").attr("class", "addessLine__email").text(resp[i].email);
					
					// 이메일 눌러 메일 작성 페이지로 이동
					// 사내 메일인지 판단
					
					if(resp[i].email !== undefined){
						if(resp[i].email.slice(resp[i].email.length-12,resp[i].email.length)=="@clovers.com"){ 
							$(addessLine__email).on("click", function(e){
								if($(this).text() != ""){
									e.stopPropagation(); // 상세보기 모달창 뜨지 않게 함. (기존 이벤트 중단)
									location.href = "/mail/sendSetEmail?addressEmail="+$(this).text();	
								}
							})
						}
					}
					
					

					let addessLine__phone = $("<div>").attr("class", "addessLine__phone").text(resp[i].number);
					let addessLine__company = $("<div>").attr("class", "addessLine__company").text(resp[i].company_name);
					let addessLine__tag = $("<div>").attr("class", "addessLine__tag d-flex");
				
					if (resp[i].tag_ids && resp[i].tag_names) {
						let tagIdArr = resp[i].tag_ids.split(",");
						let tagNameArr = resp[i].tag_names.split(",");
						for(let i=0; i<tagIdArr.length; i++){
							let addBook__tag = $("<div>").attr("class", "addBook__tag align-center").text(tagNameArr[i]).attr("selectid",tagIdArr[i]);
							addessLine__tag.append(addBook__tag);
						}
					}			
		
					addList__addessLine.append(addessLine__chkBoxCover).append(addessLint__favorites).append(addessLine__name).append(addessLine__email).append(addessLine__phone).append(addessLine__company).append(addessLine__tag)
					$(".body__addList").append(addList__addessLine);
				}
			}else{
				reloadTags(function(){
					indexSelect($("div[data-id='"+0+"']"));	
				});
				Swal.fire({
					icon: "error",
					text: "삭제된 태그입니다."
				});
			}
		}else{
			$("#abCurrentMenu").val(value);
			$(".body__addList>*").remove();
			$(".header__tagName").html($(".toggleInner[data-id='"+value+"']").text()+":&nbsp;<span class='addressCnt'>"+resp.length+"</span>개");
		}
	});
}


// 주소 입력 모달창 - 전화번호 - 숫자만 입력 가능, 하이픈 자동 생성 (010-1111-1111 or 041-111-111 형식)
$(document).on("input",".modalBody__number",function(){ 
	$(this).val($(this).val().replace(/[^0-9]/g, '').replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`).replace(/^(\d{2,3})(\d{3,4})(\d{4})(\d{1,})$/, `$1-$2-$3`));
});


// 주소 UPDATE OR INSERT 시 데이터 셋팅
function settingData(){
	let selectedTagArray = [];
	for (let i = 0; i < $(".selectedTag").length; i++) { // 선택한 태그 값 불러오기
		selectedTagArray.push(parseInt($($(".selectedTag")[i]).find(".selectedTag__delete").attr("selectid")));
	}
	
	if($(".modalBody__name").val() == ""){ // 이름 필수 입력
		Swal.fire({
			icon: "error",
			text: "이름을 입력해주세요."
		});
		$(".modalBody__name").focus();
		return ;
	}
	
	if($(".modalBody__email").val() != ""){ // 이메일 정규식
		// 둘 중 하나. 진짜 이메일 형식 or 영문+숫자 조합
		let emailRegex = /^[a-zA-Z0-9]+@[a-z]+\.[a-z]+(\.*[a-z])*$/; // 이메일 형식 ex. test@clovers.com or test@clovers.co.kr
		//let empIdRegex = /^[0-9]{4}DT[0-9]{2}[0-9]{3}$/; // 사번 형식 ex. 2023DT02020 
		let emailRegex2 = /^[a-zA-Z0-9]+$/; // 사번 형식 ex. 2023DT02020 
		let val = $(".modalBody__email").val();
		if(!emailRegex.test(val) && !emailRegex2.test(val)){
			Swal.fire({
				icon: "error",
				text: "이메일 형식에 맞게 입력해주세요."
			});
			$(".modalBody__email").focus();
			return ;
		}
		
	}
	
	if($(".modalBody__number").val()!=""){ // 전화번호 정규식		
		let regex = /^.{11,13}$/; // 10~11자 사이만 입력가능
		console.log(regex.test($(".modalBody__number").val()));
		if(!regex.test($(".modalBody__number").val())){
			Swal.fire({
				icon: "error",
				text: "전화번호는 9 ~ 11자로 입력해주세요."
			});
			$(".modalBody__number").focus();
			return ;
		}
	}
	

	let data = {
		name: $(".modalBody__name").val(),
		is_share: $(".activeType").text() == "공유 주소록" ? 1 : 0,
		email: $(".modalBody__email").val(),
		numberType: $(".modalBody__numberType option:selected").val(),
		number: $(".modalBody__number").val(),
		company_name: $(".modalBody__company_name").val(),
		dept_name: $(".modalBody__dept_name").val(),
		job_name: $(".modalBody__job_name").val(),
		addressType: $(".modalBody__addressType option:selected").val(),
		address: $(".modalBody__address").val(),
		birthType: $(".modalBody__birthType option:selected").val(),
		birth: $(".modalBody__birth").val(),
		memo: $(".modalBody__memo").text(),
		selectedTagArray: selectedTagArray
	}
	
	return data;
}

$(document).ready(function() {
	reloadTags(function(){ // 존재하는 태그 출력
		indexSelect($("div[data-id='" + $("#abCurrentMenu").val() + "']"));	
	});
	
	$(".modalBody__addBookType>div").on("click", function() { // 주소록 선택 (개인 주소록 / 공유 주소록)
		$(".activeType").removeClass("activeType");
		$(this).addClass("activeType");
		changeTab($(".activeType").attr("id"));
	});


	$(".addBookModal__cancelBtn").on("click", function() { // 모달창 닫기
		$.modal.close();
	});

	$(document).on("click","#addressBookInsert", function() { // 주소록 저장
	// 유효성 검사
		let data = settingData();
		if(data != null){
			$.ajax({
				url: "/addressbook/insert",
				data: data,
				type: "post"
	
			}).done(function(resp) {
				if (resp >= 1) {
					$.modal.close();
					indexSelect($(".activeMenu"));
				}
			});
		}
		
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
				$.modal.close();
				reloadTags(function(){
					indexSelect($("div[data-id='" + $("#abCurrentMenu").val() + "']"));	
				});
				let prevSelectTag = $(".selectedTag__delete").map((i,e)=>{
					return parseInt($(e).attr("selectid"));
				})
				prevSelectTag.push(parseInt(resp)) // 새로 등록한 태그 추가

				// changeTab($(".activeType").attr("id"));
				// 여기$(".modalBody__tag").append($("<option>").val(resp).text($(".modalBody__tagName").val()));
				changeTab($(".activeType").attr("id"),function(){
					for(let i=0; i<prevSelectTag.length; i++){
						console.log(prevSelectTag[i])
						$("select[name='modalBody__tag']").val(prevSelectTag[i]).prop("selected",true);
						tagSelect();
					}
				});
			}
		});
	});



});


// 주소록 보기 변경 (인덱스 선택)
$(document).on("click", ".toggleInner", function() {
	$(".searchBar__input").val("");
	indexSelect($(this));
});

function indexSelect(target){
	$(".activeMenu").removeClass("activeMenu");
	
	if($(target).length == 0){ // 만약 메뉴가 삭제되었다면 개인 전체 선택
		reloadAddressBook("personal", 0);
		$(".toggleInner[authority='personal']").addClass("activeMenu");
	}
	else if ($(target).attr("authority") == "personal" || $(target).attr("authority") == "shared" || $(target).attr("authority") == "favorite" || $(target).attr("authority") == "trash") // 선택한 메뉴가 개인 전체 혹은 공유 전체일 경우
		reloadAddressBook($(target).attr("authority"), $(target).attr("data-id"));
	else reloadAddressBook(parseInt($(target).attr("data-id")), $(target).attr("data-id")); // 그 외 태그 선택

	if($(target).attr("authority") == "trash")
		$(".body__emptyTrash").css("display","block");
	$(target).addClass("activeMenu");
}


// 즐겨찾기 추가
function favoriteInsert(selectedFav,address_book_id){
	$.ajax({
		url:"/addressbook/favoriteInsert",
		data:{address_book_id:address_book_id},
		type:"post"
	}).done(function(resp){
		if(resp != 0){
			$(selectedFav).addClass("chk");
			if($(selectedFav).hasClass("viewFavorite")){
				$(".addList__addessLine[data-id='"+$(selectedFav).attr("data-id")+"']").find(".favorites__icon").addClass("chk");
			}
			
		}else{
			Swal.fire({
				icon: "error",
				text: "이미 즐겨찾기한 주소록입니다."
			});
			$(selectedFav).addClass("chk");
		}
	});
}

// 즐겨찾기 삭제
function favoriteDelete(selectedFav, address_book_id){
	$.ajax({
		url:"/addressbook/favoriteDelete",
		data:{address_book_id:address_book_id},
		type:"post"
	}).done(function(){
		$(selectedFav).removeClass("chk");
		if($(selectedFav).hasClass("viewFavorite")){
			$(".addList__addessLine[data-id='"+$(selectedFav).attr("data-id")+"']").find(".favorites__icon").removeClass("chk");
		}
		if($("#abCurrentMenu").val()==-2){
			$(".addList__addessLine[data-id='"+address_book_id+"']").remove();
			$(".addressCnt").text(parseInt($(".addressCnt").text())-1);
		}
	});
}

// 주소록 상세보기 즐겨찾기 버튼 토글
$(document).on("click", ".addBookViewModal__header .favorites__icon", function() {
	let selectedFav = $(this);
	if ($(this).hasClass("chk")) {
		favoriteDelete(selectedFav, $(selectedFav).attr("data-id"));
		
	} else {
		favoriteInsert(selectedFav, $(selectedFav).attr("data-id"));
	}
});


// 즐겨찾기 (메인 홈)
$(document).on("click", ".addList__addessLine .favorites__icon", function(e) { 
	e.stopPropagation(); // 상세보기 창 뜨지 않도록 이벤트 중단
	let selectedFav = $(this);
	if ($(this).hasClass("chk")) {
		favoriteDelete(selectedFav, $(selectedFav).closest(".addList__addessLine").attr("data-id"));
	} else { 
		favoriteInsert(selectedFav,$(selectedFav).closest(".addList__addessLine").attr("data-id"));
	}
});


// 주소록 home 선택 toggle
$(document).on("click", ".addessLine__chkBox", function(e) { 
	e.stopPropagation();
	if ($(this).is(":checked")) {
		$(this).closest(".addList__addessLine").css("backgroundColor", "#DCEDD4");
		// 선택하면 위에 목록 출력
		$(".addListHeader__default").css("display","none");

		let currentMenu = parseInt($("#abCurrentMenu").val());
		if(currentMenu == -3){ // 휴지통
			$(".addListHeader__selectInTrash").attr("style","display: flex !important");
			$(".addListHeader__select").attr("style","display: none !important");	
		}else{
			$.ajax({
				url: "/members/isAdmin",
				async:"false"
			}).done(function(resp) {
				$(".addListHeader__selectInTrash").attr("style","display: none !important");
				$(".addListHeader__select").attr("style","display: flex !important");
				$(".addListHeader__trash").attr("style","display:none !important");
				if($(".activeMenu").parent().find(".toggleMenu").find(".naviConp__addTag").attr("data-isshare")==1){
					for (let i = 0; i < resp.length; i++) {
						if (resp[i] == "총괄" || resp[i] == "인사") {$(".addListHeader__trash").attr("style","display:flex !important"); break;}
					}
				}else{
					$(".addListHeader__trash").attr("style","display:flex !important");
				}
			});
			
			if(currentMenu == -2){ // 중요 주소록
				$(".addListHeader__copy").attr("style","display: none !important");
			}else{
				let currentNaviParent = $(".toggleInner[data-id='"+parseInt($("#abCurrentMenu").val())+"']").parent().find(".toggleMenu .naviConp__title").text();
				let id;
				if(currentNaviParent == "개인 주소록") {currentNaviParent = "공유 주소록"; id=1;}
				else {currentNaviParent = "개인 주소록"; id=0;}
				$(".addListHeader__copy").text(currentNaviParent+"에 복사").attr("data-id",id);
			}
		}
	
	} else {
		$(this).closest(".addList__addessLine").css("backgroundColor", "transparent");
		
		if($(".addessLine__chkBox:checked").length==0){
			$(".addListHeader__default").css("display","flex");
			$(".addListHeader__select").attr("style","display: none !important");
			$(".addListHeader__selectInTrash").attr("style","display: none !important");
		}

	}

	$(".addListHeader__selectCnt").text($(".addessLine__chkBox:checked").length);
	$(".addListHeader__copy").text();
	if ($(".addListHeader__chkBox").is(":checked")) $(".addListHeader__chkBox").prop("checked", false);
});


// 주소록 home 전체 선택 toggle
$(document).on("click", ".addListHeader__chkBox", function() { 
	if ($(this).is(":checked") == true) {
		$(".addList__addessLine").css("backgroundColor", "#DCEDD4");
		$(".addessLine__chkBox").prop("checked", true);
		
		$(".addListHeader__default").css("display","none");
		if(parseInt($("#abCurrentMenu").val()) == -3){
			$(".addListHeader__selectInTrash").attr("style","display: flex !important");
			$(".addListHeader__select").attr("style","display: none !important");	
		}else{
			$.ajax({
				url: "/members/isAdmin",
				async:"false"
			}).done(function(resp) {
				$(".addListHeader__selectInTrash").attr("style","display: none !important");
				$(".addListHeader__select").attr("style","display: flex !important");
				$(".addListHeader__trash").attr("style","display:none !important");
				if($(".activeMenu").parent().find(".toggleMenu").find(".naviConp__addTag").attr("data-isshare")==1){
					for (let i = 0; i < resp.length; i++) {
						if (resp[i] == "총괄" || resp[i] == "인사") {$(".addListHeader__trash").attr("style","display:flex !important"); break;}
					}
				}else{
					$(".addListHeader__trash").attr("style","display:flex !important");
				}
			});
				
			
			
			if(parseInt($("#abCurrentMenu").val()) == -2){ // 중요 주소록
				$(".addListHeader__copy").attr("style","display: none !important");
			}else{
				let currentNaviParent = $(".toggleInner[data-id='"+parseInt($("#abCurrentMenu").val())+"']").parent().find(".toggleMenu .naviConp__title").text();
				let id;
				if(currentNaviParent == "개인 주소록") {currentNaviParent = "공유 주소록"; id=1;}
				else {currentNaviParent = "개인 주소록"; id=0;}
				$(".addListHeader__copy").text(currentNaviParent+"에 복사").attr("data-id",id);
			}
		}
		$(".addListHeader__selectCnt").text($(".addessLine__chkBox:checked").length);
	} else {
		$(".addList__addessLine").css("backgroundColor", "transparent");
		$(".addessLine__chkBox").prop("checked", false);
		$(".addListHeader__default").css("display","flex");
		$(".addListHeader__select").attr("style","display: none !important");
		$(".addListHeader__selectInTrash").attr("style","display: none !important");
	}
});




// 주소 삭제 함수
function deleteAddress(data){
	
	if($(".activeMenu").parent().find(".toggleMenu").find(".naviConp__addTag").attr("data-isshare")==1){
		$.ajax({
			url: "/members/isAdmin"
		}).done(function(resp) {
			
			let flag = false;
			for (let i = 0; i < resp.length; i++) {
				if (resp[i] == "총괄" || resp[i] == "인사") {
					flag = true;
					let url
					if(parseInt($("#abCurrentMenu").val())==-3){ // 현재 인덱스가 휴지통이면 영구삭제
						 url="/addressbook/delete";
					}else{ // 아니면 휴지통으로 이동
						url="/addressbook/trash";
					}
					
					$.ajax({
						url:url,
						data:data,
						type:"post"
					}).done(function(){
						$.modal.close();
						indexSelect($(".activeMenu"));	
					});
					break;
				}
			}
			if(!flag){
				Swal.fire({
					icon: "error",
					text: "공유 주소록 삭제 권한이 없습니다."
				});
			}

		})
	}else{
		let url
		if(parseInt($("#abCurrentMenu").val())==-3){ // 현재 인덱스가 휴지통이면 영구삭제
			 url="/addressbook/delete";
		}else{ // 아니면 휴지통으로 이동
			url="/addressbook/trash";
		}
		
		$.ajax({
			url:url,
			data:data,
			type:"post"
		}).done(function(){
			$.modal.close();
			indexSelect($(".activeMenu"));	
		});
	}
	
}

// 정보창에서 삭제하기 클릭 (주소록 휴지통으로 이동)
$(document).on("click","#addBookModal__deleteBtn",function(){
	deleteAddress({id:$(this).attr("data-id")})
});

// 체크 박스로 주소 삭제
$(document).on("click",".addListHeader__trash",function(){
	let ids = $(".addessLine__chkBox:checked").toArray().map((e)=>{
		return parseInt($(e).closest(".addList__addessLine").attr("data-id"));
	})
	deleteAddress({ids:ids});
});

// 체크 박스로 영구 삭제
$(document).on("click",".addListHeader__delete",function(){
	let ids = $(".addessLine__chkBox:checked").toArray().map((e)=>{
		return parseInt($(e).closest(".addList__addessLine").attr("data-id"));
	})
	deleteAddress({ids:ids});
});

// 체크 박스로 다른 주소록에 주소 복사
$(document).on("click",".addListHeader__copy",function(){
	let ids = $(".addessLine__chkBox:checked").toArray().map((e)=>{
		return parseInt($(e).closest(".addList__addessLine").attr("data-id"));
	})
	
	let is_share = $(this).attr("data-id");
	$.ajax({
		url:"/addressbook/copyAddress",
		data:{
			is_share: is_share,
			ids: ids
		}
	}).done(function(resp){
		if(resp>0){		
			Swal.fire({
				icon: "info",
				text: "주소가 복사되었습니다."
			});
			indexSelect($(".toggleInner[data-id='"+(is_share==1?-1:0)+"']"));
		}
	});
});


// 주소록 클릭 시 상세 정보창
let addressKeyName = {name:"이름",email:"이메일",number:"전화",birth:"생일",company_name:"회사",dept_name:"부서",job_name:"직급",address:"주소",memo:"메모"}
$(document).on("click",".addList__addessLine ",function(){ 
	$.ajax({
		url:"/addressbook/selectById",
		data:{id:$(this).attr("data-id")},
		type:"post"
	}).done(function(resp){
		$(".addBookModal__modalBody>*").remove();
		$(".viewFavorite").removeClass("chk");
		
		for(let key in resp){
			if(resp[key]!==""&&(key!="id"&&key!="is_share"&&key!="addressType"&&key!="birthType"&&key!="emp_id"&&key!="numberType"&&key!="name"&&key!="tag_names"&&key!="existFavorite"&&key!="trash"&&key!="is_emp")){
				let modalBody__line = $("<div>").attr("class","modalBody__line d-flex");
				let modalBody__title = $("<div>").attr("class","modalBody__title d-flex");
				let modalBody__content = $("<div>").attr("class","modalBody__content modalView__content");
				
				
				if(key=="tag_ids"){ // 태그 출력
					modalBody__title.text("태그");
					modalBody__content.addClass("modalViewBody__tags");
	
					if (resp.tag_ids && resp.tag_names) {
						let tagIdArr = resp.tag_ids.split(",");
						let tagNameArr = resp.tag_names.split(",");
						for(let i=0; i<tagIdArr.length; i++){
							let addBook__tag = $("<div>").attr("class", "addBook__tag align-center").text(tagNameArr[i]).attr("selectid",tagIdArr[i]);
							modalBody__content.append(addBook__tag);
						}
					}				
						
				}else{
					modalBody__title.text(addressKeyName[key]);
					modalBody__content.addClass(`modalViewBody__${key}`).text(resp[key]);		
				}
				
				if(key=="address"||key=="birth"||key=="number") modalBody__content.text(`${resp[key]} (${resp[key+"Type"]})`);
				$(".addBookModal__modalBody").append(modalBody__line.append(modalBody__title).append(modalBody__content));	
								
			}			
			
			$(".viewFavorite").attr("data-id",resp.id);
			if(resp.existFavorite == resp.id){
				$(".viewFavorite").addClass("chk");
			}

			
		}
		
		if($("#abCurrentMenu").val()==-3){ // 휴지통이면
			// 즐겨찾기 가리기
			$(".addBookViewModal__favorites").css("display","none");
			
			// 수정 버튼 대신 복구 버튼 출력
			$("#addBookModal__updateBtn").attr("id","addBookModal__restoreBtn").text("복구");
			// 삭제 버튼 대신 영구 삭제 버튼 출력
			$("#addBookModal__deleteBtn").text("영구 삭제");
		}else{ // 휴지통 아니면 	
			// 즐겨찾기 출력
			$(".addBookViewModal__favorites").css("display","flex");
			
			// 복구 버튼 대신 수정 버튼 출력
			$("#addBookModal__restoreBtn").attr("id","addBookModal__updateBtn").text("수정");
			// 영구 삭제 버튼 대신 삭제 버튼 출력
			$("#addBookModal__deleteBtn").text("삭제");
		}
		
		$(".addBookViewModal__title").text(resp.name);
		$("#addressBookInsert").attr("id","addressBookUpdate");
		$("#addBookModal__updateBtn").attr("data-id",resp.id);
		$("#addBookModal__restoreBtn").attr("data-id",resp.id);
		$("#addBookModal__deleteBtn").attr("data-id",resp.id);	
		
		
		
		
		$(".addBookModal__deleteBtn").css("display","block");
		$(".addListHeader__trash").css("display","block");
		$(".addListHeader__delete").css("display","block");
		$("#addBookModal__updateBtn").css("display","block");
		if($(".activeMenu").parent().find(".toggleMenu").find(".naviConp__addTag").attr("data-isshare")==1){
			$.ajax({
				url: "/members/isAdmin",
				async:false
			}).done(function(resp) {
				$(".addBookModal__deleteBtn").css("display","none");
				$(".addListHeader__trash").css("display","none");
				$(".addListHeader__delete").css("display","none");
				$("#addBookModal__updateBtn").css("display","none");
				for (let i = 0; i < resp.length; i++) {
					// 총괄 권한	
					if (resp[i] == "총괄" || resp[i] == "인사") {
						$(".addBookModal__deleteBtn").css("display","block");
						$(".addListHeader__trash").css("display","block");
						$(".addListHeader__delete").css("display","block");
						$("#addBookModal__updateBtn").css("display","block");
						break;
					}	
				}
			})
		}
		
		$(".addBookViewModal").modal({
			showClose: false
		});
	})
});


// 정보창에서 수정하기 클릭 (최신 정보 불러오기)
$(document).on("click","#addBookModal__updateBtn",function(){
	let id = $(this).attr("data-id");
	if($(".activeMenu").parent().find(".toggleMenu").find(".naviConp__addTag").attr("data-isshare")==1){
		$.ajax({
			url: "/members/isAdmin",
			async:false
		}).done(function(resp) {
			for (let i = 0; i < resp.length; i++) {
				// 총괄 권한	
				if (resp[i] == "총괄" || resp[i] == "인사") {
					$.ajax({
						url:"/addressbook/selectById",
						data:{id:id},
						type:"post"
					}).done(function(resp){
						modalInit();
						$(".addBookInsertModal__title").text("주소 변경");
						for(let key in resp){
							$(`.modalBody__${key}`).val(resp[key]);
							if(key=="memo") $(`.modalBody__${key}`).text(resp[key]);
							if(key=="is_share"){
								if(resp[key] == 0){
									key = "personal";
								}else{
									key = "shared";
								}
								$(".activeType").removeClass("activeType");
								$(`#${key}`).addClass("activeType");
								changeTab($(".activeType").attr("id"),function(){
									if (resp.tag_ids && resp.tag_names) {
										let tagIdArr = resp.tag_ids.split(",");
										for(let i=0; i<tagIdArr.length; i++){
											$("select[name='modalBody__tag']").val(tagIdArr[i]).prop("selected",true);
											tagSelect();
										}
									}	
								});
							}
						}
							
						$(".modalBody__addBookType").css("display","none");	
						$("#addressBookInsert").attr("id","addressBookUpdate");
						$("#addressBookUpdate").attr("data-id",resp.id);
						$(".addBookInsertModal").modal({
							showClose: false
						});
					})
				}	
				break;
			}
		})
				
	}else{
		$.ajax({
			url:"/addressbook/selectById",
			data:{id:id},
			type:"post"
		}).done(function(resp){
			modalInit();
			$(".addBookInsertModal__title").text("주소 변경");
			for(let key in resp){
				$(`.modalBody__${key}`).val(resp[key]);
				if(key=="memo") $(`.modalBody__${key}`).text(resp[key]);
				if(key=="is_share"){
					if(resp[key] == 0){
						key = "personal";
					}else{
						key = "shared";
					}
					$(".activeType").removeClass("activeType");
					$(`#${key}`).addClass("activeType");
					changeTab($(".activeType").attr("id"),function(){
						if (resp.tag_ids && resp.tag_names) {
							let tagIdArr = resp.tag_ids.split(",");
							for(let i=0; i<tagIdArr.length; i++){
								$("select[name='modalBody__tag']").val(tagIdArr[i]).prop("selected",true);
								tagSelect();
							}
						}	
					});
				}
			}
				
			$(".modalBody__addBookType").css("display","none");	
			$("#addressBookInsert").attr("id","addressBookUpdate");
			$("#addressBookUpdate").attr("data-id",resp.id);
			$(".addBookInsertModal").modal({
				showClose: false
			});
		})
	}	
	
});


// 휴지통에서 주소 복원



// 복원 함수
function restoreAddress(data){
	$.ajax({
		url:"/addressbook/restore",
		data:data,
		type:"post"
	}).done(function(){
		$.modal.close();
		indexSelect($(".activeMenu"));	
	})
}

// 휴지통 내 상세보기 모달창에서 복원하기
$(document).on("click","#addBookModal__restoreBtn",function(){
	restoreAddress({id:$(this).attr("data-id")})
});

// 체크 박스로 복원
$(document).on("click",".addListHeader__restore",function(){	
	let ids = $(".addessLine__chkBox:checked").toArray().map((e)=>{
		return parseInt($(e).closest(".addList__addessLine").attr("data-id"));
	})
	restoreAddress({ids:ids});
});

// 주소 업데이트
$(document).on("click","#addressBookUpdate",function(){
	let data = settingData();
	data.id = $(this).attr("data-id");
	
	if($(".activeMenu").parent().find(".toggleMenu").find(".naviConp__addTag").attr("data-isshare")==1){
		$.ajax({
			url: "/members/isAdmin",
			async:false
		}).done(function(resp) {
			for (let i = 0; i < resp.length; i++) {
				// 총괄 권한	
				if (resp[i] == "총괄" || resp[i] == "인사") {
					$.ajax({
						url: "/addressbook/update",
						data: data,
						type: "post"
					}).done(function(resp) {
						if (resp >= 1) {
							$.modal.close();
							indexSelect($(".activeMenu"));	
						}
					});
					break;
				}
			}
		});
	}else{
		$.ajax({
			url: "/addressbook/update",
			data: data,
			type: "post"
		
		}).done(function(resp) {
			if (resp >= 1) {
				$.modal.close();
				indexSelect($(".activeMenu"));	
			}
		});
	}
});







// 검색 기능
$(document).on("keyup",".searchBar__input",function(){
	
	let tagId = parseInt($("#abCurrentMenu").val());
	let authorityOrTagId;
	if(tagId == 0){
		authorityOrTagId = "personal";
	}else if(tagId == -1){
		authorityOrTagId = "shared";
	}else if(tagId == -2){
		authorityOrTagId = "favorite";
	}else if(tagId > 0){
		authorityOrTagId = tagId;
	}
	let keyword = $(".searchBar__input").val();
	
	reloadAddressBook(authorityOrTagId, tagId, keyword);
	
});


// 휴지통에서 즉시 삭제 기능 
$(document).on("click",".emptyTrash__emptyTrashBtn",function(){
	Swal.fire({
		text: "정말 휴지통을 비우시겠습니까?",
		showCancelButton: true,
		allowOutsideClick: false,
	}).then(function(result) {
		if (result.isConfirmed) {
			$.ajax({
				url:"/addressbook/immediatelyEmpty"
			}).done(function(resp){
				if(resp==0){
					Swal.fire({
						icon: "error",
						text: "휴지통이 비어있습니다."
					});
				}
				indexSelect($(".activeMenu"));	
			});
		} else if (result.isDismissed) {
			return;
		}
	});	
});



