let status ="";

window.onload = function() {
	setClock();
	setInterval(setClock, 1000);


	$.ajax({
		url: "/humanResources/selectEmployeeWorkRule",
		dataType: "json"
	}).done(function(resp) {
		// 근무 계획 출력
		let today = new Date();
		// 출근 및 퇴근 시간 추출
		let attendTimeParts = resp.attend_time.split(':');
		let leaveTimeParts = resp.leave_time.split(':');
		console.log(resp.attend_time);
		console.log(resp.leave_time)

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
		console.log(leave);
		console.log(attend)

		$("#work_rule_name").html(resp.name);
		$("#work_rule_time").html(resp.attend_time + " ~ " + resp.leave_time + "(소정 " + (workTime-breakTime) + "시간)");
		
		// 근무 체크 확인
		$.ajax({
			url: "/humanResources/selectAttendStatus",
			dataType: "json"
		}).done(function(resp) {
			if(Object.keys(resp).length>0){
				// 출근 했을 경우
				
				let attendTimestamp = resp.attend_time;
				let time = new Date(attendTimestamp);
				let hour = time.getHours();
				let minutes = time.getMinutes();
				let sec = time.getSeconds();
				$("#attendTime").html(hour + " : " + minutes + " : " + sec);
				
				if(resp.leave_time==null){
					// 근무 중인경우
					console.log("근무중")
					window.status = "근무중"
					$(".statusBox").html(window.status);
					
					$("#leaveBtn").css({"color": "#75b47d", "pointer-events":"auto"});
					$("#attendBtn").css({"color": "#b5b5bb", "pointer-events":"none"});
					$(".workingStatusBtns div button").prop("disabled", false);
					if(resp.work_status_id == "지각"){
						createCommute_Attend(resp.attend_time, "지각");
					}else{
						createCommute_Attend(resp.attend_time, "출근");
					}
					
					
					
					// 퇴근하기
					$("#leaveBtn").on("click", function(){
						$.ajax({
							url: "/humanResources/updateLeavingWork",
							dataType: "json"
						}).done(function(){
							location.href="/humanResources";
						})
					})
				}else{
					// 퇴근한 경우
					console.log("퇴근함")
					window.status = "근무 종료"
					$(".statusBox").html(window.status);
					
					let leaveTimestamp = resp.leave_time;
					let time = new Date(leaveTimestamp);
					let hour = time.getHours();
					let minutes = time.getMinutes();
					let sec = time.getSeconds();
					$("#leaveTime").html(hour + " : " + minutes + " : " + sec);
					
					$("#leaveBtn").css({"color": "#b5b5bb", "pointer-events":"none"});
					$("#attendBtn").css({"color": "#b5b5bb", "pointer-events":"none"});
					$(".workingStatusBtns div button").prop("disabled", true);
					
					if(resp.work_status_id == "지각"){
						createCommute_Attend(resp.attend_time, "지각");
					}else{
						createCommute_Attend(resp.attend_time, "출근");
					}
					createCommute_Attend(resp.leave_time, "퇴근");
					
				}
				
				
			}else{
				console.log("출근 전")
				// 출근을 아직 안한 경우
				$("#leaveBtn").css({"color": "#b5b5bb", "pointer-events":"none"});
				$("#attendBtn").css({"color": "#75b47d", "pointer-events":"auto"});
				$(".workingStatusBtns div button").prop("disabled", true);
				window.status = "출근전";
				$(".statusBox").html(window.status);
				
				$("#attendBtn").on("click",function(){
					console.log("출근함")
					$.ajax({
						url: "/humanResources/insertAttendingWork",
						dataType: "json"
					}).done(function(){
						location.href="/humanResources";
					})
				})
			}
		})
		if (attend < today) {
		    console.log('attend 시간은 이미 지났습니다.');
		} else {
		    console.log('attend 시간은 아직 지나지 않았습니다.');
		}
	});
	
	if (!$("#working").prop("disabled")) {
	    $("#working").on("click", function() {
	        console.log("업무중 클릭")
	        $.ajax({
				url: "/humanResources/insertWorkCondition?status=업무",
				dataType: "json"
			}).done(function(){
				$("#working").prop("disabled", true);
				$("#goingOut").prop("disabled", false);
				$("#conference").prop("disabled", false);
				$("#outside").prop("disabled", false);
			})
	    });
	}
	if (!$("#goingOut").prop("disabled")) {
	    $("#goingOut").on("click", function() {
	        console.log("외출중 클릭")
	         $.ajax({
				url: "/humanResources/insertWorkCondition?status=외출",
				dataType: "json"
			}).done(function(){
				$("#working").prop("disabled", false);
				$("#goingOut").prop("disabled", true);
				$("#conference").prop("disabled", false);
				$("#outside").prop("disabled", false);
			})
	    });
	}
	if (!$("#conference").prop("disabled")) {
	    $("#conference").on("click", function() {
	        console.log("회의중 클릭")
	         $.ajax({
				url: "/humanResources/insertWorkCondition?status=회의",
				dataType: "json"
			}).done(function(){
				$("#working").prop("disabled", false);
				$("#goingOut").prop("disabled", false);
				$("#conference").prop("disabled", true);
				$("#outside").prop("disabled", false);
			})
	    });
	}
	if (!$("#outside").prop("disabled")) {
	    $("#outside").on("click", function() {
	        console.log("외근중 클릭")
	         $.ajax({
				url: "/humanResources/insertWorkCondition?status=외근",
				dataType: "json"
			}).done(function(){
				$("#working").prop("disabled", false);
				$("#goingOut").prop("disabled", false);
				$("#conference").prop("disabled", false);
				$("#outside").prop("disabled", true);
			})
	    });
	}
}

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
	
	let statusDiv = $("<div>").attr("class","statusBox").html(window.status);
	console.log(window.status)
	$(".nowTime").append(statusDiv);
}

function modifyNumber(time) {
	if (parseInt(time) < 10) {
		return "0" + time;
	} else {
		return time;
	}
}

function createCommute_Attend(attend_time, title){
	let attendTimestamp = attend_time;
	let time = new Date(attendTimestamp);
	let hour = time.getHours();
	let minutes = time.getMinutes();
	
	let commuteTable__row = $("<div>").attr("class", "commuteTable__row");
	let commute__left = $("<div>").attr("class", "commute__left");
	let commute__circle = $("<div>").attr("class", "commute__circle");
	let commute__time = $("<div>").attr("class", "commute__time").html(hour + " : " + minutes);
	let commute__right = $("<div>").attr("class", "commute__right");
	
	if(title=="지각"){
		commute__left.append(commute__circle).append(commute__time).append($("<div>").html("출근"))
	}else{
		commute__left.append(commute__circle).append(commute__time).append($("<div>").html(title))
	}			
	
	commute__right.append($("<div>").html(title));
	commuteTable__row.append(commute__left).append(commute__right);
	$(".commuteTable").append(commuteTable__row);
}