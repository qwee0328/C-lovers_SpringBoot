let status = "";

window.onload = function() {
	setClock();
	setInterval(setClock, 1000);

	// 현재 근무상태 불러오기
	//selectWorkConditionsList();

	// 사용자 지각 현황 불러오기
	$.ajax({
		url: "/humanResources/selectLateInfo",
		dataType: "json"
	}).done(function(resp) {
		$("#userLateCount").html(resp + "회");
	});

	// 사용자 조기퇴근 정보 불러오기
	$.ajax({
		url: "/humanResources/selectEarlyLeaveInfo",
		dataType: "json"
	}).done(function(resp) {
		$("#userEarlyLeaceCount").html(resp + "회");
	});

	// 사용자 퇴근 미체크 정보 불러오기
	$.ajax({
		url: "/humanResources/selectNotCheckedLeaveInfo",
		dataType: "json"
	}).done(function(resp) {
		$("#userNotCheckedLeaveCount").html(resp + "회");
	});

	// 사용자 결근 정보 불러오기
	$.ajax({
		url: "/humanResources/selectAbsenteeismInfo",
		dataType: "json"
	}).done(function(resp) {
		$("#userAbsenteeismCount").html(resp + "회");
	});

	// 사용자 근무 시간 정보 불러오기
	$.ajax({
		url: "/humanResources/selectWorkingDaysThisMonth",
		dataType: "json"
	}).done(function(resp) {
		console.log(resp)
		$("#workingDyas").html(resp.length + "일");
		// 현재 날짜를 얻기
		let currentDate = new Date();
		let currentDateString = currentDate.toISOString().split('T')[0]; // 날짜만 추출
		for (let i = 0; i < resp.length; i++) {
			// 오늘 날짜의 퇴근 시간이 있다면
			if (resp[i].work_date.split(' ')[0] === currentDateString && resp[i].leave_time) {
				$("#workingDyas").html(resp.length + "일");
			} else {
				$("#workingDyas").html(resp.length - 1 + "일");
			}
		}
		calculateAndAddTimeDifference(resp);
		console.log(resp)
		let totalWorkingTime = 0;
		for (let i = 0; i < resp.length; i++) {
			if (resp[i].timeDifference) {
				totalWorkingTime = totalWorkingTime + resp[i].timeDifference
			}
		}
		console.log(totalWorkingTime);
		let hour = Math.floor(totalWorkingTime / (1000 * 60 * 60));
		let min = Math.floor(totalWorkingTime % (1000 * 60 * 60) / (1000 * 60));

		$("#totalWorkingTime").html(hour + "시간 " + min + "분");

		let average = totalWorkingTime / resp.length;
		hour = Math.floor(average / (1000 * 60 * 60));
		min = Math.floor(average % (1000 * 60 * 60) / (1000 * 60));
		$("#calibratedAverage").html(hour + "시간 " + min + "분");
	})

	$.ajax({
		url: "/humanResources/selectEmployeeWorkRule",
		dataType: "json"
	}).done(function(resp) {
		// 근무 계획 출력
		let today = new Date();
		// 출근 및 퇴근 시간 추출
		let attendTimeParts = resp.attend_time.split(':');
		let leaveTimeParts = resp.leave_time.split(':');

		let leave = new Date(today.getFullYear(), today.getMonth(), today.getDate(), parseInt(leaveTimeParts[0]), parseInt(leaveTimeParts[1]), parseInt(leaveTimeParts[2]));
		let attend = new Date(today.getFullYear(), today.getMonth(), today.getDate(), parseInt(attendTimeParts[0]), parseInt(attendTimeParts[1]), parseInt(attendTimeParts[2]));
		let differenceInMillisec = Math.abs(leave - attend);
		let workTime = differenceInMillisec / (1000 * 60 * 60);
		let breakTime = 0;
		if (workTime >= 12) {
			breakTime = 1.5;
		} else if (workTime >= 8) {
			breakTime = 1;
		} else {
			breakTime = 0.5;
		}

		$("#work_rule_name").html(resp.name);
		$("#work_rule_time").html(resp.attend_time + " ~ " + resp.leave_time + "(소정 " + (workTime - breakTime) + "시간)");

		// 근무 체크 확인
		$.ajax({
			url: "/humanResources/selectAttendStatus",
			dataType: "json"
		}).done(function(resp) {
			if (Object.keys(resp).length > 0) {
				// 출근 했을 경우
				let attendTimestamp = resp.attend_time;
				let time = new Date(attendTimestamp);
				let hour = modifyNumber(time.getHours());
				let minutes = modifyNumber(time.getMinutes());
				let sec = modifyNumber(time.getSeconds());
				if (minutes == 0) {
					minutes = minutes.toString().padStart(2, '0');
				}
				$("#attendTime").html(hour + " : " + minutes + " : " + sec);

				if (resp.leave_time == null) {
					// 근무 중인경우
					window.status = "근무중"
					$(".statusBox").html(window.status);

					$("#leaveBtn").css({ "color": "#75b47d", "pointer-events": "auto" });
					$("#attendBtn").css({ "color": "#b5b5bb", "pointer-events": "none" });
					$(".workingStatusBtns div button").prop("disabled", false);
					if (resp.work_status_id == "지각") {
						createCommute_Attend(resp.attend_time, "지각");
					} else {
						createCommute_Attend(resp.attend_time, "출근");
					}

					// 현재 근무상태 불러오기
					selectWorkConditionsList();

					$.ajax({
						url: "/humanResources/selectWorkConditionsList",
						dataType: "json"
					}).done(function(respList) {
						for (let i = 0; i < respList.length; i++) {
							updateWorkCondition(respList[i]);
						}
					})

					// 퇴근하기
					$("#leaveBtn").on("click", function() {
						$.ajax({
							url: "/humanResources/updateLeavingWork",
							dataType: "json"
						}).done(function() {
							location.href = "/humanResources";
						})
					})
				} else {
					// 퇴근한 경우
					window.status = "근무 종료"
					$(".statusBox").html(window.status);

					let leaveTimestamp = resp.leave_time;
					let time = new Date(leaveTimestamp);
					let hour = modifyNumber(time.getHours());
					let minutes = modifyNumber(time.getMinutes());
					let sec = modifyNumber(time.getSeconds());

					if (minutes == 0) {
						minutes = minutes.toString().padStart(2, '0');
					}
					$("#leaveTime").html(hour + " : " + minutes + " : " + sec);

					$("#leaveBtn").css({ "color": "#b5b5bb", "pointer-events": "none" });
					$("#attendBtn").css({ "color": "#b5b5bb", "pointer-events": "none" });
					$(".workingStatusBtns div button").prop("disabled", true);

					if (resp.work_status_id == "지각") {
						createCommute_Attend(resp.attend_time, "지각");
					} else {
						createCommute_Attend(resp.attend_time, "출근");
					}

					$.ajax({
						url: "/humanResources/selectWorkConditionsList",
						dataType: "json"
					}).done(function(respList) {
						for (let i = 0; i < respList.length; i++) {
							updateWorkCondition(respList[i]);
						}
						createCommute_Attend(resp.leave_time, "퇴근");
					})
				}

			} else {
				// 출근을 아직 안한 경우
				$("#leaveBtn").css({ "color": "#b5b5bb", "pointer-events": "none" });
				$("#attendBtn").css({ "color": "#75b47d", "pointer-events": "auto" });
				$(".workingStatusBtns div button").prop("disabled", true);
				window.status = "출근전";
				$(".statusBox").html(window.status);

				$("#attendBtn").on("click", function() {
					$.ajax({
						url: "/humanResources/insertAttendingWork",
						dataType: "json"
					}).done(function() {
						location.href = "/humanResources";
					})
				})
			}
		})
	});

	if (!$("#working").prop("disabled")) {
		$("#working").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=업무",
				dataType: "json"
			}).done(function(resp) {
				statusWorking();

				updateWorkCondition(resp);
			})
		});
	}
	if (!$("#goingOut").prop("disabled")) {
		$("#goingOut").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=외출",
				dataType: "json"
			}).done(function(resp) {
				statusGoingOut();

				updateWorkCondition(resp);
			})
		});
	}
	if (!$("#conference").prop("disabled")) {
		$("#conference").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=회의",
				dataType: "json"
			}).done(function(resp) {
				statusConference();

				updateWorkCondition(resp);
			})
		});
	}
	if (!$("#outside").prop("disabled")) {
		$("#outside").on("click", function() {
			$.ajax({
				url: "/humanResources/insertWorkCondition?status=외근",
				dataType: "json"
			}).done(function(resp) {
				statusOutside();

				updateWorkCondition(resp);
			})
		});
	}
}
// 근무 시간 계산을 위한 시간 차이를 계산해 더하는 함수
function calculateAndAddTimeDifference(dateList) {
	for (let i = 0; i < dateList.length; i++) {
		let item = dateList[i];

		// 출퇴근 시간이 모두 존재할때만 계산 수행
		if (item.leave_time && item.attend_time) {
			let leaveTime = new Date(item.leave_time);
			let attendTime = new Date(item.attend_time);

			// 두 시간 차이 계산
			let timeDifference = leaveTime.getTime() - attendTime.getTime();

			// 시간차를 dateList에 추가
			dateList[i].timeDifference = timeDifference;
		}
	}
}



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

	$(".month").html(month + "월");
	let dayDiv = $("<div>").attr("class", "day");
	let dateDiv = $("<div>").attr("class", "day__num").html(date);
	let weekDiv = $("<div>").attr("class", "day__week").html(weekKR[week]);
	dayDiv.append(dateDiv).append(weekDiv);
	$(".month").append(dayDiv);
	$(".nowTime").html(hour + " : " + min + " : " + sec);

	let statusDiv = $("<div>").attr("class", "statusBox").html(window.status);
	$(".nowTime").append(statusDiv);
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
			$(".statusBox").html(window.status);
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

