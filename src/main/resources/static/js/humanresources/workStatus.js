let year;
$(document).ready(function() {
	// 탭 메뉴 움직이기
	$("ul.tabs li").click(function() {
		var tab_id = $(this).attr("data-tab");

		$("ul.tabs li").removeClass("current");
		$(".tab-content").removeClass("current");

		$(this).addClass("current");
		$("#" + tab_id).addClass("current");
	});
	//---------------휴가 내역---------------
	//날짜 설정
	let date = new Date();
	window.year = date.getFullYear();
	$(".yearInfo").html(window.year + "-01-01 ~ " + window.year + "-12-31");
	//$("#insertDate").html(window.year + "-01-01");
	// 휴가내역 페이지 화살표 이동시 날짜 변경
	$(".arrow").on("click", function() {
		if ($(this).attr("id") === "leftArrow") {
			window.year--;
		} else {
			window.year++;
		}
		$(".yearInfo").html(window.year + "-01-01 ~ " + window.year + "-12-31");
		//$("#insertDate").html(window.year + "-01-01");

		// 해당 연도 총 휴가일 불러오기
		yearTotalAnnaul();
	});

	// 휴가 신청 이동 버튼
	$("#vacationAppSmallBtn").on("click", function() {
		location.href = "/humanResources/showVacationApp";
	})

	// 해당 연도 총 휴가일 불러오기
	yearTotalAnnaul();
	// 사용자의 휴가 신청 상세 내역 확인하기
	annaulDetails();

	$("#detailAll").on("click", function() {
		annaulDetails();
	})
	
	$("#detailYear").on("click",function(){
		annaulForYearDetails();
	})
	
	$("#detailMonth").on("click",function(){
		annaulForMonthDetails();
	})
	
	$("#detailWeek").on("click",function(){
		annaulForWeekDetails();
	})
});

// 사용자의 휴가 신청 상세 내역 확인하기
function annaulDetails() {
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetails",
		type: "POST",
	}).done(function(resp) {
		detailsPrint(resp);
	})
}

// 사용자의 최근 1주일치 신청 상세 내역 확인하기
function annaulForWeekDetails(){
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetailsForWeek",
		type: "POST",
	}).done(function(resp) {
		detailsPrint(resp);
	})
}

// 사용자의 최근 1달치 신청 상세 내역 확인하기
function annaulForMonthDetails(){
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetailsForMonth",
		type: "POST",
	}).done(function(resp) {
		detailsPrint(resp);
	})
}

// 사용자의 최근 1년치 신청 상세 내역 확인하기
function annaulForYearDetails(){
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetailsForYear",
		type: "POST",
	}).done(function(resp) {
		detailsPrint(resp);
	})
}

// 신청 상세내역 출력하는 함수
function detailsPrint(resp) {
	$(".detailes__body").empty();
	for (let i = 0; i < resp.length; i++) {
		let bodyLine = $("<div>").attr("class", "body__line");
		let num = $("<div>").html(i + 1).addClass("body__td");
		let writer = $("<div>").html(resp[i].name).addClass("body__td");
		let type = $("<div>").html(resp[i].rest_reason_type).addClass("body__td");

		// 일수 계산
		let start = resp[i].start_date;
		let end = resp[i].end_date;
		let startDate = new Date(start);
		let endDate = new Date(end);

		let timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
		let dayDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

		let days = $("<div>").html(dayDiff + 1 + "일").addClass("body__td");
		let period = $("<div>").addClass("body__td");
		if (resp[i].start_date === resp[i].end_date) {
			period.html(resp[i].start_date.split(" ")[0]);
		} else {
			period.html(resp[i].start_date.split(" ")[0] + " ~ " + resp[i].end_date.split(" ")[0]);
		}
		let status = $("<div>").html(resp[i].status).addClass("body__td");
		let detail = $("<div>").html("상세").attr("id", resp[i].id).addClass("body__td");
		bodyLine.append(num).append(writer).append(type).append(days).append(period).append(status).append(detail);
		$(".detailes__body").append(bodyLine);
	}
}

