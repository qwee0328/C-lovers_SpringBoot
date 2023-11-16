$(document).ready(function() {
	//console.log(typeof $.flatpickr);
	// 날짜 선택용 달력
	/*$("#date_selector").flatpickr({
		altInput: false,
		//altFormat: "F j, Y", // 형식을 altFormat으로 변경
		mode: "multiple",
		dateFormat: "F j, Y",
		inline: true,
		minDate: "today",
		maxDate: new Date().getFullYear() + 1 + "-12-31",
		disable: [
			function(date) {
				// return true to disable
				return date.getDay() === 0 || date.getDay() === 6;
			},
		],
		locale: {
			months: {
				shorthand: [
					"1월",
					"2월",
					"3월",
					"4월",
					"5월",
					"6월",
					"7월",
					"8월",
					"9월",
					"10월",
					"11월",
					"12월",
				],
				longhand: [
					"1월",
					"2월",
					"3월",
					"4월",
					"5월",
					"6월",
					"7월",
					"8월",
					"9월",
					"10월",
					"11월",
					"12월",
				],
			},

			firstDayOfWeek: 1, // 월요일부터 시작
		},
	});*/

	// 달력 input을 숨기고 div로 보이게 설정
	/*$("#flatpickr_div").flatpickr({
		altInput: "#date_selector", // 숨겨진 input과 연결
		altFormat: "F j, Y",
		dateFormat: "Y-m-d",
	});*/

	// input의 value가 변경될 때 선택한 날짜가 옆에 나오도록 설정
	$("#date_selector").on("change", function() {
		console.log($("#date_selector").val());
		$(".appStatus").html("");
		let dateArr = [];
		dateArr = $("#date_selector").val().split(",");
		console.log(dateArr);
		for (let i = 0; i < dateArr.length; i++) {
			let dayOfWeek = new Date(dateArr[i]).toLocaleString("ko", {
				weekday: "short",
			});

			let appStatus__line = $("<div>").attr("class", "appStatus__line");
			let vacationDate = $("<div>")
				.attr("class", "vacationDate")
				.html(dateArr[i] + "(" + dayOfWeek + ")");

			let selectorType = $("<div>").attr("class", "selectorType");
			let typeName = $("<div>").attr("class", "typeName").html("연차");
			let selectorArrow = $("<div>")
				.attr("class", "selectorArrow")
				.html($("<i>").attr("class", "fa-solid fa-chevron-down"));
			selectorType.append(typeName).append(selectorArrow);

			let selector__option = $("<div>").attr("class", "selector__option");
			let option__item1 = $("<div>").attr("class", "option__item").html("연차");
			let option__item2 = $("<div>")
				.attr("class", "option__item")
				.html("경조사");
			selector__option.append(option__item1).append(option__item2);

			appStatus__line
				.append(vacationDate)
				.append(selectorType)
				.append(selector__option);
			$(".appStatus").append(appStatus__line);
		}
	});

	// selector 커스텀 해서 만들기
	let showSelector = false;
	$(document).on("click", ".selectorType", function() {
		console.log(showSelector);
		if (!showSelector) {
			console.log("1");
			$(this)
				.parent()
				.find(".selectorArrow")
				.children()
				.attr("class", "fa-solid fa-chevron-up");
			$(this).parent().find(".selector__option").css("display", "block");
			$(".option__item").on("click", function() {
				console.log($(this).parent().parent().find(".typeName"));
				$(this).parent().parent().find(".typeName").html($(this).html());
			});
			showSelector = true;
		} else {
			console.log("2");
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
			console.log("4");
			// 모든 .selector__option 숨기기
			$(".selector__option").css("display", "none");
			$(".selector__option")
				.parent()
				.find(".selectorArrow")
				.children()
				.attr("class", "fa-solid fa-chevron-down");
			// 클릭된 .selectorType에 속한 .selector__option만 보이게 설정
			if (showSelector) {
				console.log("5");
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
			console.log("3");
			$(".selector__option").css("display", "none");
			$(".selector__option")
				.parent()
				.find(".selectorArrow")
				.children()
				.attr("class", "fa-solid fa-chevron-down");
			showSelector = false;
		}
	});
});