// 근무 현황 출력
function createCommute_Attend(attend_time, title) {
	let attendTimestamp = attend_time;
	let time = new Date(attendTimestamp);
	let hour = time.getHours();
	let minutes = time.getMinutes();
	if (minutes == 0) {
		minutes = minutes.toString().padStart(2, '0');
	}

	let commuteTable__row = $("<div>").attr("class", "commuteTable__row");
	let commute__left = $("<div>").attr("class", "commute__left");
	let commute__circle = $("<div>").attr("class", "commute__circle");
	let commute__time = $("<div>").attr("class", "commute__time").html(hour + " : " + minutes);
	let commute__right = $("<div>").attr("class", "commute__right");

	if (title == "지각") {
		commute__left.append(commute__circle).append(commute__time).append($("<div>").html("출근"))
	} else {
		commute__left.append(commute__circle).append(commute__time).append($("<div>").html(title))
	}

	commute__right.append($("<div>").html(title));
	commuteTable__row.append(commute__left).append(commute__right);
	$(".commuteTable").append(commuteTable__row);
}

// 근무 현황 업데이트
function updateWorkCondition(resp) {
	let startTimestamp = resp.start_time;
	let time = new Date(startTimestamp);
	let hour = time.getHours();
	let minutes = time.getMinutes();
	if (minutes == 0) {
		minutes = minutes.toString().padStart(2, '0');
	}

	let commuteTable__row = $("<div>").attr("class", "commuteTable__row");
	let commute__left = $("<div>").attr("class", "commute__left");
	let commute__circle = $("<div>").attr("class", "commute__circle");
	let commute__time = $("<div>").attr("class", "commute__time").html(hour + " : " + minutes);

	commute__left.append(commute__circle).append(commute__time).append($("<div>").html(resp.work_condition_status_id));
	commuteTable__row.append(commute__left);
	$(".commuteTable").append(commuteTable__row);
}

function statusWorking() {
	$("#working").prop("disabled", true);
	$("#goingOut").prop("disabled", false);
	$("#conference").prop("disabled", false);
	$("#outside").prop("disabled", false);

	window.status = "업무중";
	$(".statusBox").html(window.status);
}

function statusGoingOut() {
	$("#working").prop("disabled", false);
	$("#goingOut").prop("disabled", true);
	$("#conference").prop("disabled", false);
	$("#outside").prop("disabled", false);

	window.status = "외출중";
	$(".statusBox").html(window.status);
}

function statusConference() {
	$("#working").prop("disabled", false);
	$("#goingOut").prop("disabled", false);
	$("#conference").prop("disabled", true);
	$("#outside").prop("disabled", false);

	window.status = "회의중";
	$(".statusBox").html(window.status);
}

function statusOutside() {
	$("#working").prop("disabled", false);
	$("#goingOut").prop("disabled", false);
	$("#conference").prop("disabled", false);
	$("#outside").prop("disabled", true);

	window.status = "외근중";
	$(".statusBox").html(window.status);
}