let year; // 사용자가 선택한 년도
let type = "전체"; // 휴가 신청 내역 정보
let count = 1; // 문서 정렬번호
$(document).ready(function() {
	window.count = 1;
	// 탭 메뉴 움직이기
	/*$("ul.tabs li").click(function() {
		var tab_id = $(this).attr("data-tab");

		$("ul.tabs li").removeClass("current");
		$(".tab-content").removeClass("current");

		$(this).addClass("current");
		$("#" + tab_id).addClass("current");
	});*/

	// 인사 페이지를 통해 휴가 현황으로 넘어온 경우 인식
	// 현재 페이지의 URL
	/*let currentURL = window.location.href;
	// URL에 source 파라미터가 있는지 확인
	let urlParams = new URLSearchParams(window.location.search);
	let sourceParam = urlParams.get('source');

	// source 파라미터가 "detail_colortitle"인 경우에만 설정 적용
	// 해당 버튼으로 온 경우 휴가 내역페이지를 요청하는 경우임
	console.log(currentURL);
	console.log(urlParams);
	console.log(sourceParam);
	if (sourceParam === 'currentSituation') {
		$("ul.tabs li:first-child").removeClass("current");
		$(".tab-content").removeClass("current");

		$("ul.tabs li:nth-child(2)").addClass("current");
		$("#vacationDetails").addClass("current");
	}*/

	//---------------휴가 내역---------------
	$("#cpage").val("1");
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
		window.count = 1;
		annaulDetails();
	})

	$("#detailYear").on("click", function() {
		window.count = 1;
		annaulForYearDetails();
	})

	$("#detailMonth").on("click", function() {
		window.count = 1;
		annaulForMonthDetails();
	})

	$("#detailWeek").on("click", function() {
		window.count = 1;
		annaulForWeekDetails();
	})

	// 페이지 네이션 클릭시 리스트 변경
	$(document).on("click", "#detailPagination>div", function() {
		let pageUrl = $(this).attr("href");
		console.log(pageUrl)
		$.ajax({
			url: pageUrl,
			type: 'POST',
			data: { cpage: $(this).attr("cpage") }
		}).done(function(resp) {
			detailsPrint(resp, window.type);
		})
	})

	// 상세 누르면 해당 문서 출력하는 곳으로 이동
	$(document).on("click", ".showDocument", function() {
		location.href = "/electronicsignature/viewApprovalForm?document_id=" + $(this).attr("id");
	})
});



// 사용자의 휴가 신청 상세 내역 확인하기
function annaulDetails() {
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetails",
		type: "POST",
		data: { cpage: "1" }
	}).done(function(resp) {
		console.log(resp.detail)
		window.type = "전체";
		detailsPrint(resp, window.type);

	})
}

// 사용자의 최근 1주일치 신청 상세 내역 확인하기
function annaulForWeekDetails() {
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetailsForWeek",
		type: "POST",
		data: { cpage: "1" }
	}).done(function(resp) {
		console.log(resp)
		window.type = "1주일";
		detailsPrint(resp, window.type);
	})
}

// 사용자의 최근 1달치 신청 상세 내역 확인하기
function annaulForMonthDetails() {
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetailsForMonth",
		type: "POST",
		data: { cpage: "1" }
	}).done(function(resp) {
		console.log(resp)
		window.type = "한달";
		detailsPrint(resp, window.type);
	})
}

// 사용자의 최근 1년치 신청 상세 내역 확인하기
function annaulForYearDetails() {
	$.ajax({
		url: "/humanResources/selectAnnaulAppDetailsForYear",
		type: "POST",
		data: { cpage: "1" }
	}).done(function(resp) {
		console.log(resp)
		window.type = "1년";
		detailsPrint(resp, window.type);
	})
}


// 신청 상세내역 출력하는 함수
function detailsPrint(resp, type) {
	$(".detailes__body").empty();
	console.log(window.count);
	console.log(resp.detail.length)
	for (let i = 0; i < resp.detail.length; i++) {
		let bodyLine = $("<div>").attr("class", "body__line");
		let num = $("<div>").html(window.count++).addClass("body__td");
		let writer = $("<div>").html(resp.detail[i].name).addClass("body__td");
		let type = $("<div>").html(resp.detail[i].rest_reason_type).addClass("body__td");

		// 일수 계산
		let start = resp.detail[i].start_date;
		let end = resp.detail[i].end_date;
		let startDate = new Date(start);
		let endDate = new Date(end);

		let timeDiff = Math.abs(endDate.getTime() - startDate.getTime());
		let dayDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));

		let days = $("<div>").html(dayDiff + 1 + "일").addClass("body__td");
		let period = $("<div>").addClass("body__td");
		if (resp.detail[i].start_date === resp.detail[i].end_date) {
			period.html(resp.detail[i].start_date.split(" ")[0]);
		} else {
			period.html(resp.detail[i].start_date.split(" ")[0] + " ~ " + resp.detail[i].end_date.split(" ")[0]);
		}
		let status = $("<div>").html(resp.detail[i].status).addClass("body__td");
		let detail = $("<div>").html("상세").attr("id", resp.detail[i].id).addClass("body__td").addClass("showDocument");
		bodyLine.append(num).append(writer).append(type).append(days).append(period).append(status).append(detail);
		$(".detailes__body").append(bodyLine);
	}
	console.log(resp)
	console.log("total" + resp.recordTotalCount)
	pagination(resp.recordTotalCount, resp.recordCountPerPage, resp.naviCountPerPage, resp.lastPageNum, type);
}

