let status = "";

$(document).ready(function() {
	setClock();
	setInterval(setClock, 1000);


	$(document).on("click", ".naviItem__itemCurcle", function() {
		if ($(this).siblings().html() == "메일") {
			location.href = "/mail";
		}

		if ($(this).siblings().html() == "일정") {
			location.href = "/schedule";
		}

		if ($(this).siblings().html() == "주소록") {
			location.href = "/addressbook";
		}

		if ($(this).siblings().html() == "인사") {
			location.href = "/humanResources";
		}

		if ($(this).siblings().html() == "전자결재") {
			location.href = "/electronicsignature";
		}

		if ($(this).siblings().html() == "오피스 관리") {
			location.href = "/admin/office";
		}

		if ($(this).siblings().html() == "회계지원") {
			location.href = "/admin/accounting";
		}
	})
	
	$("#total").on("click",function(){
		location.href="/electronicsignature/progressTotal";
	})
	$("#waiting").on("click",function(){
		location.href="/electronicsignature";
	})
	$("#expected").on("click",function(){
		location.href="/electronicsignature/progressCheck";
	})
	$("#progress").on("click",function(){
		location.href="/electronicsignature/progressExpected";
	})
	$("#receivedMail").on("click",function(){
		location.href="/mail";
	})
	$("#reservatioinMail").on("click",function(){
		location.href="/mail/outBox";
	})

	// 관리자 권한 가져오기
	$(document).ready(function() {
		$.ajax({
			url: "/members/isAdmin"
		}).done(function(resp) {
			for (let i = 0; i < resp.length; i++) {
				// 회계 권한
				if (resp[i] == "회계" || resp[i] == "총괄") {
					$("#accountingController").css("display", "block");
				}

				// 총괄 권한
				if (resp[i] == "총괄") {
					$("#officeController").css("display", "block");
				}
			}
		})
	})

	// 근무 체크 확인
	$.ajax({
		url: "/humanResources/selectAttendStatus",
		dataType: "json"
	}).done(function(resp) {
		// 출근 했을 경우
		if (Object.keys(resp).length > 0) {
			let attendTimestamp = resp.attend_time;
			let time = new Date(attendTimestamp);
			let hour = modifyNumber(time.getHours());
			let minutes = modifyNumber(time.getMinutes());
			let sec = modifyNumber(time.getSeconds());
			if (minutes == 0) {
				minutes = minutes.toString().padStart(2, '0');
			}
			$("#attendTime").html(hour + " : " + minutes + " : " + sec);

			// 근무 중인경우
			if (resp.leave_time == null) {
				window.status = "근무중"
				$(".timeline__status").html(window.status);

				$("#leaveBtn").css({ "pointer-events": "auto" });
				$("#leaveBtn>.work__text").css({ "color": "#75b47d" });
				$("#attendBtn").css({ "pointer-events": "none" });
				$("#attendBtn>.work__text").css({ "color": "#b5b5bb" });
				$("#workConditionBtns div button").prop("disabled", false);

				// 현재 근무상태 불러오기
				selectWorkConditionsList();

				$("#leaveBtn").on("click", function() {
					$.ajax({
						url: "/humanResources/updateLeavingWork",
						dataType: "json"
					}).done(function() {
						location.href = "/";
					})
				})
			} else { // 퇴근한 경우
				window.status = "근무 종료"
				$(".timeline__status").html(window.status);

				let leaveTimestamp = resp.leave_time;
				let time = new Date(leaveTimestamp);
				let hour = modifyNumber(time.getHours());
				let minutes = modifyNumber(time.getMinutes());
				let sec = modifyNumber(time.getSeconds());

				if (minutes == 0) {
					minutes = minutes.toString().padStart(2, '0');
				}
				$("#leaveTime").html(hour + " : " + minutes + " : " + sec);
				$("#attendBtn").css({ "pointer-events": "none" });
				$("#attendBtn>.work__text").css({ "color": "#b5b5bb" });
				$("#leaveBtn").css({ "pointer-events": "none" });
				$("#leaveBtn>.work__text").css({ "color": "#b5b5bb" });
				$("#workConditionBtns div button").prop("disabled", true);
			}
		} else { // 출근을 아직 안한경우
			$("#leaveBtn").css({ "color": "#b5b5bb", "pointer-events": "none" });
			$("#attendBtn").css({ "color": "#75b47d", "pointer-events": "auto" });
			$("#workConditionBtns div button").prop("disabled", true);
			window.status = "출근전";
			$(".timeline__status").html(window.status);

			$("#attendBtn").on("click", function() {
				$.ajax({
					url: "/humanResources/insertAttendingWork",
					dataType: "json"
				}).done(function() {
					location.href = "/";
				})
			})
		}
	});

	// 업무 버튼 눌렀을 때
	if (!$("#working").prop("disabled")) {
		$("#working").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=업무",
				dataType: "json"
			}).done(function(resp) {
				statusWorking();
			})
		});
	}
	// 외출 버튼 눌렀을 때
	if (!$("#goingOut").prop("disabled")) {
		$("#goingOut").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=외출",
				dataType: "json"
			}).done(function(resp) {
				statusGoingOut();
			})
		});
	}
	// 회의 버튼 눌렀을 때
	if (!$("#conference").prop("disabled")) {
		$("#conference").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=회의",
				dataType: "json"
			}).done(function(resp) {
				statusConference();
			})
		});
	}
	// 외근 버튼 눌렀을 때
	if (!$("#outside").prop("disabled")) {
		$("#outside").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=외근",
				dataType: "json"
			}).done(function(resp) {
				statusOutside();
			})
		});
	}
});

