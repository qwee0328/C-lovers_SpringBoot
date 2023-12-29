document.addEventListener("DOMContentLoaded", function() {
	let rest_reason_type = [];
	let dateTypePairs = {};  // 추가: 날짜와 타입을 쌍으로 백업할 배열
	$.ajax({
		url: "/humanResources/selectRestReasonType",
		dataType: "json",
		type: "POST",
	}).done(function(resp) {
		for (let i = 0; i < resp.length; i++) {
			rest_reason_type.push(resp[i]);
		}
	})

	// user 이름 불러오기
	$.ajax({
		url: "/office/selectOfficeName"
	}).done(function(data) {
		// user 이름 불러오기
		$.ajax({
			url: "/humanResources/headerProfile",
			type: "POST",
		}).done(function(resp) {
			$("#documentWriter").html(data +" " + resp.name);
		});
	})


	// input의 value가 변경될 때 선택한 날짜가 옆에 나오도록 설정
	$("#date_selector").on("change", function() {
		if ($("#date_selector").val() !== "") {
			$(".appStatus").html("");
			let dateArr = [];
			dateArr = $("#date_selector").val().split(", ");

			for (let i = 0; i < dateArr.length; i++) {
				let dayOfWeek = new Date(dateArr[i]).toLocaleString("ko", {
					weekday: "short",
				});

				let vacationType = $("<input>").attr("type", "hidden").attr("class", "vacationType").val("연차");
				let appStatus__line = $("<div>").attr("class", "appStatus__line");
				let vacationDate = $("<div>")
					.attr("class", "vacationDate")
					.html(dateArr[i] + "(" + dayOfWeek + ")");

				let selectorType = $("<div>").attr("class", "selectorType");
				let typeName = $("<div>").attr("class", "typeName").html("연차");

				// 이전에 선택한 타입이 있다면 해당 타입으로 설정
				let savedType = findTypeForDate(dateArr[i]);
				if (savedType) {
					typeName.html(savedType);
					vacationType.val(savedType);
				}

				let selectorArrow = $("<div>")
					.attr("class", "selectorArrow")
					.html($("<i>").attr("class", "fa-solid fa-chevron-down"));
				selectorType.append(typeName).append(selectorArrow);

				let selector__option = $("<div>").attr("class", "selector__option");
				for (let i = 0; i < rest_reason_type.length; i++) {
					let option__item = $("<div>").attr("class", "option__item").html(rest_reason_type[i]);
					selector__option.append(option__item)
				}
				appStatus__line.append(vacationType)
					.append(vacationDate)
					.append(selectorType)
					.append(selector__option);
				$(".appStatus").append(appStatus__line);

				// 선택한 날짜와 타입을 기억
				dateTypePairs[dateArr[i]] = vacationType.val();
			}
		} else {
			$(".appStatus").html("");
			dateTypePairs = {};
		}

	});

	// 선택한 날짜에 대한 이전에 선택한 타입을 찾는 함수
	function findTypeForDate(date) {
		return dateTypePairs[date];
	}

	// 선택한 타입을 dateTypePairs에 업데이트하는 함수
	function updateTypeForDate(date, type) {
		dateTypePairs[date] = type;
	}

	// selector 커스텀 해서 만들기
	let showSelector = false;
	$(document).on("click", ".selectorType", function() {
		if (!showSelector) {
			$(this)
				.parent()
				.find(".selectorArrow")
				.children()
				.attr("class", "fa-solid fa-chevron-up");
			$(this).parent().find(".selector__option").css("display", "block");

			// 선택한 타입 업데이트
			$(".option__item").on("click", function() {
				// 선택한 날짜에 대한 타입을 업데이트
				updateTypeForDate($(this).closest(".appStatus__line").find(".vacationDate").text().split("(")[0].trim(), $(this).html());

				$(this).parent().parent().find(".typeName").html($(this).html());
				$(this).parent().parent().find(".vacationType").val($(this).html());
			});
			showSelector = true;
		} else {
			$(this).parent().find(".selector__option").css("display", "none");
			$(this)
				.parent()
				.find(".selectorArrow")
				.children()
				.attr("class", "fa-solid fa-chevron-down");
			showSelector = false;
		}
	});

	// selector외부를 클릭하면 selector가 닫히도록 설정
	$(document).on("click", function(event) {
		let clickElement = $(event.target);
		if (clickElement.closest(`.selectorType`).length > 0) {
			// 모든 .selector__option 숨기기
			$(".selector__option").css("display", "none");
			$(".selector__option")
				.parent()
				.find(".selectorArrow")
				.children()
				.attr("class", "fa-solid fa-chevron-down");
			// 클릭된 .selectorType에 속한 .selector__option만 보이게 설정
			if (showSelector) {
				clickElement
					.closest(`.selectorType`)
					.parent()
					.find(".selector__option")
					.css("display", "block");
				clickElement
					.closest(`.selectorType`)
					.parent()
					.find(".selectorArrow")
					.children()
					.attr("class", "fa-solid fa-chevron-up");
				showSelector = true;
			}
		} else {
			$(".selector__option").css("display", "none");
			$(".selector__option")
				.parent()
				.find(".selectorArrow")
				.children()
				.attr("class", "fa-solid fa-chevron-down");
			showSelector = false;
		}
	});

	// 잔여 휴가 정보 불러오기
	let date = new Date();
	let year = date.getFullYear();
	let remainingAnnaul;
	$.ajax({
		url: "/humanResources/selectYearTotalAnnaul",
		data: { year: year },
		type: "POST",
	}).done(function(resp) {
		if (Object.keys(resp).length !== 0) {
			$(".vacationInfo__main>span").html(resp.total_rest_cnt + "일");
			remainingAnnaul = resp.total_rest_cnt;
		} else {
			$("#totalAnnaul").html("0일");
		}

	})

	$("#vacationdraftingBtn").on("click", function() {
		if ($("#processEmployeeIDList").val() === "") {
			alert("결제선을 설정해주세요.");
			return;
		}
		if ($("#date_selector").val() === "") {
			alert("휴가 날짜를 선택해주세요.");
			return;
		}
		if ($("#vacationReason").val() === "") {
			alert("휴가 사유를 입력해주세요.");
			$("#vacationReason").focus();
			return;
		}
		if ($("#processEmployeeIDList").val() !== "" && $("#date_selector").val() !== "" && $("#vacationReason").val() !== "") {

			// 사용 가능한 휴가가 남아있으면
			if (remainingAnnaul > 0) {
				let processEmployeeIDList = $("#processEmployeeIDList").val();
				let processEmployeeIDArray = processEmployeeIDList.split(",");

				let vacationTypeList = [];
				$(".vacationType").each(function() {
					vacationTypeList.push($(this).val())
				});

				// 선택 가능한 날짜를 넘어가지 않으면
				if (vacationTypeList.length <= remainingAnnaul) {
					$.ajax({
						url: "/electronicsignature/insertVacation",
						dataType: "json",
						type: "POST",
						data: { processEmployeeIDArray: processEmployeeIDArray, vacationDateList: $("#date_selector").val().split(", "), vacationTypeList: vacationTypeList, reson: $("#vacationReason").val() },
					}).done(function() {
						location.href = "/humanResources";
					})
				} else {
					alert("남아있는 휴가보다 많이 신청할 수는 없습니다.");
					return;
				}

			} else {
				alert("사용 가능한 휴가가 남아있지 않습니다.");
				location.href = "/humanResources";
			}


		}
	})

});

// 모달창 오픈
$(document).ready(function() {
	$("#approvalLineBtn").on("click", function() {
		$(".approvalLineModal").modal({
			fadeDuration: 300,
			showClose: false
		});
		$("#approvalLineModal__cancle").on("click", $.modal.close);
	})

});