// 페이지 네이션 구성
function pagination(recordTotalCount, recordCountPerPage, naviCountPerPage, lastPageNum, type) {
	$("#detailPagination").empty();
	let pageUrl = "";
	if (type === "전체") {
		pageUrl = "/humanResources/selectAnnaulAppDetails";
	} else if (type === "1년") {
		pageUrl = "/humanResources/selectAnnaulAppDetailsForYear";
	} else if (type === "한달") {
		pageUrl = "/humanResources/selectAnnaulAppDetailsForMonth";
	} else if (type === "1주일") {
		pageUrl = "/humanResources/selectAnnaulAppDetailsForWeek";
	}

	if (recordTotalCount != 0) {

		let pageTotalCount = 0;
		pageTotalCount = Math.ceil(recordTotalCount / recordCountPerPage);

		let currentPage = lastPageNum;

		// 비정상 접근 차단
		if (currentPage < 1) {
			currentPage = 1;
		} else if (currentPage > pageTotalCount) {
			currentPage = pageTotalCount;
		}

		let startNavi = Math.floor((currentPage - 1) / naviCountPerPage) * naviCountPerPage + 1;
		let endNavi = startNavi + (naviCountPerPage - 1);
		if (endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		console.log("start" + startNavi);
		console.log("end" + endNavi)

		let needPrev = true;
		let needNext = true;

		if (startNavi == 1) {
			needPrev = false;
		}

		if (endNavi == pageTotalCount) {
			needNext = false;
		}

		let pagination = $("#detailPagination");
		if (startNavi != 1) {
			let divTag = $("<div>");
			divTag.attr("href", pageUrl).attr("cpage", "1");
			let iTag = $("<i>");
			iTag.addClass("fa-solid fa-angles-left");
			divTag.append(iTag);
			pagination.append(divTag);
		}

		console.log("startNavi: " + startNavi);
		console.log("needPrev: " + needPrev);
		console.log("endNavi:" + endNavi);
		console.log("pageTotalCount: " + pageTotalCount);

		if (needPrev) {
			let divTag = $("<div>");
			divTag.attr("href", pageUrl).attr("cpage", (startNavi - 1));
			let iTag = $("<i>");
			iTag.addClass("fa-solid fa-chevron-left");
			divTag.append(iTag);
			pagination.append($(divTag));
		}

		for (let i = startNavi; i <= endNavi; i++) {
			let divTag = $("<div>");
			divTag.text(i);
			divTag.attr("href", pageUrl).attr("cpage", i);
			if (i == currentPage) {
				divTag.addClass("pageNavi__circle");
			}
			pagination.append(divTag);
		}

		if (needNext) {
			let divTag = $("<div>");
			divTag.attr("href", pageUrl).attr("cpage", (endNavi + 1));
			let iTag = $("<i>");
			iTag.addClass("fa-solid fa-chevron-right");
			divTag.append(iTag);
			pagination.append(divTag);
		}

		if (endNavi != pageTotalCount) {
			let divTag = $("<div>");
			divTag.attr("href", pageUrl).attr("cpage", (endNavi + 1));
			let iTag = $("<i>");
			iTag.addClass("fa-solid fa-angles-right");
			divTag.append(iTag);
			pagination.append(divTag);
		}
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
		let usedCount = $("#usedCountHidden").val();//실 사용개수
		let restCount = $("#usedCountHidden").val();//남은 제거 개수
		let totaluse = false;
		console.log("use" + usedCount);
		if (resp.length > 0) {
			for (let i = 0; i < resp.length; i++) {
				let printCount = 0;
				let tableLine = $("<div>").attr("class", "table__line");
				let bodyConf_date = $("<div>").attr("class", "body__conf").html(resp[i].reg_date.split(" ")[0]);
				let bodyConf_line = $("<div>").attr("class", "body__conf");
				let lineDiv_left = $("<div>").html(resp[i].rest_cnt);
				if (resp[i].rest_cnt < usedCount) {
					printCount =0;
					restCount = usedCount - resp[i].rest_cnt;
				} else {
					if(!totaluse){
						printCount = resp[i].rest_cnt - restCount;
						totaluse=true;
					}else{
						printCount = resp[i].rest_cnt;
					}
					
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
