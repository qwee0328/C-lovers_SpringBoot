
document.addEventListener('DOMContentLoaded', function() {
	function modalInitail() {
		$(".modalName").text("일정 추가");
		$(".insertSchedule__title").val("");
		$(".insertSchedule__startDate").val("");
		$(".insertSchedule__startTime").val("09:00");
		$(".insertSchedule__endDate").val("");
		$(".insertSchedule__endTime").val("18:00");
		$(".insertSchedule__content").html("");

		let allDayState = false;
		$(".insertSchedule__allDay").prop("checked", allDayState).trigger("change");

		$(".insertSchedule__repeat").prop("checked", false).trigger("change");
		$(".calendarModal__save").css("display", "block");
		$(".calendarModal__updateSave").css("display", "none");
		$(".range__count").val(1);
		$("input[type='checkbox'][name='week']").prop("checked", false);

		$.ajax({
			url: "/schedule/calendarByEmpId",
			async: false
		}).done(function(resp) {
			$(".calendarModal__calNameList *").remove();
			for (let i = 0; i < resp.length; i++) {
				$(".calendarModal__calNameList").append($("<option>").val(resp[i].id).text(resp[i].name).attr("color", resp[i].color));
			}
			$(".calendarModal__calNameList:first-child").prop("selected", true);
		});
	}

	let calendarEl = document.getElementById('homeCalendar');
	let calendarListEl = document.getElementById('calendarList');

	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		googleCalendarApiKey: "AIzaSyA7ZEbhFsxIvQqSdU-m-3PgDvCfxICmYBc", // 구글 캘린더 api key
		eventSources: [{ // 구글 캘린더를 이용해 한국 공휴일 불러오기
			googleCalendarId: "ko.south_korea.official#holiday@group.v.calendar.google.com",
			className: "koHolidays",
			color: "transparent"
		}
		, {
			events: function(info, successCallback) {

				let eventDatas = [];
				$.ajax({
					url: "/schedule/selectAll",
					async: false
				}).done(function(resp) {
					console.log(resp);
					for (let i = 0; i < resp.length; i++) {

						let eventData = {
							id: resp[i].id,
							title: resp[i].title,
							allDay: resp[i].all_day,
							color: resp[i].color,
							content: resp[i].content,
							calNameVal: resp[i].calendar_id,
							registor: resp[i].emp_id,
							reg_date: resp[i].reg_date,
							repeat: false
						}


						if (resp[i].recurring_id == 0) {
							eventData.start = resp[i].start_date; // 일정 시작 일자
							eventData.end = resp[i].end_date; // 일정 종료 일자

						}
						else {
							eventData.repeat = true;
							let endKey = resp[i].endKey;
							let frequency_whenOption = resp[i].frequency_whenOption == "weekDay" ? "weekly" : resp[i].frequency_whenOption;
							let chkWeekDayList = resp[i].selectWeeks.split(",");
							if (chkWeekDayList.length >= 1)
								chkWeekDayList = chkWeekDayList.map((e) => { return parseInt(e); });
							// resp[i].frequency_whenOption == "weekDay" ? [0, 1, 2, 3, 4] :
							let endValue = resp[i].endValue;
							if (endKey == "count" && frequency_whenOption == "weekly") endValue *= chkWeekDayList.length;
							let rrule = {
								freq: frequency_whenOption,
								[endKey]: endValue,
								interval: resp[i].intervalCnt,
								dtstart: resp[i].start_date
							}
							if (frequency_whenOption == "weekly") {
								rrule.byweekday = chkWeekDayList;
							}
							eventData.rrule = rrule;
							eventData.startDateTime = resp[i].start_date;
							eventData.endDateTime = resp[i].end_date.slice(11, 16);
							eventData.groupId = resp[i].recurring_id;
							eventData.frequency__when = resp[i].frequency_whenOption;
							eventData[`${endKey}`] = resp[i].endValue;
						}
						eventDatas.push(eventData);
					}
					successCallback(eventDatas);
					eventsLoaded = true;
				});



			}}
		],


		headerToolbar: { // 캘린더 header
			left: 'title prev next',
			center: '',
			right: 'addEventButton'
		},
		views: { // 타이틀 형식
			dayGridMonth: { // 달
				titleFormat: "YYYY.MM",
				dayMaxEventRows: 0
			}
		},
		customButtons: {
			addEventButton: {
				text: '+',
				click: function() {
					modalInitail();
					$(".scheduleInsertModal").modal({
						showClose: false
					});
					$(".calendarModal__cancel").on("click", $.modal.close);
				}
			}
		},

		locale: "ko", // 캘린더 한국어로 출력
		dayCellContent: function(e) { // 달력에서 일 이라는 글자 제거
			return e.dayNumberText.replace("일", "");
		},
		selectable: true,
		eventClick: function(e) {
			$(".fc-popover").css("display", "none"); // 팝업창 제거
			e.jsEvent.preventDefault(); // 구글 캘린더 이동 막기
		},
		dateClick: function(info) {
			calendarList.gotoDate(info.dateStr);
		}
	});




	const calendarList = new FullCalendar.Calendar(calendarListEl, {
		initialView: 'listDay',
		googleCalendarApiKey: "AIzaSyA7ZEbhFsxIvQqSdU-m-3PgDvCfxICmYBc", // 구글 캘린더 api key
		eventSources: [{ // 구글 캘린더를 이용해 한국 공휴일 불러오기
			googleCalendarId: "ko.south_korea.official#holiday@group.v.calendar.google.com",
			className: "koHolidays",
			color: "transparent"
		}, {
			events: function(info, successCallback) {

				let eventDatas = [];
				$.ajax({
					url: "/schedule/selectAll",
					async: false
				}).done(function(resp) {

					for (let i = 0; i < resp.length; i++) {

						let eventData = {
							id: resp[i].id,
							title: resp[i].title,
							allDay: resp[i].all_day,
							color: resp[i].color,
							content: resp[i].content,
							calNameVal: resp[i].calendar_id,
							registor: resp[i].emp_id,
							reg_date: resp[i].reg_date,
							repeat: false
						}


						if (resp[i].recurring_id == 0) {
							eventData.start = resp[i].start_date; // 일정 시작 일자
							eventData.end = resp[i].end_date; // 일정 종료 일자

						}
						else {
							eventData.repeat = true;
							let endKey = resp[i].endKey;
							let frequency_whenOption = resp[i].frequency_whenOption == "weekDay" ? "weekly" : resp[i].frequency_whenOption;
							let chkWeekDayList = resp[i].selectWeeks.split(",");
							if (chkWeekDayList.length >= 1)
								chkWeekDayList = chkWeekDayList.map((e) => { return parseInt(e); });
							// resp[i].frequency_whenOption == "weekDay" ? [0, 1, 2, 3, 4] :
							let endValue = resp[i].endValue;
							if (endKey == "count" && frequency_whenOption == "weekly") endValue *= chkWeekDayList.length;
							let rrule = {
								freq: frequency_whenOption,
								[endKey]: endValue,
								interval: resp[i].intervalCnt,
								dtstart: resp[i].start_date
							}
							if (frequency_whenOption == "weekly") {
								rrule.byweekday = chkWeekDayList;
							}
							eventData.rrule = rrule;
							eventData.startDateTime = resp[i].start_date;
							eventData.endDateTime = resp[i].end_date.slice(11, 16);
							eventData.groupId = resp[i].recurring_id;
							eventData.frequency__when = resp[i].frequency_whenOption;
							eventData[`${endKey}`] = resp[i].endValue;
						}
						eventDatas.push(eventData);
					}
					successCallback(eventDatas);
					eventsLoaded = true;
				});



			}
		}],
		listDayFormat: "YYYY.MM.DD",
		headerToolbar: { // 캘린더 header
			left: '',
			center: '',
			right: ''
		},
		locale: "ko",
		height: true,
		eventClick: function(e) { // 일정 클릭 시 일정 상세보기
			$(".fc-popover").css("display", "none"); // 일정 더보기 팝업찹 가리기
			e.jsEvent.preventDefault();
		},
		noEventsContent: '일정 없음' // "No events to display" 메시지를 없애기

	});



	calendar.render();
	calendarList.render();
	$(".calendarModal__save").on("click", function() { // 일정 등록 모달 저장 누를 시 일정 등록
		if ($(".insertSchedule__startDate").val() > $(".insertSchedule__endDate").val()) {
			Swal.fire({
				icon: "error",
				text: "일정 시작일보다 일정 종료일이 앞설 수 없습니다."
			});
			$(".insertSchedule__endDate").val($(".insertSchedule__startDate").val());
			return false;
		}
		let events = generateEvent();
		calendar.addEventSource(events);

		$.modal.close(); // 등록 후 모달 닫기
	});

	function generateEvent() {
		console.log("generateEvent")
		let startDate = new Date($(".insertSchedule__startDate").val());
		startDate.setHours((parseInt($(".insertSchedule__startTime").val().slice(0, 2)) + 9), $(".insertSchedule__startTime").val().slice(3, 5)); // +9 하는 이유는 한국 시간으로 변경하기 위함.
		let startDateStr = startDate.toISOString().slice(0, 16);

		let endDate = new Date($(".insertSchedule__endDate").val());
		endDate.setHours((parseInt($(".insertSchedule__endTime").val().slice(0, 2)) + 9), $(".insertSchedule__endTime").val().slice(3, 5));

		if ($(".insertSchedule__allDay").is(":checked")) { endDate.setDate(endDate.getDate() + 1); }
		let endDateStr = endDate.toISOString().slice(0, 16);

		let reg_date = new Date();
		reg_date.setHours(reg_date.getHours() + 9);
		let events = [];

		let eventData = {
			title: $(".insertSchedule__title").val(), // 일정 제목
			allDay: $(".insertSchedule__allDay").is(":checked"), // 종일 여부
			color: $(".calendarModal__calNameList option:selected").attr("color"),
			content: $(".insertSchedule__content").html(),
			calNameVal: $(".calendarModal__calNameList option:selected").val(),
			reg_date: reg_date.toISOString().slice(0, 16),
			repeat: $(".insertSchedule__repeat").is(":checked") // 반복 여부
		}


		if ($(".insertSchedule__repeat").is(":checked")) {
			let frequency__whenOption = $(".frequency__when option:selected").val() == "weekDay" ? "weekly" : $(".frequency__when option:selected").val();
			let chkWeekDayList = $(".frequency__when option:selected").val() == "weekDay" ? [0, 1, 2, 3, 4] : $("input[type=checkbox][name='week']:checked").map((i, e) => { return parseInt($(e).val()); }).toArray();

			let endKey = $(":radio[name='period']:checked").val();
			let endValue = $(":radio[name='period']:checked").next().children().val();
			if (endKey == "count" && frequency__whenOption == "weekly") endValue *= chkWeekDayList.length; // 월, 수 고르면 월 수 월 로 끝남. -> 월, 수 *3이 되도록


			let during = Math.ceil((new Date($(".insertSchedule__endDate").val()).getTime() - new Date($(".insertSchedule__startDate").val()).getTime()) / (1000 * 60 * 60 * 24));

			eventData.frequency__when = $(".frequency__when option:selected").val();
			eventData[`${endKey}`] = $(":radio[name='period']:checked").next().children().val();
			eventData.chkWeekDayList = chkWeekDayList;

			if (during == 0) { // 1일짜리 이벤트 일 때
				let ed = { ...eventData };

				let rrule = {
					freq: frequency__whenOption, // daily, weekly, monthly, yearly
					[endKey]: endValue,  // count: $(".range__count").val() or until: endDate
					interval: parseInt($(".frequency__every option:selected").val()), //ex 3이면 3주마다
					dtstart: startDate
				}

				/*	rrule.byhours=range(10,12);*/
				ed.rrule = rrule;
				ed.startDateTime = startDate.toISOString().slice(0, 16);
				ed.endDateTime = endDate.toISOString().slice(11, 16);

				if (ed.allDay) {
					ed.startDateTime = startDate.toISOString().slice(0, 11) + "00:00";
					ed.endDateTime = "00:00";
					endDateStr = endDate.toISOString().slice(0, 11) + "00:00";
				}
				if (frequency__whenOption == "weekly") {
					ed.rrule.byweekday = chkWeekDayList; // 선택한 요일
				}

				$.ajax({
					url: "/schedule/insertReccuring",
					data: {
						during: during,
						frequency_whenOption: eventData.frequency__when,
						endKey: endKey,
						endValue: eventData[`${endKey}`],
						intervalCnt: parseInt(rrule.interval),
						startTime: ed.startDateTime,
						endTime: ed.endDateTime,
						selectWeeks: chkWeekDayList.toString(),
						calendar_id: parseInt(eventData.calNameVal),
						title: ed.title,
						content: ed.content,
						start_date: ed.startDateTime,
						end_date: endDateStr,
						reg_date: ed.reg_date,
						all_day: ed.allDay
					},
					type: "post",
					async: false // 이거 안하면 비동기 처리 되면서 id, registor 등록 전에 events.push(~), return events 되어버림... done안에 해당 구문 넣어도 정상동작 x 그래서 동기적으로 동작하도록 해주는 코드
				}).done(function(resp) {
					ed.id = resp.id;
					ed.registor = resp.emp_id;
					ed.groupId = resp.recurring_id;
				});

				events.push(ed);


			} else { // 1일 이상 이벤트 일 때
				Swal.fire({
					icon: "error",
					text: "반복이벤트는 2일 이상 설정할 수 없습니다."
				});
				return false;
			}

		} else { // 반복 일정 아니면
			eventData.start = startDateStr; // 일정 시작 일자
			eventData.end = endDateStr; // 일정 종료 일자

			if (eventData.allDay) {
				eventData.start = startDateStr.slice(0, 11) + "00:00";
				eventData.end = endDateStr.slice(0, 11) + "00:00";
			}

			$.ajax({
				url: "/schedule/insert",
				data: {
					calendar_id: parseInt(eventData.calNameVal),
					title: eventData.title,
					content: eventData.content,
					start_date: eventData.start,
					end_date: eventData.end,
					reg_date: eventData.reg_date,
					all_day: eventData.allDay
				},
				type: "post",
				async: false // 이거 안하면 비동기 처리 되면서 id, registor 등록 전에 events.push(~), return events 되어버림... done안에 해당 구문 넣어도 정상동작 x 그래서 동기적으로 동작하도록 해주는 코드
			}).done(function(resp) {
				eventData.id = resp.id;
				eventData.registor = resp.emp_id;
			});
			events.push(eventData);
		}

		return events;
	}
	
	$(".insertSchedule__allDay").on("change", function() { // 일정 등록 시 종일 선택하면 시간 입력 불가
		if ($(this).is(":checked")) {
			$("input[type=time]").attr("disabled", true);
		} else {
			$("input[type=time]").removeAttr("disabled");
		}
	});

	$(".insertSchedule__repeat").on("change", function() { // 일정 등록 시 반복 누르면 관련 태그 보이게
		if ($(this).is(":checked")) {
			$(".bodyLine__repeat").css("display", "flex");
			$(".frequency__when").val("daily").trigger("change"); // 반복 빈도 기본 매일로 체크
			$(":radio[name='period']").prop("checked", false); // 반복 범위 체크 설정 초기화
			$(":radio[name='period'][value='count']").prop("checked", true); // 반복 범위 기본 개수로 체크
			$("input[type='checkbox'][name='week']").prop("checked", false);
			$(".range__count").val(1);
			$(".range__endDate").val($(".insertSchedule__endDate").val());
			let dur = (new Date($(".insertSchedule__endDate").val()).getTime() - new Date($(".insertSchedule__startDate").val()).getTime()) / (1000 * 60 * 60 * 24) + 1;
			if (dur != 1) {
				$(".weekDayOption").prop("disabled", true); // 기간이 하루 이상이면 매주 월-금 선택 불가
			} else {
				$(".weekDayOption").prop("disabled", false);
			}
		} else {
			$(".bodyLine__repeat").css("display", "none");
		}
	});
	
	$(".range__endDate").on("change", function() {
		$(":radio[name='period'][value='until']").prop("checked", true); // 반복 종료일 선택 시 반복 범위 반복 종료일로 변경
		// $(".insertSchedule__endDate").val($(this).val()); // 반복 종료일과 이벤트 종료일 동일하게 설정
		if ($(this).val() < $(".insertSchedule__endDate").val()) {
			$(this).val($(".insertSchedule__endDate").val());
			Swal.fire({
				icon: "error",
				text: "일정 종료일보다 반복 종료일이 작을 수 없습니다."
			});
		}
	})

	$(".range__count").on("keydown", function() {
		$(":radio[name='period'][value='count']").prop("checked", true);
	})

	$(".insertSchedule__endDate").on("change", function() {
		$(".insertSchedule__repeat").prop("checked", false).trigger("change"); // 날짜 변경 시 반복 버튼 체크 해제  
	});
	$(".insertSchedule__startDate").on("change", function() {
		$(".insertSchedule__repeat").prop("checked", false).trigger("change"); // 날짜 변경 시 반복 버튼 체크 해제
	});


	$("input[type='checkbox'][name='week']").on("change", function() {
		let dur = (new Date($(".insertSchedule__endDate").val()).getTime() - new Date($(".insertSchedule__startDate").val()).getTime()) / (1000 * 60 * 60 * 24) + 1;
		if (dur > 1) { // 기간이 하루 이상이면 요일 하나만 지정 가능
			$("input[type='checkbox'][name='week']:checked").prop("checked", false);
			$(this).prop("checked", true);
		}
	});


	$(".frequency__when").on("change", function(e) {
		let max;
		let txt;
		$(".insertSchedule__repeatChk").css("display", "none");
		switch ($(this).val()) {
			case "daily": max = 31; txt = "일"; break;
			case "weekly": $(".insertSchedule__repeatChk").css("display", "flex");
			case "weekDay": max = 4; txt = "주"; break;
			case "monthly": max = 11; txt = "개월"; break;
			case "yearly": max = 9; txt = "년"; break;
		}
		$(".frequency__every option").remove();
		for (let i = 1; i <= max; i++) {
			let option = $("<option>").val(i).text(i);
			$(".frequency__every").append(option);
		}
		$(".frequency__txt").text(`${txt} 마다`);
	});
});