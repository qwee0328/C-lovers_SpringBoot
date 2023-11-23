$(document).ready(function () {
	// 예약 발송 드롭다운 버튼 눌렀을 때
    $(".sendReserve__icon").on("click", function () {
        // sendReserve__dropDown 클래스에 hide가 있으면 show로, show가 있으면 hide로 토글
        $(".sendReserve__dropDown").toggleClass("hide show");

        // sendReserve__dropDown의 하위 요소인 dropDown__box의 디스플레이 속성을 토글
        $(".sendReserve__dropDown .dropDown__box").toggle();
    });
    
    // input type=date에서 현재 날짜 이전은 선택 불가능
    // 현재 날짜 가져오기
    var currentDate = new Date().toISOString().split("T")[0];
    // input 요소의 min 속성 설정
    $("#send_date").attr("min", currentDate);
	
	// 예약 드롭다운 확인 버튼 눌렀을 때
	$(".reserve__btn").on("click", function(e) {
		e.preventDefault(); // a 태그 페이지 이동하지 않도록
		
		let sendDate = $("#send_date").val();
		let sendHour = $("#send_hour").val();
		let sendMinute = $("#send_minute").val();
		
		let reserveDate = sendDate + " " + sendHour + ":" + sendMinute + ":00";
		
		let result = isDateAfterNow(reserveDate);
		if(result) {			
			$("#reserve__dateBox").css("display", "block");
			$("#reserve__date").html(reserveDate);
			$("#reserve__hidden__date").val(reserveDate);
			
			$(".sendReserve__dropDown").toggleClass("hide show");
			$(".sendReserve__dropDown .dropDown__box").toggle();
		} else {
			alert("현재보다 이전의 시간은 선택할 수 없습니다.");
		}
		
		
	})
	
	// 받는 사람 입력할 때 자동완성
	$("#receive_id").on("keyup", function() {
		let inputId = $(this).val();
		
		if(inputId != "") {
			$.ajax({
				dataType: "json",
				url: "/mail/autoComplete",
				data: { keyword : inputId }
			}).done(function(resp) {
				console.log(resp);
				$("#autoComplete").empty();
				if(resp.length > 0) {
					for(let i = 0; i < resp.length; i++) {
						emailList = $("<div>");
						emailList.addClass("emailList__autoComplete")
						emailList.attr("data-email", resp[i].company_email);
						
						autoName = $("<div>");
						autoName.addClass("autoComplete__name");
						autoName.html(resp[i].name + "[" + resp[i].task_name + "]");
						
						autoEmail = $("<div>");
						autoEmail.addClass("autoComplete__email");
						autoEmail.html(resp[i].company_email);
						
						emailList.append(autoName).append(autoEmail);
						$("#autoComplete").append(emailList);
					}
				}
			})
		} else {
			$(".emailList__autoComplete").remove();
		}
	})
	
	// 자동완성 클릭했을 때
	$(document).on("click", ".emailList__autoComplete", function() {
		$("#receive_id").val("");
		$(".emailList__autoComplete").remove();
		
		let email = $(this).attr("data-email");
		$("#receive_id").val(email);
	})
	
	// 파일 리스트 삭제 버튼 눌렀을 때
	let deleteFileList = "";
	$(document).on("click", ".deleteFile__btn", function() {
		deleteFileList = deleteFileList.concat($(this).attr("sysName")+":");
		console.log(deleteFileList);
		
		$(this).parent().remove();
		$("#deleteFiles").val(deleteFileList);
	});
	
	$('#summernote').summernote({           // set editor height
		height: 500,  
		disableResizeEditor: true,          // set maximum height of editor
		focus: true,
		callbacks: {
			onImageUpload: function(files) {
				uploadImage(files);
			},
			onMediaDelete: function($target, editor, $editable) {        
            	deleteImage($target.attr("src"));
			}
		}
	});
	
	// 로그인한 사용자의 이메일을 보낸 사람으로 세팅
	$.ajax({
		url : "/mail/getUserEmail"
	}).done(function(resp) {
		$("#send_id").val(resp);
	})
});

// 선택한 날짜가 현재 날짜보다 이후인지
function isDateAfterNow(dateString) {
	let now = new Date();
	let inputDate = new Date(dateString);
	return inputDate > now;
}

// 필수 입력값들이 존재하는지
function validateForm() {	
	// 받는 사람을 입력하지 않았을 경우
	if($("#receive_id").val() == "") {
		alert("받는 사람은 필수 입력 항목입니다.");
		return false;
	}
	
	// 제목을 입력하지 않았을 경우
	if($("#title").val() == "") {
		alert("제목은 필수 입력 항목입니다.");
		return false;
	}
	
	// 받는 사람의 이메일이 존재하는지
	let email = $("#receive_id").val();
	let isValidEmail = false;
	$.ajax({
		url: "/mail/existEmail",
		data: { email : email },
		async: false,
	}).done(function(resp){
		// 존재하지 않는다면
		if(resp == false) {
			alert("존재하지 않는 이메일입니다. 이메일을 다시 확인해주세요.");
			$("#receive_id").focus();
		} else {
			isValidEmail = true;
		}
	})
	return isValidEmail;
}

// 이미지에 realpath 경로 부여하고 summernote에 출력
function uploadImage(files) {
	let formData = new FormData();
	
	for (let i = 0; i < files.length; i++) {
        formData.append("files", files[i]);
    }
	
	$.ajax({
		url: "/mail/uploadImage",
		type: "POST",
		data: formData,
		contentType: false,
    	processData: false,
    	success: function(data) {
			for (let i = 0; i < data.length; i++) {
				let img = $("<img>");
				img.css('width', '100%');
				img.attr("src", data[i]);
				$("#summernote").summernote("insertNode", img[0]);
			}
		}
	})
}

// 이미지 삭제
function deleteImage(imageSrc) {
	
	$.ajax({
		url: "/mail/deleteImage",
		type: "POST",
		data: { src : imageSrc }
	})
}