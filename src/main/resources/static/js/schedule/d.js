// full calendar 관련 main function
let eventCnt = 0; // 나중에 삭제하고 auto_increment 값 불러오ㅏ 넣어주기
let groupCnt = 0; // 마찬가지 반복이벤트 아이디

$(document).ready(function(){
	
})

document.addEventListener('DOMContentLoaded', function() {
	function modalInitail(datas) {
		$(".modalName").text("일정 추가");
		$(".insertSchedule__title").val("");
		$(".insertSchedule__startTime").val("09:00");
		$(".insertSchedule__endDate").val("");
		$(".insertSchedule__endTime").val("18:00");
		$(".insertSchedule__content").html("");

		if (datas.view.type == "dayGridMonth") {
			$(".insertSchedule__allDay").prop("checked", false).trigger("change");
		} else {
			$(".insertSchedule__allDay").prop("checked", datas.all_day).trigger("change");
		}

		$(".insertSchedule__repeat").prop("checked", false).trigger("change");
		$(".calendarModal__save").css("display", "block");
		$(".calendarModal__updateSave").css("display", "none");
		$(".range__count").val(1);
		$("input[type='checkbox'][name='week']").prop("checked", false);

	}
	
	let calendarEl = document.getElementById('calendar');
/*	$.ajax({
		url:"/schedule/selectAll"
	}).done(function(resp){
		console.log(resp);
	});*/
	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		height: true,
		googleCalendarApiKey: "AIzaSyAStWVnOxLheNeBURhUh6-xC-jitc60FcE", // 구글 캘린더 api key
		eventSources: [{ // 구글 캘린더를 이용해 한국 공휴일 불러오기
			googleCalendarId: "ko.south_korea.official#holiday@group.v.calendar.google.com",
			className: "koHolidays",
			color: "white"
		}],
		events:function(start, end, timezone, callback){
			let events = [];
			$.ajax({
				url:"/schedule/selectAll",
				async:false
			}).done(function(resp){
				for(let i=0; i<resp.length; i++){
					if(resp[i].recurring_id == 0){
							
					}else{
						
					}
					events.push({
						
					});
				}
				callback(events);
			});
		},
		
		headerToolbar: { // 캘린더 header
			left: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth',
			center: 'prev title next today',
			right: ''
		},
		views: { // 타이틀 형식
			dayGridMonth: { // 달
				titleFormat: "YYYY.MM",
				dayMaxEventRows: 2 // 캘린더 한 칸에 최대 보여지는 이벤트 개수(+more n -> 누르면 자세히 보기 나옴)
			},
			timeGridWeek: { // 주
				titleFormat: function(date) {
					if (date.date.year == date.end.year) { // 이번 주에 대하여 년도가 바뀌지 않으면
						if ((date.date.month + 1) == (date.end.month + 1)) { // 이번 주에 대하여 월이 바뀌지 않으면
							return `${date.date.year}. ${date.date.month + 1}. ${date.date.day} - ${date.end.day}`;
						} else { // 이번 주에 대하여 월이 바뀌면
							return `${date.date.year}. ${date.date.month + 1}. ${date.date.day} - ${date.end.month + 1}. ${date.end.day}`;
						}
					} else { // 이번 주에 대하여 년도가 바뀌면 (달도 함께 바뀜)
						return `${date.date.year}. ${date.date.month + 1}. ${date.date.day} - ${date.end.year}. ${date.end.month + 1}. ${date.end.day}`;
					}
				}
			},
			timeGridDay: { // 일
				titleFormat: "YYYY.MM.DD"
			},
			listMonth: { // 달 목록
				titleFormat: "YYYY.MM"
			},

		},

		locale: "ko", // 캘린더 한국어로 출력
		buttonText: { // 오늘 달력으로 이동하는 버튼 text 변경
			today: "오늘"
		},
		selectable: true,

		dateClick: function(e) { // 날짜 클릭 시 일정 등록 모달 띄우기
			modalInitail(e);
			$(".insertSchedule__startDate").val(e.dateStr.slice(0,10)); // 시작 날짜
			if (e.view.type == "dayGridMonth") {
				let selectDate = new Date(e.date);
				selectDate.setHours(selectDate.getHours() + 9, selectDate.getMinutes());
				$(".insertSchedule__endDate").val(e.dateStr); // 끝 날짜 
				$(".range__endDate").val($(".insertSchedule__endDate").val());
			} else {
				if (e.allDay) {
					$(".insertSchedule__startDate").val(e.dateStr); // 시작 날짜
					$(".insertSchedule__startTime").val("00:00");
					let selectDate = new Date(e.dateStr);
					selectDate.setDate(selectDate.getDate() + 1);
					$(".insertSchedule__endDate").val(selectDate.toISOString().slice(0, 10)); // 끝 날짜
					$(".insertSchedule__endTime").val("00:00");
					$(".range__endDate").val($(".insertSchedule__endDate").val());
				} else {
					let selectDate = new Date(e.date);
					selectDate.setHours(selectDate.getHours() + 9, selectDate.getMinutes());
					$(".insertSchedule__startDate").val(selectDate.toISOString().slice(0, 10)); // 시작 날짜
					$(".insertSchedule__startTime").val(selectDate.toISOString().slice(11, 16));
					selectDate.setHours(selectDate.getHours(), selectDate.getMinutes() + 30);
					$(".insertSchedule__endDate").val(selectDate.toISOString().slice(0, 10)); // 끝 날짜
					$(".insertSchedule__endTime").val(selectDate.toISOString().slice(11, 16));
					$(".range__endDate").val(selectDate.toISOString().slice(0, 10));
				}
			}

			$(".scheduleInsertModal").modal({
				showClose: false
			});
		},
		select: function(e) { // 날짜 드래그 시 일정 등록 모달 창 띄우기
			modalInitail(e);
			$(".insertSchedule__startDate").val(e.startStr); // 시작 일자 넣어주기

			if (e.allDay) {
				if (e.view.type == "dayGridMonth") { // dayGridMonth로 볼 때, select로 선택하면 기본적으로 allDay 속성이 true이나, 임의로 9-6로 바꾸기 때문에 날짜 계산할 때 -1 해줘야함. (기존 endStr 날짜는 12시까지여서 다음 날까지로 계산됨.)
					let date = new Date(e.endStr);
					date.setDate(new Date(e.endStr).getDate() - 1);
					$(".insertSchedule__endDate").val(date.toISOString().slice(0, 10)); // 종료 일자 넣어주기
					$(".range__endDate").val($(".insertSchedule__endDate").val());
				} else { // dayGridMonth가 아니면 날짜 선택 시에 allDay 속성을 선택할 수 있기 때문에 날짜 그대로 넣어줌.
					$(".insertSchedule__endDate").val(e.endStr); // 종료 일자 넣어주기
					$(".insertSchedule__startTime").val("00:00");
					$(".insertSchedule__endTime").val("00:00");
					$(".range__endDate").val($(".insertSchedule__endDate").val());
				}
			} else {
				if (e.view.type != "dayGridMonth") { //allDay 아니면서 dayGridMonth 아닐 때
					let selectStartDate = new Date(e.start);
					selectStartDate.setHours(selectStartDate.getHours() + 9, selectStartDate.getMinutes());
					$(".insertSchedule__startDate").val(selectStartDate.toISOString().slice(0, 10)); // 시작 날짜
					$(".insertSchedule__startTime").val(selectStartDate.toISOString().slice(11, 16));
					let selectEndDate = new Date(e.end);
					selectEndDate.setHours(selectEndDate.getHours() + 9, selectEndDate.getMinutes());
					$(".insertSchedule__endDate").val(selectEndDate.toISOString().slice(0, 10)); // 끝 날짜
					$(".insertSchedule__endTime").val(selectEndDate.toISOString().slice(11, 16));
					$(".range__endDate").val($(".insertSchedule__endDate").val());
				}
			}

			$(".scheduleInsertModal").modal({
				showClose: false
			});
		},
		eventClick: function(e) { // 일정 클릭 시 일정 상세보기
			$(".fc-popover").css("display", "none"); // 일정 더보기 팝업찹 가리기

			console.log(e);
			if (!$(e.el).hasClass("koHolidays")) {
				let { event: { _def: datas, _instance: { range } } } = e;

				let startDate = range.start.toISOString().slice(0, 10) + " " + range.start.toISOString().slice(11, 16);
				let endDate = range.end.toISOString().slice(0, 10) + " " + range.end.toISOString().slice(11, 16);
				$(".calendarModal__calName").attr("val", datas.extendedProps.calNameVal)
				$(".calendarModal__scheTitle").text(datas.title);
				$(".calendarModal__scheDate").text(`${startDate} ~ ${endDate}`)
				$(".calendarModal__scheReg").text(`${datas.extendedProps.registor} (${datas.extendedProps.reg_date})`);
				$("#eventId").val(datas.publicId);

				$(".calendarModal__scheContent").text(datas.extendedProps.content);

				$(".scheduleViewModal").modal({
					showClose: false
				});


				$(".calendarModal__delete").on("click", () => { 
					calendar.getEventById($("#eventId").val()).remove();
					$.ajax({
						url:"/schedule/delete",
						data:{id:$("#eventId").val()},
						type:"post"
					}).done(function(resp){
						if(resp==1)
							$.modal.close(); 
					});
					
				}); // 일정 삭제
			} else {
				e.jsEvent.preventDefault(); // 구글 일정 클릭 시 구글 캘린더 페이지로 이동하는 이벤트 방지
			}
		},

		dayCellContent: function(e) { // 달력에서 일 이라는 글자 제거
			return e.dayNumberText.replace("일", "");
		}
	});



	///////////////////////////////////////////////////////////////
	function generateEvent() {
		let startDate = new Date($(".insertSchedule__startDate").val());
		startDate.setHours((parseInt($(".insertSchedule__startTime").val().slice(0, 2)) + 9), $(".insertSchedule__startTime").val().slice(3, 5)); // +9 하는 이유는 한국 시간으로 변경하기 위함.
		let startDateStr = startDate.toISOString().slice(0, 16);

		let endDate = new Date($(".insertSchedule__endDate").val());
		endDate.setHours((parseInt($(".insertSchedule__endTime").val().slice(0, 2)) + 9), $(".insertSchedule__endTime").val().slice(3, 5));

		if ($(".insertSchedule__allDay").is(":checked")) { endDate.setDate(endDate.getDate() + 1); }
		let endDateStr = endDate.toISOString().slice(0, 16);

		let reg_date = new Date();
		reg_date.setHours(reg_date.getHours()+9);
		let events = [];

		let eventData = {
			/*id: (++eventCnt), // 직접 넣어주는 것보단 DB에 저장을 하고 가져온 번호를 넣어주는 방향.*/
			title: $(".insertSchedule__title").val(), // 일정 제목
			all_day: $(".insertSchedule__allDay").is(":checked"), // 종일 여부
			color: "#75B47D",
			content: $(".insertSchedule__content").html(),
			calNameVal: $(".calendarModal__calNameList option:selected").val(),
			/*calName: $(".calendarModal__calNameList option:selected").html(),*/
			/*registor: "등록자", // 나중에 ajax로 insert 쿼리 날린 후 값 가져오기.. (session id 값...)     */
			reg_date: reg_date.toISOString().slice(0,16), // 나중에 ajax로 insert 쿼리 날린 후 값 가져오기.. (now() 해서 날짜값...)  
			repeat: $(".insertSchedule__repeat").is(":checked") // 반복 여부
		}





		if ($(".insertSchedule__repeat").is(":checked")) {
			let frequency__whenOption = $(".frequency__when option:selected").val() == "weekDay" ? "weekly" : $(".frequency__when option:selected").val();
			let chkWeekDayList = $(".frequency__when option:selected").val() == "weekDay" ? [0, 1, 2, 3, 4] : $("input[type=checkbox][name='week']:checked").map((i, e) => { return parseInt($(e).val()); }).toArray();

			let endKey = $(":radio[name='period']:checked").val();
			let endValue = $(":radio[name='period']:checked").next().children().val();
			if (endKey == "count" && frequency__whenOption == "weekly") endValue *= chkWeekDayList.length; // 월, 수 고르면 월 수 월 로 끝남. -> 월, 수 *3이 되도록


			// 몇일짜리 이벤트 인지 구하기
			//Math.ceil((endDate.getTime()-startDate.getTime())/(1000 * 60 * 60 * 24)); -> 문제점: 8~10일로 잡앗을 때 시작시간이 오후 09, 끝시간이 오전 06-> 3일로 나와야하는데 2일로 나옴...
			// 그럼 기준 시간을 12시로 잡고 해보자, 12시면 자정이라 하루가 빠짐 -> 1 더해주기        
			let during = Math.ceil((new Date($(".insertSchedule__endDate").val()).getTime() - new Date($(".insertSchedule__startDate").val()).getTime()) / (1000 * 60 * 60 * 24)) + 1;

			eventData.frequency__when = $(".frequency__when option:selected").val();
			//eventData.repeatChk = $("input[type='checkbox'][name='week']:checked").val();
			eventData[`${endKey}`] = $(":radio[name='period']:checked").next().children().val();
			eventData.chkWeekDayList = chkWeekDayList;
			
			
			if (during == 1) { // 1일짜리 이벤트 일 때
				let ed = { ...eventData };

				let rrule = {
					freq: frequency__whenOption, // daily, weekly, monthly, yearly
					[endKey]: endValue,  // count: $(".range__count").val() or until: endDate
					interval: parseInt($(".frequency__every option:selected").val()), //ex 3이면 3주마다
					dtstart: startDate
				}
				ed.rrule = rrule;
				ed.startDateTime = startDate.toISOString().slice(0, 16);
				ed.endDateTime = endDate.toISOString().slice(11, 16);

				if (frequency__whenOption == "weekly") {
					ed.rrule.byweekday = chkWeekDayList; // 선택한 요일
				}

				$.ajax({
					url:"/schedule/insertRuccuring",
					data: {
					    during : during,
					    frequency__whenOption : eventData.frequency__when,
					    endKey : endKey,
					    endValue : eventData[`${endKey}`],
					    intervalCnt : parseInt(rrule.interval),
					    startTime : ed.startDateTime, 
					    endTime : ed.endDateTime,
					    selectWeeks : chkWeekDayList.toString(),
					   	calendar_id : parseInt(eventData.calNameVal),
					    title : ed.title,
					    content : ed.content,
					  	start_date : startDateStr,
					    end_date : endDateStr,
					    reg_date : ed.reg_date,
					    all_day : ed.all_day
					},
					type:"post",
					async:false // 이거 안하면 비동기 처리 되면서 id, registor 등록 전에 events.push(~), return events 되어버림... done안에 해당 구문 넣어도 정상동작 x 그래서 동기적으로 동작하도록 해주는 코드
				}).done(function(resp){
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
			
			// 1일 이벤트 여러요일 선택했을 때 고려해야함
			
			/*	let dbEvents =[];
				let currentDate = startDate;
				if (eventData.all_day) during++;

				// 만약 주를 선택했다면 startDate를 가장 최신 고른 요일로..
				if (frequency__whenOption == "weekly") {
					let targetDay = parseInt(($("input[type=checkbox][name='week']:checked").val())) + 1 % 7;
					let daysToAdd = (targetDay - currentDate.getDay() + 7) % 7;
					currentDate.setDate(currentDate.getDate() + daysToAdd);
				}

				let term = parseInt($(".frequency__every option:selected").val());// 간격 (2일마다, 3일마다,...)
				
				
				$.ajax({
					url:"/schedule/insertRuccuring",
					data: {
					    during : during,
					    frequency__whenOption : eventData.frequency__when,
					    endKey : endKey,
					    endValue : eventData[`${endKey}`],
					    intervalCnt : term,
					    selectWeeks : eventData.chkWeekDayList.toString(),
					},
					type:"post",
					async:false // 이거 안하면 비동기 처리 되면서 id, registor 등록 전에 events.push(~), return events 되어버림... done안에 해당 구문 넣어도 정상동작 x 그래서 동기적으로 동작하도록 해주는 코드
				}).done(function(resp){
					eventData.id = resp.id;
					eventData.registor = resp.emp_id;
					eventData.groupId = resp.recurring_id;
				});
				
				
				if (endKey == "count") { // 반복 횟수 지정
					for (let i = 0; i < endValue; i++) {
						setEvents(events, dbEvents, endDate, eventData, currentDate, during, frequency__whenOption, term);
					}
				} else { // 종료 일자 지정

					let range_endDate = new Date($(".range__endDate").val());
					while (currentDate <= range_endDate) {
						setEvents(events, dbEvents, endDate, eventData, currentDate, during, frequency__whenOption, term);
					}

					let lastEventDate = new Date(events[events.length - 1].end);
					if (lastEventDate > range_endDate) {
						range_endDate.setHours(endDate.getHours(), endDate.getMinutes());
						events[events.length - 1].end = range_endDate.toISOString().slice(0, 16);
					}
				}*/
				
			
		}

		} else { // 반복 일정 아니면
			eventData.start = startDateStr; // 일정 시작 일자
			eventData.end = endDateStr; // 일정 종료 일자
			
			$.ajax({
				url:"/schedule/insert",
				data: {
				    calendar_id : parseInt(eventData.calNameVal),
				    title : eventData.title,
				    content : eventData.content,
				  	start_date : startDateStr,
				    end_date : endDateStr,
				    reg_date : eventData.reg_date,
				    all_day : eventData.all_day
				},
				type:"post",
				async:false // 이거 안하면 비동기 처리 되면서 id, registor 등록 전에 events.push(~), return events 되어버림... done안에 해당 구문 넣어도 정상동작 x 그래서 동기적으로 동작하도록 해주는 코드
			}).done(function(resp){
				eventData.id = resp.id;
				eventData.registor = resp.emp_id;
			});
			events.push(eventData);
		}

		return events;
	}

	// 이벤트 생성
	let setEvents = (events, dbEvents, endDate, eventData, currentDate, during, frequency__whenOption, term) => {
		let ed = { ...eventData };

		ed.start = currentDate.toISOString().slice(0, 16); // 일정 시작 일자
		let currentEndDate = new Date(currentDate);
		currentEndDate.setDate(currentDate.getDate() + during);
		currentEndDate.setHours(endDate.getHours(), endDate.getMinutes());
		ed.end = currentEndDate.toISOString().slice(0, 16); // 일정 종료 일자
		
		let dbMap = new Map();
		dbMap.set("calendar_id", parseInt(ed.calNameVal));
		dbMap.set("title", ed.title);
		dbMap.set("content", ed.content);
		dbMap.set("start_date",ed.start);
		dbMap.set("end_date",ed.end);
		dbMap.set("reg_date",ed.reg_date);
		dbMap.set("all_day",ed.all_day);
		dbEvents.push(Object.fromEntries(dbMap));
		
		/*dbEvents.push({
			calendar_id : parseInt(ed.calNameVal),
		    title : ed.title,
		    content : ed.content,
		  	start_date : ed.start,
		    end_date : ed.end,
		    reg_date : ed.reg_date,
		    all_day : ed.all_day
		})*/
		
		events.push(ed);

		if (frequency__whenOption == "daily") { // daily
			currentDate.setDate(currentDate.getDate() + term);
		} else if (frequency__whenOption == "weekly") {
			currentDate.setDate(currentDate.getDate() + term * 7);
		} else if (frequency__whenOption == "monthly") {
			currentDate.setMonth(currentDate.getMonth() + term);
		} else if (frequency__whenOption == "yearly") {
			currentDate.setFullYear(currentDate.getFullYear() + term);
		}
	}
	///////////////////////////////////////////////////////////////

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

	$(".calendarModal__update").on("click", function() { // 일정 수정 모달 띄우기 (기존 내용 불러오기)
		$.modal.close();
		
		$.ajax({
			url:"/schedule/selectById",
			data:{id:$("#eventId").val()},
			type:"post"
		}).done(function(resp){
			
		});
		
		
		let sche = calendar.getEventById($("#eventId").val());
		console.log(sche);

		$(".calendarModal__save").css("display", "none");
		$(".calendarModal__updateSave").css("display", "block");
		$(".modalName").text("일정 수정");

		let startDate, startTime, endDate, endTime;

		if (sche._instance == null) { // 1일짜리 반복 이벤트
			let sdt = new Date(sche._def.extendedProps.startDateTime);

			startDate = sdt.toISOString().slice(0, 10);
			startTime = sdt.toISOString().slice(11, 16);
			sdt.setDate(sdt.getDate() + 1);
			endDate = sdt.toISOString().slice(0, 10);
			endTime = sche._def.extendedProps.endDateTime;
		} else { // 그외
			startDate = sche._instance.range.start.toISOString().slice(0, 10);
			startTime = sche._instance.range.start.toISOString().slice(11, 16);
			endDate = sche._instance.range.end.toISOString().slice(0, 10);
			endTime = sche._instance.range.end.toISOString().slice(11, 16);
		}


		$(".calendarModal__calNameList").val(sche._def.extendedProps.calNameVal);

		$(".insertSchedule__content").html(sche._def.extendedProps.content);
		$(".insertSchedule__title").val(sche._def.title);

		$(".insertSchedule__startDate").val(startDate);
		$(".insertSchedule__startTime").val(startTime);

		$(".insertSchedule__endDate").val(endDate);
		$(".insertSchedule__endTime").val(endTime);
		$(".insertSchedule__allDay").prop("checked", sche._def.allDay).trigger("change");
		$(".insertSchedule__repeat").prop("checked", sche._def.extendedProps.repeat).trigger("change");
		$(".frequency__when").val(sche.extendedProps.frequency__when).prop("checked", true).trigger("change");
		if (!(sche.extendedProps.repeatChk === undefined)) {
			if (sche._instance == null) { // 반복 일정
				let repeatChkArr = sche._def.recurringDef.typeData.rruleSet._rrule[0].options.byweekday;
				if (repeatChkArr.length > 1) {
					repeatChkArr.forEach(e => {
						$($("input[type='checkbox'][name='week']")[e]).prop("checked", true);
					});
				} else if (repeatChkArr.length == 1) {
					$($("input[type='checkbox'][name='week']")[repeatChkArr[0]]).prop("checked", true);
				}
			} else { // 1개만 선택했을 때
				$($("input[type='checkbox'][name='week']")[parseInt(sche.extendedProps.repeatChk)]).prop("checked", true);
			}
		}

		if (!(sche.extendedProps.count === undefined)) {
			$('.range__count').val(parseInt(sche.extendedProps.count)).trigger("keydown");
		}
		if (!(sche.extendedProps.until === undefined)) {
			$(".range__endDate").val(sche.extendedProps.until).trigger("change");
		}


		$(".scheduleInsertModal").modal({
			showClose: false
		});

	});

	$(".calendarModal__updateSave").on("click", function() { // 일정 수정 내용 저장
		// 날짜 데이터에 시간 데이터 추가
	
		
		let startDate = new Date($(".insertSchedule__startDate").val());
		startDate.setHours((parseInt($(".insertSchedule__startTime").val().slice(0, 2)) + 9), $(".insertSchedule__startTime").val().slice(3, 5)); // +9 하는 이유는 한국 시간으로 변경하기 위함.
		startDate = startDate.toISOString().slice(0, 16);

		let endDate = new Date($(".insertSchedule__endDate").val());
		endDate.setHours((parseInt($(".insertSchedule__endTime").val().slice(0, 2)) + 9), $(".insertSchedule__endTime").val().slice(3, 5));
		endDate = endDate.toISOString().slice(0, 16);


		let sche = calendar.getEventById($("#eventId").val()); // 번호 불러올 방법에 대해서 잘 생각해보기... 
		sche.setProp('title', $(".insertSchedule__title").val());
		sche.setDates(startDate, endDate, $(".insertSchedule__allDay").is(":checked"));
		sche.setExtendedProp('content', $(".insertSchedule__content").html());
		sche.setExtendedProp('calNameVal', $(".calendarModal__calNameList option:selected").val());
		/*sche.setExtendedProp('calName', $(".calendarModal__calNameList option:selected").html());*/

		$.modal.close();
	});

	calendar.render();
});



// 그 외 function -> btn click event etc...
$(document).ready(function() {
	$(".calendarAdd").on("click", function(e) { // 캘린더 추가
		$(".calendarInsertModal").modal({
			fadeDuration: 300,
			showClose: false
		});
		$(".calendarModal__cancel").on("click", $.modal.close);
	});


	$(".insertSchedule__allDay").on("change", function() { // 일정 등록 시 종일 선택하면 시간 입력 불가
		if ($(this).is(":checked")) {
			$("input[type=time]").attr("disabled", true);
		} else {
			$("input[type=time]").removeAttr("disabled");
		}
	});

	$(".calendarModal__cancel").on("click", $.modal.close); // 등록 취소

	$(".calendarModal__close").on("click", $.modal.close); // 모달창 닫기

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
		console.log($(this).val());
		console.log($(".insertSchedule__endDate").val());
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
})