// 해당 년도 총 휴가일 불러오기
function yearTotalAnnaul() {
	$.ajax({
		url: "/humanResources/selectYearTotalAnnaul",
		data: { year: window.year },
		dataType: "json",
		type: "POST",
	}).done(function(resp) {
		console.log(resp)
		console.log(resp == undefined)
		console.log(resp === {})
		if (Object.keys(resp).length !== 0) {
			$("#totalAnnaul").html("총 휴가 : " + resp.total_rest_cnt);
			$("#totalCountHidden").val(resp.total_rest_cnt);
			console.log($("#totalCountHidden").val())
		} else {
			$("#totalAnnaul").html("총 휴가 : 0");
			$("#totalCountHidden").val(0);
		}

		// 해당 년도 휴가 사용 정보 불러오기
		usedAnnualInfo();
		// 해당 년도 휴가 생성 상세 정보 불러오기
		yearDetailAnnual();
	})
}

// 해당 년도 휴가 생성 상세 정보 불러오기
function yearDetailAnnual() {
	$.ajax({
		url: "/humanResources/selectYearDetailAnnaul",
		data: { year: window.year },
		type: "POST",
	}).done(function(resp) {
		console.log(resp)
		$(".table__body").empty();
		let usedCount = $("#usedCountHidden").val();
		let restCount = $("#usedCountHidden").val();
		console.log("use" + usedCount);
		if (resp.length > 0) {
			for (let i = 0; i < resp.length; i++) {
				let printCount = 0;
				let tableLine = $("<div>").attr("class", "table__line");
				let bodyConf_date = $("<div>").attr("class", "body__conf").html(resp[i].reg_date.split(" ")[0]);
				let bodyConf_line = $("<div>").attr("class", "body__conf");
				let lineDiv_left = $("<div>").html(resp[i].rest_cnt);
				if (resp[i].rest_cnt < restCount) {
					restCount = usedCount - resp[i].rest_cnt;
				} else {
					printCount = resp[i].rest_cnt - restCount;
				}
				console.log("use" + usedCount);
				console.log("rest" + restCount)
				console.log("print" + printCount)
				let lineDiv_right = $("<div>").html(printCount);
				let bodyConf_type = $("<div>").attr("class", "body__conf").html((resp[i].rest_type_id === "연차") ? "정기휴가" : resp[i].rest_type_id);
				let bodyConf_etc = $("<div>").attr("class", "body__conf").attr("id", "detailNote").html(resp[i].rest_type_id + " (" + resp[i].rest_cnt + "일 x 8시간 = " + resp[i].rest_cnt * 8 + "시간)");

				bodyConf_line.append(lineDiv_left).append(lineDiv_right);
				tableLine.append(bodyConf_date).append(bodyConf_line).append(bodyConf_type).append(bodyConf_etc);
				$(".table__body").append(tableLine);
			}
		} else {
			let emptyDiv = $("<div>").attr("class", "emptyInfo").html("해당 데이터가 없습니다.");
			$(".table__body").append(emptyDiv);
		}
	});
}

// 해당 년도 휴가 사용 정보 불러오기
function usedAnnualInfo() {
	$.ajax({
		url: "/humanResources/selectUsedAnnaul",
		data: { year: window.year },
		type: "POST",
	}).done(function(resp) {
		console.log(resp)
		$("#usedAnnaul").html("사용 : " + resp);
		console.log($("#totalCountHidden").val());
		console.log($("#totalCountHidden").val() - resp)
		if ($("#totalCountHidden").val() !== 0) {
			$("#remainingCnt").html("잔여 : " + ($("#totalCountHidden").val() - resp));
		} else {
			$("#remainingCnt").html("잔여 : 0");
		}
		$("#usedCountHidden").val(resp);
	})
}
