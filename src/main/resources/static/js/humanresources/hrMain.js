window.onload = function() {
	setClock();
	setInterval(setClock, 1000);

	$.ajax({
		url: "/humanResources/selectEmployeeWorkRule",
		dataType: "json"
	}).done(function(resp){
		console.log(resp);
	});
}

function setClock() {
	let dateInfo = new Date();
	let weekKR = ['월요일', '화요일', '수요일', '목요일', '금요일', '토요일', '일요일'];
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
}

function modifyNumber(time) {
	if (parseInt(time) < 10) {
		return "0" + time;
	} else {
		return time;
	}
}