// 시계 움직이기
function setClock() {
	let dateInfo = new Date();
	let weekKR = ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'];
	let hour = modifyNumber(dateInfo.getHours());
	let min = modifyNumber(dateInfo.getMinutes());
	let sec = modifyNumber(dateInfo.getSeconds());
	let month = dateInfo.getMonth() + 1;
	let date = dateInfo.getDate();
	let week = dateInfo.getDay();

	$(".contentBox__date").html(month + "월 " + date + "일 (" + weekKR[week].charAt(0) + ")");
	$(".timeline__time").html(hour + " : " + min + " : " + sec);
	$(".timeline__status").html(window.status);
}

// 시계 시간 보정
function modifyNumber(time) {
	if (parseInt(time) < 10) {
		return "0" + time;
	} else {
		return time;
	}
}

function selectWorkConditionsList() {
	// 현재 근무 상태 불러오기
	$.ajax({
		url: "/humanResources/selectWorkConditionsList",
		dataType: "json"
	}).done(function(respList) {
		if (respList.length === 0) {
			window.status = "근무중";
			$(".timeline__status").html(window.status);
		} else {
			console.log(respList)
			if (respList[respList.length - 1].work_condition_status_id === "업무") {
				statusWorking();
			} else if (respList[respList.length - 1].work_condition_status_id === "외출") {
				statusGoingOut();
			} else if (respList[respList.length - 1].work_condition_status_id === "회의") {
				statusConference();
			} else if (respList[respList.length - 1].work_condition_status_id === "외근") {
				statusOutside();
			}
		}
	})
}

function statusWorking() {
	$("#working").prop("disabled", true);
	$("#goingOut").prop("disabled", false);
	$("#conference").prop("disabled", false);
	$("#outside").prop("disabled", false);

	window.status = "업무중";
	$(".timeline__status").html(window.status);
}

function statusGoingOut() {
	$("#working").prop("disabled", false);
	$("#goingOut").prop("disabled", true);
	$("#conference").prop("disabled", false);
	$("#outside").prop("disabled", false);

	window.status = "외출중";
	$(".timeline__status").html(window.status);
}

function statusConference() {
	$("#working").prop("disabled", false);
	$("#goingOut").prop("disabled", false);
	$("#conference").prop("disabled", true);
	$("#outside").prop("disabled", false);

	window.status = "회의중";
	$(".timeline__status").html(window.status);
}

function statusOutside() {
	$("#working").prop("disabled", false);
	$("#goingOut").prop("disabled", false);
	$("#conference").prop("disabled", false);
	$("#outside").prop("disabled", true);

	window.status = "외근중";
	$(".timeline__status").html(window.status);
}