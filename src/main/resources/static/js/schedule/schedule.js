// full calendar 관련 main function
/*let eventCnt = 0; // 나중에 삭제하고 auto_increment 값 불러오ㅏ 넣어주기
let groupCnt = 0; // 마찬가지 반복이벤트 아이디
*/
$(document).ready(function(){
	
})

let eventsLoaded = false;

document.addEventListener('DOMContentLoaded', function() {
	function modalInitail(type,datas) {
		$(".modalName").text("일정 추가");
		$(".insertSchedule__title").val("");
		$(".insertSchedule__startTime").val("09:00");
		$(".insertSchedule__endDate").val("");
		$(".insertSchedule__endTime").val("18:00");
		$(".insertSchedule__content").html("");

		let allDayState;
		if(type=="insert"){
			if (datas.view.type == "dayGridMonth") {allDayState =false;} 
			else {allDayState = datas.allDay;}
		}else{
			if(datas._context.viewApi.types == "dayGridMonth")allDayState = false;
			else allDayState = datas.allDay
		}
		$(".insertSchedule__allDay").prop("checked", allDayState).trigger("change");

		$(".insertSchedule__repeat").prop("checked", false).trigger("change");
		$(".calendarModal__save").css("display", "block");
		$(".calendarModal__updateSave").css("display", "none");
		$(".range__count").val(1);
		$("input[type='checkbox'][name='week']").prop("checked", false);
		
		$.ajax({
			url:"/schedule/calendarByEmpId",
			async:false
		}).done(function(resp){
			$(".calendarModal__calNameList *").remove();
			for(let i=0; i<resp.length; i++){
				$(".calendarModal__calNameList").append($("<option>").val(resp[i].id).text(resp[i].name).attr("color",resp[i].color));
			}	
			$(".calendarModal__calNameList:first-child").prop("selected",true);
		});
	}
	
	let calendarEl = document.getElementById('calendar');

	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		height: true,
		googleCalendarApiKey: "AIzaSyAStWVnOxLheNeBURhUh6-xC-jitc60FcE", // 구글 캘린더 api key
		eventSources: [{ // 구글 캘린더를 이용해 한국 공휴일 불러오기
			googleCalendarId: "ko.south_korea.official#holiday@group.v.calendar.google.com",
			className: "koHolidays",
			color: "white"
		}
		,{events:function(info, successCallback){
			if(!eventsLoaded){
				let eventDatas = [];
				$.ajax({
					url:"/schedule/selectAll",
					async:false
				}).done(function(resp){
					
					for(let i=0; i<resp.length; i++){
						
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
						
						
						if(resp[i].recurring_id == 0){
							eventData.start = resp[i].start_date; // 일정 시작 일자
							eventData.end = resp[i].end_date; // 일정 종료 일자
							
						}
						else{
							eventData.repeat =true;
							let endKey = resp[i].endKey;
							let frequency_whenOption = resp[i].frequency_whenOption == "weekDay" ? "weekly" : resp[i].frequency_whenOption;
							let chkWeekDayList = resp[i].selectWeeks.split(",");
							if(chkWeekDayList.length>=1)
								chkWeekDayList = chkWeekDayList.map((e)=>{ return parseInt(e); });
								// resp[i].frequency_whenOption == "weekDay" ? [0, 1, 2, 3, 4] :
							let endValue = resp[i].endValue;
							if (endKey == "count" &&  frequency_whenOption == "weekly") endValue *= chkWeekDayList.length; 
							let rrule = {
								freq:  frequency_whenOption,
								[endKey]: endValue,
								interval: resp[i].intervalCnt,
								dtstart: resp[i].start_date
							}
							if(frequency_whenOption == "weekly"){
								rrule.byweekday = chkWeekDayList;
							}
							eventData.rrule =rrule;
							eventData.startDateTime =resp[i].start_date; 
							eventData.endDateTime = resp[i].end_date.slice(11,16);
							eventData.groupId = resp[i].recurring_id;
							eventData.frequency__when = resp[i].frequency_whenOption;
							eventData[`${endKey}`] =resp[i].endValue;
						}
						eventDatas.push(eventData);
					}
					successCallback(eventDatas);
					eventsLoaded = true;
				});
					
			}
		
		}}],
		
		
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
			modalInitail("insert",e);
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
					selectDate.setDate(selectDate.getDate());
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
			modalInitail("insert",e);
			$(".insertSchedule__startDate").val(e.startStr); // 시작 일자 넣어주기

			if (e.allDay) {
				if (e.view.type == "dayGridMonth") {
					let date = new Date(e.endStr);
					date.setDate(new Date(e.endStr).getDate() - 1);
					$(".insertSchedule__endDate").val(date.toISOString().slice(0, 10)); // 종료 일자 넣어주기
					$(".range__endDate").val($(".insertSchedule__endDate").val());
				} else { 
					let date = new Date(e.endStr);
					date.setDate(new Date(e.endStr).getDate() - 1);
					$(".insertSchedule__endDate").val(date.toISOString().slice(0, 10)); // 종료 일자 넣어주기
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

			if (!$(e.el).hasClass("koHolidays")) {
				let { event: { _def: datas, _instance: { range } } } = e;
				//console.log(e); // e.range 이용해서 각 반복 이벤트 중 특정 이벤트만 설정도 가능..
				// 특정 이벤트만 삭제하는 건 exdate 이용하면 될듯
				
				$.ajax({
					url:"/schedule/selectById",
					data:{id:e.event._def.publicId},
					type:"post",
					async:false
				}).done(function(resp){
					console.log(resp);
					$(".calendarModal__calName").text(resp.calendar_name);
					$(".calendarModal__scheTitle").text(resp.title);
					let regDate = resp.reg_date.slice(0,10) + " " +resp.reg_date.slice(11,16);
					$(".calendarModal__scheReg").text(`${resp.name} (${regDate})`);
					$("#eventId").val(resp.id);
					$(".calendarModal__repeatYN").text(resp.recurring_id != 0 ? "반복 있음" : "반복 없음");
					$(".calendarModal__scheContent").text(resp.content);
					
					
					
					// 시간 어떻게 해야할 지 고민이 크다...  디비에서 불러올 수 없는 내용인데
					let startDate = range.start.toISOString().slice(0, 10) + " " + range.start.toISOString().slice(11, 16);
					let endDate = range.end.toISOString().slice(0, 10) + " " + range.end.toISOString().slice(11, 16);
			
					$(".calendarModal__scheDate").text(`${startDate} ~ ${endDate}`)
					if(resp.recurring_id != 0 && !resp.all_day){ //반복 이벤트이면서 시간이 지정되어있으면
						let endDate = range.end.toISOString().slice(0, 10) + " " + resp.endTime;
						$(".calendarModal__scheDate").text(`${startDate} ~ ${endDate}`);
					}
				});
				

				$(".scheduleViewModal").modal({
					showClose: false
				});


				$(".calendarModal__delete").on("click", () => { 
					calendar.getEventById($("#eventId").val()).remove();
					$.ajax({
						url:"/schedule/delete",
						data:{id:$("#eventId").val()},
						type:"post"
					}).done(function(resp){}); // resp가 1이면 정살적으로 삭제 
					$.modal.close(); 
					
				}); // 일정 삭제
			} else {
				e.jsEvent.preventDefault(); // 구글 일정 클릭 시 구글 캘린더 페이지로 이동하는 이벤트 방지
			}
		},

		dayCellContent: function(e) { // 달력에서 일 이라는 글자 제거
			return e.dayNumberText.replace("일", "");
		},
		datesSet: function (info) {
	        $.ajax({
				url:"/schedule/selectAll",
				async:false
			}).done(function(resp){
				calendar.removeAllEvents();
				let eventDatas = [];
	           for(let i=0; i<resp.length; i++){
					
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
					
					
					if(resp[i].recurring_id == 0){
						eventData.start = resp[i].start_date; // 일정 시작 일자
						eventData.end = resp[i].end_date; // 일정 종료 일자
						
					}
					else{
						eventData.repeat =true;
						let endKey = resp[i].endKey;
						let frequency_whenOption = resp[i].frequency_whenOption == "weekDay" ? "weekly" : resp[i].frequency_whenOption;
						let chkWeekDayList = resp[i].selectWeeks.split(",");
						if(chkWeekDayList.length>=1)
							chkWeekDayList = chkWeekDayList.map((e)=>{ return parseInt(e); });
							// resp[i].frequency_whenOption == "weekDay" ? [0, 1, 2, 3, 4] :
						let endValue = resp[i].endValue;
						if (endKey == "count" &&  frequency_whenOption == "weekly") endValue *= chkWeekDayList.length; 
						let rrule = {
							freq:  frequency_whenOption,
							[endKey]: endValue,
							interval: resp[i].intervalCnt,
							dtstart: resp[i].start_date
						}
						if(frequency_whenOption == "weekly"){
							rrule.byweekday = chkWeekDayList;
						}
						eventData.rrule =rrule;
						eventData.startDateTime =resp[i].start_date; 
						eventData.endDateTime = resp[i].end_date.slice(11,16);
						eventData.groupId = resp[i].recurring_id;
						eventData.frequency__when = resp[i].frequency_whenOption;
						eventData[`${endKey}`] =resp[i].endValue;
					}
					eventDatas.push(eventData);
				}
				calendar.addEventSource(eventDatas);
	            calendar.render();
			});
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
			allDay: $(".insertSchedule__allDay").is(":checked"), // 종일 여부
			color:  $(".calendarModal__calNameList option:selected").attr("color"),
			content: $(".insertSchedule__content").html(),
			calNameVal: $(".calendarModal__calNameList option:selected").val(),
			/*calName: $(".calendarModal__calNameList option:selected").html(),*/
			/*registor: "등록자", // 나중에 ajax로 insert 쿼리 날린 후 값 가져오기.. (session id 값...)     */
			reg_date: reg_date.toISOString().slice(0,16), 
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
			let during = Math.ceil((new Date($(".insertSchedule__endDate").val()).getTime() - new Date($(".insertSchedule__startDate").val()).getTime()) / (1000 * 60 * 60 * 24));

			eventData.frequency__when = $(".frequency__when option:selected").val();
			//eventData.repeatChk = $("input[type='checkbox'][name='week']:checked").val();
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

				if(ed.allDay){
					ed.startDateTime = startDate.toISOString().slice(0, 11)+"00:00";
					ed.endDateTime = "00:00";
					endDateStr = endDate.toISOString().slice(0,11)+"00:00";
				}
				if (frequency__whenOption == "weekly") {
					ed.rrule.byweekday = chkWeekDayList; // 선택한 요일
				}

				$.ajax({
					url:"/schedule/insertReccuring",
					data: {
					    during : during,
					    frequency_whenOption : eventData.frequency__when,
					    endKey : endKey,
					    endValue : eventData[`${endKey}`],
					    intervalCnt : parseInt(rrule.interval),
					    startTime : ed.startDateTime, 
					    endTime : ed.endDateTime,
					    selectWeeks : chkWeekDayList.toString(),
					   	calendar_id : parseInt(eventData.calNameVal),
					    title : ed.title,
					    content : ed.content,
					  	start_date : ed.startDateTime,
					    end_date : endDateStr,
					    reg_date : ed.reg_date,
					    all_day : ed.allDay
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
			}

		} else { // 반복 일정 아니면
			eventData.start = startDateStr; // 일정 시작 일자
			eventData.end = endDateStr; // 일정 종료 일자
			
			if(eventData.allDay){
				eventData.start = startDateStr.slice(0,11)+"00:00";
				eventData.end = endDateStr.slice(0,11)+"00:00";
			}
			
			$.ajax({
				url:"/schedule/insert",
				data: {
				    calendar_id : parseInt(eventData.calNameVal),
				    title : eventData.title,
				    content : eventData.content,
				  	start_date : eventData.start,
				    end_date : eventData.end,
				    reg_date : eventData.reg_date,
				    all_day : eventData.allDay
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
		dbMap.set("all_day",ed.allDay);
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
		
		console.log(calendar.getEventById($("#eventId").val()));
		
		$.ajax({
			url:"/schedule/selectById",
			data:{id:$("#eventId").val()},
			type:"post",
			async:false
		}).done(function(resp){
			console.log(resp);
			
			modalInitail("modify",calendar.getEventById($("#eventId").val()));

			let startDate, startTime, endDate, endTime;
			startDate = resp.start_date.slice(0,10);
			startTime = resp.start_date.slice(11,16);
			endDate = resp.end_date.slice(0,10);
			endTime = resp.end_date.slice(11,16);

	
			console.log($(".calendarModal__calNameList").val(resp.calendar_id));
			$(".calendarModal__calNameList").val(resp.calendar_id).prop("selected",true);
			console.log($(".calendarModal__calNameList option:selected").val());

			$(".insertSchedule__content").html(resp.content);
			$(".insertSchedule__title").val(resp.title);
	
			$(".insertSchedule__startDate").val(startDate);
			$(".insertSchedule__startTime").val(startTime);
	
			$(".insertSchedule__endDate").val(endDate);
			$(".insertSchedule__endTime").val(endTime);
			$(".range__endDate").val($(".insertSchedule__endDate").val());
			
			$(".insertSchedule__allDay").prop("checked", resp.all_day).trigger("change");
			$(".insertSchedule__repeat").prop("checked", resp.recurring_id != 0 ? true : false).trigger("change");
			if(resp.recurring_id!=0){
				let frequency_whenOption = resp.frequency_whenOption == "weekDay" ? "weekly" : resp.frequency_whenOption;
				$(".frequency__when").val(frequency_whenOption).prop("checked", true).trigger("change");
				$(".frequency__every").val(resp.intervalCnt).prop("checked", true).trigger("change");
				
				if(frequency_whenOption == "weekly"){
					resp.selectWeeks.split(",").forEach((e)=>{ $($("input[type='checkbox'][name='week']")[parseInt(e)]).prop("checked",true);});
				}
	
				if(resp.endKey == "count"){
					$('.range__count').val(parseInt(resp.endValue)).trigger("keydown");
				}else{
					$(".range__endDate").val(resp.endValue).trigger("change");
				}				
			
			}
		
				
			
		});
		
		$(".calendarModal__save").css("display", "none");
		$(".calendarModal__updateSave").css("display", "block");
		$(".modalName").text("일정 수정");	
		console.log($(".calendarModal__calNameList option:selected").val());

		$(".scheduleInsertModal").modal({
			showClose: false
		});
		
		console.log($(".calendarModal__calNameList option:selected").val());

	});

	$(".calendarModal__updateSave").on("click", function() { // 일정 수정 내용 저장
	
	
		// 날짜 데이터에 시간 데이터 추가
		let startDate = new Date($(".insertSchedule__startDate").val());
		startDate.setHours((parseInt($(".insertSchedule__startTime").val().slice(0, 2)) + 9), $(".insertSchedule__startTime").val().slice(3, 5)); // +9 하는 이유는 한국 시간으로 변경하기 위함.
		startDate = startDate.toISOString().slice(0, 16);

		let endDate = new Date($(".insertSchedule__endDate").val());
		endDate.setHours((parseInt($(".insertSchedule__endTime").val().slice(0, 2)) + 9), $(".insertSchedule__endTime").val().slice(3, 5));
		endDate = endDate.toISOString().slice(0, 16);
		
		let sche = calendar.getEventById($("#eventId").val());
		if($(".insertSchedule__allDay").is(":checked")){
			startDate = startDate.slice(0, 11)+"00:00";
			endDateTime = "00:00";
			if(sche._def.allDay){
				endDate = endDate.slice(0, 11)+"00:00";
			}else{
				endDate = new Date($(".insertSchedule__endDate").val());
				endDate.setDate(endDate.getDate()+1); // 하루종일이니 다음 날 12시로 설정
				endDate = endDate.toISOString().slice(0, 11)+"00:00";
			}
		}
		
		
		
		sche.setProp('title', $(".insertSchedule__title").val());
		sche.setDates(startDate, endDate, $(".insertSchedule__allDay").is(":checked"));
		sche.setExtendedProp('content', $(".insertSchedule__content").html());
		sche.setExtendedProp('calNameVal', $(".calendarModal__calNameList option:selected").val());
		sche.setProp("color",$(".calendarModal__calNameList option:selected").attr("color"));
		
	
		if($(".insertSchedule__repeat").is(":checked")){
			let during = Math.ceil((new Date($(".insertSchedule__endDate").val()).getTime() - new Date($(".insertSchedule__startDate").val()).getTime()) / (1000 * 60 * 60 * 24));
			
			if ($(".insertSchedule__allDay").is(":checked") && during == 1 || !$(".insertSchedule__allDay").is(":checked") && during>0) { // 반복 이벤트 X 
				Swal.fire({
					icon: "error",
					text: "반복이벤트는 2일 이상 설정할 수 없습니다."
				});
				return false;
			}else{
				
				let frequency__whenOption = $(".frequency__when option:selected").val() == "weekDay" ? "weekly" : $(".frequency__when option:selected").val();
				let chkWeekDayList = $(".frequency__when option:selected").val() == "weekDay" ? [0, 1, 2, 3, 4] : $("input[type=checkbox][name='week']:checked").map((i, e) => { return parseInt($(e).val()); }).toArray();
	
				let endKey = $(":radio[name='period']:checked").val();
				let endValue = $(":radio[name='period']:checked").next().children().val();
				if (endKey == "count" && frequency__whenOption == "weekly") endValue *= chkWeekDayList.length; // 월, 수 고르면 월 수 월 로 끝남. -> 월, 수 *3이 되도록
	
				sche.setExtendedProp('frequency__when',$(".frequency__when option:selected").val());
				sche.setExtendedProp(`${endKey}`,$(":radio[name='period']:checked").next().children().val());
				sche.setExtendedProp('chkWeekDayList', chkWeekDayList);
				sche.setExtendedProp("repeat",true);
				let rrule = {
					freq: frequency__whenOption, // daily, weekly, monthly, yearly
					[endKey]: endValue,  // count: $(".range__count").val() or until: endDate
					interval: parseInt($(".frequency__every option:selected").val()), //ex 3이면 3주마다
					dtstart: startDate
				}
				
				let startDateTime;
				let endDateTime;
				if($(".insertSchedule__allDay").is(":checked")){
					endDate = endDate.slice(0,11)+"00:00";
					sche.setExtendedProp('startDateTime',startDate.slice(0, 11)+"00:00");
					sche.setExtendedProp('endDateTime',"00:00");
					startDateTime = startDate.slice(0, 11)+"00:00";
					endDateTime = "00:00";
				}else{
					sche.setExtendedProp('startDateTime', startDate.slice(0, 16));
					sche.setExtendedProp('endDateTime', endDate.slice(11, 16));
					startDateTime = startDate.slice(0, 16);
					endDateTime = endDate.slice(11, 16);
				}
				if (frequency__whenOption == "weekly") {
					rrule.byweekday = chkWeekDayList; // 선택한 요일
				}
				
				sche.setProp("rrule",rrule);
				
				$.ajax({
					url:"/schedule/recurringScheduleUpdate",
					type:"post",
					data:{
						id :$("#eventId").val(),
						during : during,
					    frequency_whenOption : $(".frequency__when option:selected").val(),
					    endKey : endKey,
					    endValue : endValue,
					    intervalCnt : parseInt(rrule.interval),
					    endTime : endDateTime,
					    startTime: startDateTime,
					    selectWeeks : chkWeekDayList.toString(),
					   	calendar_id : parseInt($(".calendarModal__calNameList option:selected").val()),
					    title : $(".insertSchedule__title").val(),
					    content : $(".insertSchedule__content").html(),
					  	start_date : startDateTime,
					    end_date : endDate,
					    all_day : $(".insertSchedule__allDay").is(":checked"),
					    recurring_id : sche.groupId
					}
				}).done(function(resp){
					console.log("반복 변경 성공~~~");
				});
				
				
			}
			
		}else{
			$.ajax({
				url:"/schedule/scheduleUpdate",
				type:"post",
				data:{
					id : $("#eventId").val(),
					calendar_id : parseInt( $(".calendarModal__calNameList option:selected").val()),
					title : $(".insertSchedule__title").val(),
					content : $(".insertSchedule__content").html(),
					start_date : startDate,
					end_date : endDate,
					all_day : $(".insertSchedule__allDay").is(":checked"),
					recurring_id : sche.groupId
				}
			}).done(function(){
				console.log("업데이트 성공!");
			});
		}
	

		
		
	

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