document.addEventListener("DOMContentLoaded", function() {
	let rest_reason_type = [];
	$.ajax({
		url: "/humanResources/selectRestReasonType",
		dataType: "json",
	}).done(function(resp) {
		for (let i = 0; i < resp.length; i++) {
			rest_reason_type.push(resp[i]);
		}
	})

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
			for (let i = 0; i < rest_reason_type.length; i++) {
				let option__item = $("<div>").attr("class", "option__item").html(rest_reason_type[i]);
				selector__option.append(option__item)
			}

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

	$("#vacationdraftingBtn").on("click", function() {
		console.log($("#vacationReason").val())
		if($("#processEmployeeIDList").val() === ""){
			alert("결제선을 설정해주세요.");
			return;
		}
		if($("#date_selector").val()===""){
			alert("휴가 날짜를 선택해주세요.");
			return;
		}
		if($("#vacationReason").val()===""){
			alert("휴가 사유를 입력해주세요.");
			$("#vacationReason").focus();
			return;
		}
		if ($("#processEmployeeIDList").val() !== "" && $("#date_selector").val()!=="" && $("#vacationReason").val() !== "") {
			$.ajax({
				
			}).done(function(){
				
			})
		}
	})

});

$(document).ready(function() {
	$("#approvalLineBtn").on("click", function() {
		$(".approvalLineModal").modal({
			fadeDuration: 300,
			showClose: false
		});
		$("#approvalLineModal__cancle").on("click", $.modal.close);
	})

});