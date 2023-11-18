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

function changeTab(tapName, callback) { // 주소록 변경 시, 태그 새로 불러오기
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


$(document).on("click", ".modalBody__plusBtn>i", function() { // 태그 추가 모달 (주소 추가 내)
	$(".modalBody__tagName").val("");
	$(".addBookTagInsertModal").modal({
		showClose: false,
		closeExisting: false
	});
});

/*$(document).on("click",".naviConp__addTag",function(e){ // 태그 추가 모달 (주소록 오른쪽 hover 버튼	 -> 실행 순서 때문에 navi js파일로 이동
	e.stopPropagation(); // 삭제 버튼 누르면 페이지 이동하지 않음. (이벤트 중단)
	
	$(".modalBody__tagName").val("");
	$(".activeType").removeClass("activeType");
	if($(this).attr("data-isShare") == 0){
		$("#personal").addClass("activeType");
	}else{
		$("#shared").addClass("activeType");
	}
	changeTab($(".activeType").attr("id"));
	
	$(".addBookTagInsertModal").modal({
		showClose: false,
		closeExisting: false
	});
});
*/
$(document).on("click", ".selectedTag__delete", function() { // 선택한 태그 취소 및 비활성화 취소
	let id = $(this).attr("selectId");
	$(`.modalBody__tag option[value=${id}]`).removeAttr("disabled");
	$(this).closest(".selectedTag").remove();
});


$(document).on("change", "select[name='modalBody__tag']", function() { // 태그 선택	
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

function reloadTags(callback) { // 태그 목록 불러와서 인덱스에 넣어주기
	$.ajax({
		url: "/addressbook/tagSelect",
		async:"false"
	}).done(function(tagList) {
		$(".customMenu").remove();
		let naviConp__icon = $("<div>").attr("class", "naviConp__icon").html(`<i class="fa-solid fa-tag"></i>`);
		for (let i = 0; i < tagList.length; i++) {
			let naviConp__title = $("<div>").attr("class", "naviConp__title naviConp__titleMini").text(tagList[i].name)
			let toggleInner = $("<div>").attr("class", "naviConp toggleInner customMenu").attr("data-id",tagList[i].id);
			let removeNavi = $("<div>").attr("class","removeNavi").html('<i class="fa-solid fa-minus"></i>').attr("data-id",tagList[i].id);
			toggleInner.append(naviConp__icon.clone()).append(naviConp__title).attr("data-title", tagList[i].name).append(removeNavi);

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


function reloadAddressBook(authorityOrTagId, tagId, keyword) {
	//console.log(`authOrId: ${authorityOrTagId}, id: ${tagId}`);

	let key, value;

	if (typeof authorityOrTagId == "string") {
		key = "is_share";
		if (authorityOrTagId == "personal") value = 0;
		else if (authorityOrTagId == "shared")value = -1;
		else if (authorityOrTagId == "favorite")value = -2;
	} else {
		key = "id";
		value = authorityOrTagId;
	}


	console.log(`${key} : ${value} : ${tagId} : ${keyword}`);
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
		if(resp.length>0){
			if(resp[0].deleteTag === undefined){ // 삭제된 태그이면 개인 전체 선택되도록
				$("#abCurrentMenu").val(value);
				$(".body__addList>*").remove();
				$(".header__tagName").html($(".toggleInner[data-id='"+value+"']").text()+":&nbsp;<span class='addressCnt'>"+resp.length+"</span>개");
				for (let i = 0; i < resp.length; i++) {
					let addList__addessLine = $("<div>").attr("class", "addList__addessLine d-flex").attr("data-id",resp[i].id);
					let addessLine__chkBoxCover = $("<div>").attr("class", "addessLine__chkBoxCover align-center");
					let addessLine__chkBox = $("<input type='checkbox'>").attr("class", "addessLine__chkBox")
					addessLine__chkBoxCover.append(addessLine__chkBox);
		
					let addessLint__favorites = $("<div>").attr("class", "addessLint__favorites align-center");
					//.html(`<i class="fa-regular fa-star align-center favorites__icon"></i>`);
					let favorites__icon = $("<i>").attr("class","fa-regular fa-star align-center favorites__icon")
					addessLint__favorites.append(favorites__icon);
					let addessLine__name = $("<div>").attr("class", "addessLine__name").text(resp[i].name);
					let addessLine__email = $("<div>").attr("class", "addessLine__email").text(resp[i].email);
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
		
					if(resp[i].existFavorite == resp[i].id){
						favorites__icon.addClass("chk");
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

// 주소 UPDATE OR INSERT 시 데이터 셋팅
function settingData(){
	let selectedTagArray = [];
	for (let i = 0; i < $(".selectedTag").length; i++) { // 선택한 태그 값 불러오기
		selectedTagArray.push(parseInt($($(".selectedTag")[i]).find(".selectedTag__delete").attr("selectid")));
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
		$.ajax({
			url: "/addressbook/insert",
			data: settingData(),
			type: "post"

		}).done(function(resp) {
			if (resp >= 1) {
				$.modal.close();
				indexSelect($(".activeMenu"));	
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
				reloadTags(function(){indexSelect($("div[data-id='" + $("#abCurrentMenu").val() + "']"));});
				$.modal.close();
				$(".modalBody__tag").append($("<option>").val(resp).text($(".modalBody__tagName").val()));
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
	else if ($(target).attr("authority") == "personal" || $(target).attr("authority") == "shared" || $(target).attr("authority") == "favorite") // 선택한 메뉴가 개인 전체 혹은 공유 전체일 경우
		reloadAddressBook($(target).attr("authority"), $(target).attr("data-id"));
	else reloadAddressBook(parseInt($(target).attr("data-id")), $(target).attr("data-id")); // 그 외 태그 선택

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

$(document).on("click", ".addList__addessLine .favorites__icon", function(e) { // 즐겨찾기 (메인 홈)
	e.stopPropagation(); // 상세보기 창 뜨지 않도록 이벤트 중단
	let selectedFav = $(this);
	if ($(this).hasClass("chk")) {
		favoriteDelete(selectedFav, $(selectedFav).closest(".addList__addessLine").attr("data-id"));
	} else { 
		favoriteInsert(selectedFav,$(selectedFav).closest(".addList__addessLine").attr("data-id"));
	}
});

$(document).on("click", ".addessLine__chkBox", function(e) { // 주소록 home 선택 toggle
	e.stopPropagation();
	if ($(this).is(":checked")) {
		$(this).closest(".addList__addessLine").css("backgroundColor", "#DCEDD4");
	} else {
		$(this).closest(".addList__addessLine").css("backgroundColor", "transparent");
	}

	if ($(".addListHeader__chkBox").is(":checked")) $(".addListHeader__chkBox").prop("checked", false);
});

$(document).on("click", ".addListHeader__chkBox", function() { // 주소록 home 전체 선택 toggle
	if ($(this).is(":checked") == true) {
		$(".addList__addessLine").css("backgroundColor", "#DCEDD4");
		$(".addessLine__chkBox").prop("checked", true);
	} else {
		$(".addList__addessLine").css("backgroundColor", "transparent");
		$(".addessLine__chkBox").prop("checked", false);
	}
});



// 주소록 클릭 시 샂세 정보창
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
			if(resp[key]!==""&&(key!="id"&&key!="is_share"&&key!="addressType"&&key!="birthType"&&key!="emp_id"&&key!="numberType"&&key!="name"&&key!="tag_names"&&key!="existFavorite")){
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
		
		$(".addBookViewModal__title").text(resp.name);
		$("#addressBookInsert").attr("id","addressBookUpdate");
		$("#addBookModal__updateBtn").attr("data-id",resp.id);
		$("#addBookModal__deleteBtn").attr("data-id",resp.id);	
		
		$(".addBookViewModal").modal({
			showClose: false
		});
	})
});


// 정보창에서 수정하기 클릭 (최신 정보 불러오기)
$(document).on("click","#addBookModal__updateBtn",function(){
	$.ajax({
		url:"/addressbook/selectById",
		data:{id:$(this).attr("data-id")},
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
});


// 주소 업데이트
$(document).on("click","#addressBookUpdate",function(){
	let data = settingData();
	data.id = $(this).attr("data-id");
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
});


// 정보창에서 삭제하기 클릭 (주소록 삭제)
$(document).on("click","#addBookModal__deleteBtn",function(){
	$.ajax({
		url:"/addressbook/delete",
		data:{id:$(this).attr("data-id")},
		type:"post"
	}).done(function(){
		$.modal.close();
		indexSelect($(".activeMenu"));	
	});
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