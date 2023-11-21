let eventsLoaded = 0;

// 일정 등록 모달창 초기화 함수
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
		url:"/schedule/selectCalendarByEmpId",
		async:false
	}).done(function(resp){
		$(".calendarModal__calNameList *").remove();
		for(let i=0; i<resp.length; i++){
			$(".calendarModal__calNameList").append($("<option>").val(resp[i].id).text(resp[i].name).attr("color",resp[i].color));
		}	
		$(".calendarModal__calNameList:first-child").prop("selected",true);
	});
}


// 네비 목록 불러오기 (캘린더)
function getNavi(){
	$.ajax({
		url:"/schedule/selectCalendarByEmpId",
		async:false
	}).done(function(calendarList){
		let selectedNavi = $(".selectNavi").map((i,e)=>{
			return parseInt($(e).attr("data-id"))
		}).toArray();
		
		$(".customMenu").remove();
		$(".trashMenu").remove();
		let naviConp__calColor = $("<div>").attr("class", "naviConp__calColor");
		for (let i = 0; i < calendarList.length; i++) {
			let naviConp__title = $("<div>").attr("class", "naviConp__title naviConp__titleMini").text(calendarList[i].name)
			let toggleInner = $("<div>").attr("class", "naviConp toggleInner customMenu").attr("data-id",calendarList[i].id);
			let editNavi = $("<div>").attr("class","editNavi").html('<i class="fa-solid fa-pen"></i>').attr("data-id",calendarList[i].id).attr("data-trash",calendarList[i].trash).attr("data-is_share",calendarList[i].is_share);
			
			if(calendarList[i].trash == 1){ //휴지통
				toggleInner.removeClass("customMenu").addClass("trashMenu");
				toggleInner.append(naviConp__calColor.clone().css("background-color",calendarList[i].color)).append(naviConp__title).attr("data-title", calendarList[i].name).append(editNavi);
				$(".naviBar__trashCalendar").append(toggleInner);
			}else{
				toggleInner.append(naviConp__calColor.clone().attr("data-color",calendarList[i].color)).append(naviConp__title).attr("data-title", calendarList[i].name).append(editNavi);
				if (calendarList[i].is_share) { // 공유주소록
					$(".naviBar__sharedCalendar").append(toggleInner);
				} else { // 개인주소록
					$(".naviBar__personalCalendar").append(toggleInner);
				}
			}
			
			if(selectedNavi.includes(calendarList[i].id))
				toggleInner.trigger("click");
			
		}
		
		if($(".naviBar__sharedCalendar").find(".toggleMenu").attr("toggleView") == "false"){
			$(".naviBar__sharedCalendar .toggleInner").css("display","none");
		}
		if($(".naviBar__personalCalendar").find(".toggleMenu").attr("toggleView") == "false"){
			$(".naviBar__personalCalendar .toggleInner").css("display","none");
		}
		if($(".naviBar__trashCalendar").find(".toggleMenu").attr("toggleView") == "false"){
			$(".naviBar__trashCalendar .toggleInner").css("display","none");
		}
	});
}

document.addEventListener('DOMContentLoaded', function() {
	
	getNavi();
	let calendarEl = document.getElementById('calendar');

	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'dayGridMonth',
		height: true,
		googleCalendarApiKey: "AIzaSyAStWVnOxLheNeBURhUh6-xC-jitc60FcE", // 구글 캘린더 api key
		eventSources: [{ // 구글 캘린더를 이용해 한국 공휴일 불러오기
			googleCalendarId: "ko.south_korea.official#holiday@group.v.calendar.google.com",
			className: "koHolidays",
			color: "white"
		}],
		
		
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
					console.log("요기");
					console.log(resp);
					$(".calendarModal__calName").text(resp.calendar_name);
					$(".calendarModal__scheTitle").text(resp.title);
					let regDate = resp.reg_date.slice(0,10) + " " +resp.reg_date.slice(11,16);
					$(".calendarModal__scheReg").text(`${resp.name} (${regDate})`);
					$("#eventId").val(resp.id);
					$(".calendarModal__repeatYN").text(resp.recurring_id != 0 ? "반복 있음" : "반복 없음");
					$(".calendarModal__scheContent").text(resp.content);
					
					
					// 시간 어떻게 해야할 지 고민이 크다...  디비에서 불러올 수 없는 내용인데
					
					let startDate = new Date(resp.start_date);
					startDate.setHours(startDate.getHours()+9);
					let endDate = new Date(resp.end_date);
					endDate.setHours(endDate.getHours()+9);
					
					
					startDate = startDate.toISOString().slice(0, 10) + " " + startDate.toISOString().slice(11, 16);
					endDate = endDate.toISOString().slice(0, 10) + " " +  endDate.toISOString().slice(11, 16);
			
					$(".calendarModal__scheDate").text(`${startDate} ~ ${endDate}`);
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
		datesSet: function () {
			if(!eventsLoaded){
				eventsLoaded = 1;
				reloadEvent(eventsLoaded);
			}else{
				reloadEvent();
			}
		  
	    }
	});


	///////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	// 일정 생성 (입력한 일정 정보를 이용해 달력 및 DB에 일정 삽입)
	function generateEvent(type) {

		// 일정 시작일
		let eventStartDate = new Date($(".insertSchedule__startDate").val());
		eventStartDate.setHours( parseInt($(".insertSchedule__startTime").val().slice(0, 2)),  parseInt($(".insertSchedule__startTime").val().slice(3, 5)));
		
		// 일정 종료일
		let eventEndDate = new Date($(".insertSchedule__endDate").val());
		eventEndDate.setHours( parseInt($(".insertSchedule__endTime").val().slice(0, 2)),  parseInt($(".insertSchedule__endTime").val().slice(3, 5)));
				
		// 이벤트 등록일자
		let reg_date = new Date();
		reg_date.setHours(reg_date.getHours()+9);
		
		
		
		// 일정 (이벤트) 정보 객체
		let eventData = {
			title: $(".insertSchedule__title").val(), // 일정 제목
			allDay: $(".insertSchedule__allDay").is(":checked"), // 종일 여부
			color:  $(".calendarModal__calNameList option:selected").attr("color"), // 캘린더 색상
			content: $(".insertSchedule__content").html(), // 일정 내용
			calNameVal: $(".calendarModal__calNameList option:selected").val(), // 캘린더 번호
			reg_date: reg_date, // 등록일자
			repeat: $(".insertSchedule__repeat").is(":checked"), // 반복 이벤트 여부
			start:eventStartDate,
			end: eventEndDate
		}
	
		// 일정 (이벤트) 정보 배열
		let events = [];
		
		if($(".insertSchedule__repeat").is(":checked")){ // 반복 일정이면
		
			// 반복 종류 (일, 주(선택한 요일), 주(월~금), 월, 년) -> 주는 전부 weekly로 통일
			let frequency__whenOption = $(".frequency__when option:selected").val() == "weekDay" ? "weekly" : $(".frequency__when option:selected").val();
			
			// 반복 종류 "주" 선택했을 경우, 반복하는 요일 
			// weekDay 선택했을 경우 월화수목금으로 설정, 그 외는 선택한 요일 저장 (숫자 배열)
			let chkWeekDayList = $(".frequency__when option:selected").val() == "weekDay" ? [0, 1, 2, 3, 4] : $("input[type=checkbox][name='week']:checked").map((i, e) => { return parseInt($(e).val()); }).toArray();
			
			// 종료 옵션: 반복 횟수 or 반복 종료일
			// count or until
			let endKey = $(":radio[name='period']:checked").val(); 
			let endValue = $(":radio[name='period']:checked").next().children().val();
			
			// 반복 횟수로 종료 시기를 지정할 때, 
			// "월, 수" 고르면 "월 수 월" 로 끝남. -> "월, 수 *3" 이 되도록 고른 개수만큼 곱해줌
			if (endKey == "count" && frequency__whenOption == "weekly") endValue *= chkWeekDayList.length; 
			
			
			// 몇일 짜리 이벤트인지 구하기 (ex. 7일 ~ 8일 이벤트이면 1, 7일~7일 이벤트이면 0, 종일이어도 00:00 ~ 23:59 이므로 0임)
			// 날짜를 구하는 것이므로 시간은 낮 12시로 통일하여 계산함. (ex. 8일 21:00 ~ 10일 06:00로 잡았을 때, 3을 기대하지만 결과는 2가 나옴. 48시간이 되지 않기 때문.)
			let duringStart = new Date($(".insertSchedule__startDate").val());
			duringStart.setHours("11","30");
			let duringEnd = new Date($(".insertSchedule__endDate").val());
			duringEnd.setHours("12","00");
			let during = Math.ceil((duringEnd.getTime() - duringStart.getTime()) / (1000 * 60 * 60 * 24));
			
			
			let dtstart = new Date(eventStartDate);
			dtstart.setHours(dtstart.getHours()+9);
			
			if(during == 1){ // 1일 짜리 반복 이벤트
				
				let ed = {...eventData};
				let rrule = {
					freq : frequency__whenOption, // daily, weekly, monthly, yearly
					[endKey]: endValue,  // count: $(".range__count").val() or until: endDate
					interval: parseInt($(".frequency__every option:selected").val()), //ex 3이면 3주마다
					dtstart: dtstart
				}
				ed.rrule = rrule;
				
				// 주마다 반복일 경우 요일정보 저장
				if(frequency__whenOption == "weekly"){ 
					ed.rrule.byweekday = chkWeekDayList;
				}

				if(type == "insert"){
					$.ajax({
						url:"/schedule/insertReccuring",
						data: {
							// 기본 이벤트 정보
							calendar_id : parseInt(ed.calNameVal),
						    title : ed.title,
						    content : ed.content,
						  	start_date : eventStartDate.toISOString(),
						    end_date : eventEndDate.toISOString(),
						    reg_date : ed.reg_date.toISOString(),
						    all_day : ed.allDay,
	
							// 반복 이벤트 정보
						    during : during, // 기간
						    frequency_whenOption : frequency__whenOption, // 반복 종류 (일, 주, 월, 년)
						    endKey : endKey, // 종료 옵션 key (until, count)
						    endValue : $(":radio[name='period']:checked").next().children().val(), // 종료 옵션 value
						    intervalCnt : parseInt(rrule.interval), // 반복 횟수 (ex. 3주 마다)
						    selectWeeks : chkWeekDayList.toString()
						},
						type:"post",
						async:false
					}).done(function(resp){
						ed.id = resp.id;
						ed.registor = resp.emp_id;
						ed.groupId = resp.recurring_id;
						events.push(ed);
					});	
				}
				


			// 1일 이상의 반복 이벤트 일 때 (반복 연속이벤트)
			} else {
				Swal.fire({
					icon: "error",
					text: "반복이벤트는 2일 이상 설정할 수 없습니다."
				});
				return false;
			}
	
				
		} else { // 일반 이벤트 이면 (1일 혹은 연속이벤트
			if(type == "insert"){
				$.ajax({
					url:"/schedule/insert",
					data:{
						calendar_id : parseInt(eventData.calNameVal),
					    title : eventData.title,
					    content : eventData.content,
					  	start_date : eventStartDate.toISOString(),
					    end_date : eventEndDate.toISOString(),
					    reg_date : eventData.reg_date.toISOString(),
					    all_day : eventData.allDay
					},
					type:"post",
					async:false
				}).done(function(resp){
					eventData.id = resp.id;
					eventData.registor = resp.emp_id;
					events.push(eventData);
				});
			}
		}
			
		// 현재 등록한 이벤트의 캘린더가 활성화된 캘린더이면 캘린더에 이벤트 추가, 아니면 DB에만 추가
		let selectCal = $(".selectNavi").map((i,e)=>{
			return parseInt($(e).attr("data-id"));
		}).toArray();	
		
		if(selectCal.includes(parseInt(eventData.calNameVal))){
			return events;
		}else{
			return [];
		}	
	}
	
	


	// 일정 등록 모달 저장 누를 시 일정 등록
	$(".calendarModal__save").on("click", function() { 
		if ($(".insertSchedule__startDate").val() > $(".insertSchedule__endDate").val()) {
			Swal.fire({
				icon: "error",
				text: "일정 시작일보다 일정 종료일이 앞설 수 없습니다."
			});
			$(".insertSchedule__endDate").val($(".insertSchedule__startDate").val());
			return false;
		}
		
		if($(".insertSchedule__title").val()==""){
			Swal.fire({
				icon: "error",
				text: "일정 제목을 입력해주세요."
			});
			return;
		}
		
		if($(".insertSchedule__startDate").val()==""){
			Swal.fire({
				icon: "error",
				text: "시작 일자를 입력해주세요."
			});
			return;
		}		
		
		let events = generateEvent("insert");
		calendar.addEventSource(events);

		$.modal.close(); // 등록 후 모달 닫기
	});
	
	
	// 일정 수정 모달 띄우기 (클린한 이벤트에 대하여 DB 내용 불러오기)
	$(".calendarModal__update").on("click", function() { 
		$.modal.close();
		
		console.log(calendar.getEventById($("#eventId").val()));
		
		$.ajax({
			url:"/schedule/selectById",
			data:{id:$("#eventId").val()},
			type:"post",
			async:false
		}).done(function(resp){
			modalInitail("modify",calendar.getEventById($("#eventId").val()));
			
			let startDate = new Date(resp.start_date);
			startDate.setHours(startDate.getHours()+9);
			let endDate = new Date(resp.end_date);
			endDate.setHours(endDate.getHours()+9);
			
			
			startDateStr = startDate.toISOString().slice(0, 10)
			let startTime = startDate.toISOString().slice(11, 16);
			endDateStr = endDate.toISOString().slice(0, 10)
			let endTime = endDate.toISOString().slice(11, 16);


			$(".calendarModal__calNameList").val(resp.calendar_id).prop("selected",true);

			$(".insertSchedule__content").html(resp.content);
			$(".insertSchedule__title").val(resp.title);
	
			$(".insertSchedule__startDate").val(startDateStr);
			$(".insertSchedule__startTime").val(startTime);
	
			$(".insertSchedule__endDate").val(endDateStr);
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

		$(".scheduleInsertModal").modal({
			showClose: false
		});
		
		
		// 기존 이벤트 객체
		let sche = calendar.getEventById($("#eventId").val());
		console.log("sche");
		console.log(sche);
		
	});

	
	



	//------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// 일정 수정 내용 저장
	$(".calendarModal__updateSave").on("click", function() { 
		
		if ($(".insertSchedule__startDate").val() > $(".insertSchedule__endDate").val()) {
			Swal.fire({
				icon: "error",
				text: "일정 시작일보다 일정 종료일이 앞설 수 없습니다."
			});
			$(".insertSchedule__endDate").val($(".insertSchedule__startDate").val());
			return false;
		}
		
		if($(".insertSchedule__title").val()==""){
			Swal.fire({
				icon: "error",
				text: "일정 제목을 입력해주세요."
			});
			return;
		}
		
		if($(".insertSchedule__startDate").val()==""){
			Swal.fire({
				icon: "error",
				text: "시작 일자를 입력해주세요."
			});
			return;
		}		
		
		
		
		// 일정 시작일
		let eventStartDate = new Date($(".insertSchedule__startDate").val());
		eventStartDate.setHours( parseInt($(".insertSchedule__startTime").val().slice(0, 2)),  parseInt($(".insertSchedule__startTime").val().slice(3, 5)));
		
		// 일정 종료일
		let eventEndDate = new Date($(".insertSchedule__endDate").val());
		eventEndDate.setHours( parseInt($(".insertSchedule__endTime").val().slice(0, 2)),  parseInt($(".insertSchedule__endTime").val().slice(3, 5)));
				
		
		// 기존 이벤트 객체
		let sche = calendar.getEventById($("#eventId").val());
			
		
		// 반복 이벤트로 수정 (일반->반복, 반복->반복)
		if($(".insertSchedule__repeat").is(":checked")){

			// 몇일 짜리 이벤트인지 구하기 (ex. 7일 ~ 8일 이벤트이면 1, 7일~7일 이벤트이면 0, 종일이어도 00:00 ~ 23:59 이므로 0임)
			// 날짜를 구하는 것이므로 시간은 낮 12시로 통일하여 계산함. (ex. 8일 21:00 ~ 10일 06:00로 잡았을 때, 3을 기대하지만 결과는 2가 나옴. 48시간이 되지 않기 때문.)
			let duringStart = new Date($(".insertSchedule__startDate").val());
			duringStart.setHours("11","30");
			let duringEnd = new Date($(".insertSchedule__endDate").val());
			duringEnd.setHours("12","00");
			let during = Math.ceil((duringEnd.getTime() - duringStart.getTime()) / (1000 * 60 * 60 * 24));
			
			
			if (during > 1) {
				Swal.fire({
					icon: "error",
					text: "반복이벤트는 2일 이상 설정할 수 없습니다."
				});
				return false;
			}else{
				
				// 반복 종류 (일, 주(선택한 요일), 주(월~금), 월, 년) -> 주는 전부 weekly로 통일
				let frequency__whenOption = $(".frequency__when option:selected").val() == "weekDay" ? "weekly" : $(".frequency__when option:selected").val();
				
				// 반복 종류 "주" 선택했을 경우, 반복하는 요일 
				// weekDay 선택했을 경우 월화수목금으로 설정, 그 외는 선택한 요일 저장 (숫자 배열)
				let chkWeekDayList = $(".frequency__when option:selected").val() == "weekDay" ? [0, 1, 2, 3, 4] : $("input[type=checkbox][name='week']:checked").map((i, e) => { return parseInt($(e).val()); }).toArray();
				
				// 종료 옵션: 반복 횟수 or 반복 종료일
				// count or until
				let endKey = $(":radio[name='period']:checked").val(); 
				let endValue = $(":radio[name='period']:checked").next().children().val();
				
				// 반복 횟수로 종료 시기를 지정할 때, 
				// "월, 수" 고르면 "월 수 월" 로 끝남. -> "월, 수 *3" 이 되도록 고른 개수만큼 곱해줌
				if (endKey == "count" && frequency__whenOption == "weekly") endValue *= chkWeekDayList.length; 
			
				
				let dtstart = new Date(eventStartDate);
				dtstart.setHours(dtstart.getHours()+9);
			
				let rrule = {
					freq: frequency__whenOption, // daily, weekly, monthly, yearly
					[endKey]: endValue,  // count: $(".range__count").val() or until: endDate
					interval: parseInt($(".frequency__every option:selected").val()), //ex 3이면 3주마다
					dtstart: dtstart
				}
					
				if (frequency__whenOption == "weekly") {
					rrule.byweekday = chkWeekDayList; // 선택한 요일
				}

				$.ajax({
					url:"/schedule/recurringScheduleUpdate",
					type:"post",
					data:{
						// 기본 이벤트 정보
						id :$("#eventId").val(),
						calendar_id :parseInt($(".calendarModal__calNameList option:selected").val()),
					    title :  $(".insertSchedule__title").val(),
					    content : $(".insertSchedule__content").html(),			    
					  	start_date : eventStartDate.toISOString(),
					    end_date : eventEndDate.toISOString(),
					    all_day : $(".insertSchedule__allDay").is(":checked"),
					    
					    // 반복 이벤트 정보
					    recurring_id : sche.groupId===""?0:parseInt(sche.groupId,10),
						during : during,
					    frequency_whenOption : frequency__whenOption,
					    endKey : endKey,
					    endValue : $(":radio[name='period']:checked").next().children().val(),
					    intervalCnt : parseInt(rrule.interval),
						selectWeeks : chkWeekDayList.toString() 
					}
				}).done(function(resp){
					let selectCal = $(".selectNavi").map((i,e)=>{
						return parseInt($(e).attr("data-id"));
					}).toArray();
					

					calendar.getEventById($("#eventId").val()).remove();			
	
					if(selectCal.includes(parseInt( $(".calendarModal__calNameList option:selected").val()))){
						let newEventData = {
							id : resp.id,
							title: resp.title, // 일정 제목
							allDay: resp.all_day, // 종일 여부
							color:  $(".calendarModal__calNameList option:selected").attr("color"), // 캘린더 색상
							content: resp.content, // 일정 내용
							calNameVal: resp.calendar, // 캘린더 번호
							reg_date: new Date(sche._def.extendedProps.reg_date), // 등록일자
							repeat: true, // 반복 이벤트 여부
							start:eventStartDate,
							end: eventEndDate,
							groupId : resp.recurring_id,
							registor : sche._def.extendedProps.registor,
							rrule :rrule  
						}
						
						let events = [newEventData]
						calendar.addEventSource(events);	
					}	
					
						
				});		
			}		
		}
		
		
		
		// 일반 이벤트로 수정 (일반->일반, 반복->일반)	
		else{
			let prevRpt = sche._def.extendedProps.repeat;
			
			if(prevRpt == false){
				sche.setProp('title', $(".insertSchedule__title").val()); // 타이틀
				sche.setDates(eventStartDate, eventEndDate); // 날짜
				sche.setProp("allDay",$(".insertSchedule__allDay").is(":checked")) // 종일 여부
				sche.setExtendedProp('content', $(".insertSchedule__content").html()); // 내용
				sche.setExtendedProp('calNameVal', $(".calendarModal__calNameList option:selected").val()); // 캘린더 종류
				sche.setProp("color",$(".calendarModal__calNameList option:selected").attr("color")); // 컬러
			}
			$.ajax({
				url:"/schedule/scheduleUpdate",
				type:"post",
				data:{
					id : $("#eventId").val(),
					calendar_id : parseInt( $(".calendarModal__calNameList option:selected").val()),
					title : $(".insertSchedule__title").val(),
					content : $(".insertSchedule__content").html(),
					start_date : eventStartDate.toISOString(),
				    end_date : eventEndDate.toISOString(),
					all_day : $(".insertSchedule__allDay").is(":checked"),
					recurring_id : sche.groupId===""?0:parseInt(sche.groupId,10)
				}
			}).done(function(resp){
				let selectCal = $(".selectNavi").map((i,e)=>{
					return parseInt($(e).attr("data-id"));
				}).toArray();
				
				if(!selectCal.includes(parseInt( $(".calendarModal__calNameList option:selected").val()))){
					calendar.getEventById($("#eventId").val()).remove();
				}else{
					// 일반 -> 일반: 별도의 처리 필요없음.
							
					// 반복 -> 일반	
					if(prevRpt == true){
						calendar.getEventById($("#eventId").val()).remove();
	
						let newEventData = {
							id : resp.id,
							title: resp.title, // 일정 제목
							allDay: resp.all_day, // 종일 여부
							color:  $(".calendarModal__calNameList option:selected").attr("color"), // 캘린더 색상
							content: resp.content, // 일정 내용
							calNameVal: resp.calendar, // 캘린더 번호
							reg_date: new Date(sche._def.extendedProps.reg_date), // 등록일자
							repeat: false, // 반복 이벤트 여부
							start:eventStartDate, // 일정 시작일
							end: eventEndDate // 일정 종료일
						}
												
						let events = [newEventData];
						calendar.addEventSource(events);
					}	
				}
			});
		}
		$.modal.close();
	});

	calendar.render();
	
	function reloadEvent(init){ // 캘린더 선택 시 이벤트 다시 로드
		calendar.removeAllEvents();
	
		if(init ==1){ // 누른 캘린더 기억하도록 (로그아웃 시 초기화)
			let arrString = $("#calIds").val();
			let arr = arrString.replace(/\[|\]/g, '').split(',');
			arr.map((e,i)=>{
				$(".customMenu[data-id='"+parseInt(e)+"']").addClass("selectNavi");
				let color = $(".customMenu[data-id='"+parseInt(e)+"']").find(".naviConp__calColor");
				color.css("background-color",color.attr("data-color"));
			})
		}
		
	
		if($(".selectNavi").length>=1){
			let calIds = $(".selectNavi").map((i,e)=>{
				return parseInt($(e).attr("data-id"));
			}).toArray();
			
			$.ajax({
				url:"/schedule/selectByCalendarIdSchedule",
				data:{calIds:calIds, init:init},
				type:"post",
				async:false
			}).done(function(resp){

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
						repeat: false,
						start: resp[i].start_date, // 일정 시작 일자
						end: resp[i].end_date // 일정 종료 일자
					}
					
					
					if(resp[i].recurring_id != 0){
						eventData.repeat = true;
						let endKey = resp[i].endKey;
						let frequency_whenOption = resp[i].frequency_whenOption;
						
						let chkWeekDayList = resp[i].selectWeeks && resp[i].selectWeeks !== "" ? resp[i].selectWeeks.split(",") : [];
						if(chkWeekDayList.length>=1)
							chkWeekDayList = chkWeekDayList.map((e)=>{ return parseInt(e); });
							
						let endValue = resp[i].endValue;
						if (endKey == "count" &&  frequency_whenOption == "weekly") endValue *= chkWeekDayList.length; 
						
						let dtstart = new Date(resp[i].start_date);
						dtstart.setHours(dtstart.getHours()+9);
						
						let rrule = {
							freq:  frequency_whenOption,
							[endKey]: endValue,
							interval: resp[i].intervalCnt,
							dtstart:dtstart
						}
						if(frequency_whenOption == "weekly"){
							rrule.byweekday = chkWeekDayList;
						}
						eventData.rrule =rrule
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
	}
	
	
	
	// 캘린더 수정 모달
	$(document).on("click",".customMenu .editNavi, .trashMenu .editNavi",function(e){
		e.stopPropagation(); // 수정 버튼 누르면 캘린더 활성화 이벤트 중지
		$(".calendarInsertModal__save").addClass("calendarInsertModal__update").removeClass("calendarInsertModal__save").text("수정");
		calendarModalUpdateInit($(this).attr("data-id"),$(this).parent().parent().find(".naviConp__addTag").attr("data-isshare"));
		$(".calendarInsertName").removeAttr("disabled");
		$(".deleteWhenUpdate").css("display","flex");
		$(".calendarInsertModal__update").css("display","block");
		$(".calendarInsertModal__restore").css("display","none");
		
		if($(this).attr("data-trash") == "false"){ // 일반 캘린더
			if($(this).parent().parent().find(".naviConp__addTag").attr("data-isshare")==0){ // 내 캘린더 추가 모달창
				$(".calendarInsertModalTite").text("내 캘린더 수정");
				$(".calendarInsertModal").attr("style","max-width:400px !important;");
				$(".privateCalendarEl1").attr("style","width:30%");
				$(".privateCalendarEl2").attr("style","width:70%");
				$(".shareCalendarEl").css("display","none");
				
			}else{ // 공유 캘린더 추가 모달창
				$(".calendarInsertModalTite").text("공유 캘린더 수정");
				$(".calendarInsertModal").attr("style","max-width:900px !important;");
				$(".privateCalendarEl1").attr("style","width:15%");
				$(".privateCalendarEl2").attr("style","width:40%")
				$(".shareCalendarEl1").css("display","flex");
				$(".shareCalendarEl2").css("display","flex");
			}

		}else{ // 휴지통 캘린더
			let title = $(this).attr("data-is_share")=="true"?"공유 캘린더":"내 캘린더";
			$(".calendarInsertModalTite").text("휴지통 ("+title+")");
			$(".deleteWhenUpdate").css("display","none");
			$(".calendarInsertModal").attr("style","max-width:400px !important;");
			$(".privateCalendarEl1").attr("style","width:30%");
			$(".privateCalendarEl2").attr("style","width:70%");
			$(".shareCalendarEl").css("display","none");
			$(".calendarInsertName").attr("disabled",true);
			$(".calendarInsertModal__update").css("display","none");
			$(".calendarInsertModal__restore").css("display","block").attr("data-id",$(this).attr("data-id"));
			$(".calendarInsertModal__delete").attr("data-trash","1");
		}
		$(".calendarInsertModal").modal({
			showClose: false
		});
	});
	
	
	// 캘린더 클릭 시 달력이 일정 제거 혹은 추가
	$(document).on("click",".customMenu",function(){
		if($(this).hasClass("selectNavi")){ // 캘린더 선택 해제
			$(this).removeClass("selectNavi");
			$(this).find(".naviConp__calColor").css("background-color","transparent");
		}else{ // 캘린더 선택
			$(this).addClass("selectNavi");
			let color = $(this).find(".naviConp__calColor");
			color.css("background-color",color.attr("data-color"));
		}
		reloadEvent();
	});
});







// 그 외 function -> btn click event etc...
$(document).ready(function() {
	$(".naviConp__addTag").on("click", function(e) { // 캘린더 추가
		calendarModalInit();
		$(".calendarInsertModal__update").addClass("calendarInsertModal__save").removeClass("calendarInsertModal__update").text("저장");
		$(".calendarInsertModal__restore").css("display","none");
		$(".calendarInsertModal__save").css("display","block");
		
		if($(this).attr("data-isshare")==0){ // 내 캘린더 추가 모달창
			$(".calendarInsertModalTite").text("내 캘린더");
			$(".calendarInsertModal").attr("style","max-width:400px !important;");
			$(".privateCalendarEl1").attr("style","width:30%");
			$(".privateCalendarEl2").attr("style","width:70%");
			$(".shareCalendarEl").css("display","none");
		}else{ // 공유 캘린더 추가 모달창
			$(".calendarInsertModalTite").text("공유 캘린더");
			$(".calendarInsertModal").attr("style","max-width:900px !important;");
			$(".privateCalendarEl1").attr("style","width:15%");
			$(".privateCalendarEl2").attr("style","width:40%")
			$(".shareCalendarEl1").css("display","flex");
			$(".shareCalendarEl2").css("display","flex");
		}
		$(".calendarInsertModal").modal({
			showClose: false
		});
		$(".calendarModal__cancel").on("click", $.modal.close);
	});
	
	$("#scheduleAddBtn").on("click", function(e) {
		modalInitail("insert",e);
		$(".insertSchedule__startDate").val(new Date().toISOString().slice(0,10)); // 시작 날짜
		$(".insertSchedule__endDate").val(new Date().toISOString().slice(0,10)); // 끝 날짜
		$(".scheduleInsertModal").modal({
			showClose: false
		});	
	});


	$(".insertSchedule__allDay").on("change", function() { // 일정 등록 시 종일 선택하면 시간 입력 불가, 시간 00:00 ~ 23:59로 변경
		if ($(this).is(":checked")) {
			$("input[type=time]").attr("disabled", true);
			$(".insertSchedule__startTime").val("00:00");
			$(".insertSchedule__endTime").val("23:59");
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









// ----------------------------------------------------------------------------------------------------------------------------------------------------------------
/* 캘린더 추가 모달창 */
// ----------------------------------------------------------------------------------------------------------------------------------------------------------------


/* insert용 데이터 초기화 */
function calendarModalInit(){
	$(".calendarInsertName").val("");
	$(".colorInput").val("#000000");
	$("input:radio[name=share]").prop("checked",false);
	$(".empSearchKeyword").val("");
	$(".deptList__cover *").remove();
	$(".empAllList__empList *").remove();
	$(".empSelectList__empList *").remove();
	$(".calendarModal__buttons>.calendarInsertModal__delete").css("display","none");
	$(".calendarInsertName").removeAttr("disabled");
	$(".deleteWhenUpdate").css("display","flex")
	reloadEmpList();
	loadUserInfo();
}

/* update용 데이터 setting */
function calendarModalUpdateInit(id, is_share){
	$.ajax({
		url:"/schedule/selectCaledarInfoByCalendarId",
		data:{
			id:id
		},
		type:"post"
	}).done(function(resp){
		$(".calendarInsertName").val(resp.name);
		$(".colorInput").val(resp.color);
		$("input:radio[name=share]").prop("checked",false);
		$(".empSearchKeyword").val("");
		$(".deptList__cover *").remove();
		$(".empAllList__empList *").remove();
		$(".empSelectList__empList *").remove();
		reloadEmpList();
		loadUserInfo();
		
		$(".calendarInsertModal__update").attr("data-id",resp.id);
		$(".calendarModal__buttons>.calendarInsertModal__delete").css("display","block").attr("data-id",resp.id);
		// 권한 불러오기
		
		if(is_share == 1){
			let emp_ids = resp.emp_ids.split(",");
			let emp_names = resp.emp_names.split(",");
			let task_names = resp.task_names.split(",");
			for(let i=0; i<emp_ids.length; i++){
				if(emp_ids[i]==resp.loginID) continue;
				let empList__emp = $("<div>").attr("class","empSelectList__emp").attr("data-emp_id",emp_ids[i]);
				let name = $("<span>").text(emp_names[i]);
				let taskName = $("<span>").text("("+task_names[i]+")");
				$(".empSelectList__empList").append(empList__emp.append(name).append(taskName))
			}
		}
		
	});
}


// 로그인한 사용자는 기본적으로 권한 추가
function loadUserInfo(){
	// 로그인 아이디 해당 정보 불러오기
	$.ajax({
		url: "/office/getMyInfo",
		dataType: "json",
		data: { keyword: $("#loginID").val() },
		type: "POST"
	}).done(function(resp) {
		let userId = $("<div>").attr("data-emp_id",resp.id).attr("class","myEmpInfo");
		let name = $("<span>").text(resp.name);
		let taskName = $("<span>").text("("+resp.task_name+")");
		$(".empSelectList__empList").append(userId.append(name).append(taskName))
	})
}

// department & team 전체 목록 불러오기 & 
function reloadEmpList(){
	$(document).ready(function(){
		// 회사 총 인원 수
		$.ajax({
			url: "/office/empCount",
			dataType: "json",
			type: "POST"
		}).done(function(resp) {
			$("#officeEmpCount").text("(" + resp + ")");
		})
	
		// 부서별 총 인원 수
		$.ajax({
			url: "/office/selectDeptInfo",
			type: "POST",
		}).done(function(resp) {
			let firstDeptName = resp[0].dept_name;
			for(let i=0; i<resp.length; i++){
				let dept__deptName = $("<div>").attr("class","dept__deptName").attr("data-dept_name",resp[i].dept_name).attr("data-dept_id",resp[i].department_id);
				let dept__team = $("<div>").attr("class","dept__team");
				let dept__toggle = $("<i>").attr("class","fa-solid fa-plus");
				
				let name = $("<span>").text(resp[i].dept_name);
				let cnt = $("<span>").text("("+resp[i].count+")");
				dept__deptName.append(dept__toggle).append(name).append(cnt);
				if(resp[i].dept_name == firstDeptName){
					dept__toggle.attr("class","fa-solid fa-minus");
					dept__deptName.addClass("selectToggle currentSelectToggle");
				}
				$(".deptList__cover").append(dept__deptName).append(dept__team);
				
			}
	
			// 팀 별 총 인원수
			$.ajax({
				url: "/office/selectTaskInfo",
				type: "POST",
			}).done(function(resp) {
				for(let i=0; i<resp.length; i++){	
					let dept__team = $(".dept__deptName[data-dept_name='"+resp[i].dept_name+"']").next();
					let team__teamName = $("<div>").attr("class","team__teamName").attr("data-task_id",resp[i].task_id);;
					let name = $("<span>").text(resp[i].task_name);
					let cnt = $("<span>").text("("+resp[i].count+")");
					team__teamName.append(name).append(cnt);
					dept__team.append(team__teamName);
					
					if(resp[i].dept_name != firstDeptName){
						dept__team.css("display","none");
					}
				}
			});
			selectDepartmentEmpInfo($(".dept__deptName[data-dept_name='"+firstDeptName+"']").attr("data-dept_id"));
		});
	})
}


// 회사내 모든 인원의 이름과 부서명 출력하기
function selectAllEmpInfo() {
	$.ajax({
		url: "/office/selectAllEmpInfo",
		type: "POST",
	}).done(function(resp) {
		$(".empAllList__empList *").remove();
		for(let i=0; i<resp.length; i++){
			let empList__emp = $("<div>").attr("class","empList__emp").attr("data-emp_id",resp[i].id);
			let name = $("<span>").text(resp[i].name);
			let taskName = $("<span>").text("("+resp[i].task_name+")");
			$(".empAllList__empList").append(empList__emp.append(name).append(taskName))
		}
	});
}

// 회사 내 부서별 모든 인원의 이름과 부서명 출력하기
function selectDepartmentEmpInfo(id) {
	$.ajax({
		url: "/office/selectDepartmentEmpInfo",
		data: { dept_id: id },
		type: "POST",
	}).done(function(resp) {
		$(".empAllList__empList *").remove();
		for(let i=0; i<resp.length; i++){
			let empList__emp = $("<div>").attr("class","empList__emp").attr("data-emp_id",resp[i].id);;
			let name = $("<span>").text(resp[i].name);
			let taskName = $("<span>").text("("+resp[i].task_name+")");
			$(".empAllList__empList").append(empList__emp.append(name).append(taskName))
		}
		
	});
}

// 회사 내 팀별 모든 인원의 이름과 부서명 출력하기
function selectDetpTaskEmpInfo(id) {
	$.ajax({
		url: "/office/selectDetpTaskEmpInfo",
		data: { task_id: id },
		type: "POST",
	}).done(function(resp) {
		$(".empAllList__empList *").remove();
		for (let i = 0; i < resp.length; i++) {
			let empList__emp = $("<div>").attr("class","empList__emp").attr("data-emp_id",resp[i].id);;
			let name = $("<span>").text(resp[i].name);
			let taskName = $("<span>").text("("+resp[i].task_name+")");
			$(".empAllList__empList").append(empList__emp.append(name).append(taskName))
		}
	});
}

// 조직도 클릭했을 때 변화 (회사명)
$(document).on("click", ".deptList__companyName", function() {
	
	if($(this).hasClass("selectToggle")){ // 해제
		$(this).find("i").attr("class","fa-solid fa-plus");
		$(this).next().css("display","none");
		$(this).removeClass("selectToggle");
		$(".empAllList__empList *").remove();
	}
	else{ // 선택
		$(this).addClass("selectToggle");
		$(this).find("i").attr("class","fa-solid fa-minus");
		$(this).next().css("display","block");
		
		if($(".selectToggle").length==1){ // 아무것도 선택 안된거니까 전체 출력
			selectAllEmpInfo();
		}else{
			if($(".currentSelectToggle").length==1){ // 기존에 선택했던 부서
				selectDepartmentEmpInfo($(".currentSelectToggle").attr("data-dept_id"));
			}else{ //기존에 선택했던 팀
				selectDetpTaskEmpInfo($(".selectTeam").attr("data-task_id"));
			}
		}
	}
});

// 조직도 클릭했을 때 변화 (부서명)
$(document).on("click", ".dept__deptName", function() {
	if($(this).hasClass("selectToggle")){ // 해제
		$(this).find("i").attr("class","fa-solid fa-plus");
		$(this).next().css("display","none");
		$(this).removeClass("selectToggle");
		$(this).removeClass("currentSelectToggle");
		
		if($(".selectTeam").parent().prev().attr("data-dept_id")==$(this).attr("data-dept_id")){
			$(".selectTeam").removeClass("selectTeam"); // 선택 해제한 팀 내에 선택한 팀 요소가 있었을 경우 취소 
		}
		
		if($(".selectToggle").length==1){ // 아무것도 선택 안된거니까 전체 출력
			selectAllEmpInfo();
		}else{
			if($(".selectTeam").length == 0){ // 선택된 팀이 없으면 첫번째 부서 선택
				$($(".selectToggle.dept__deptName")[0]).addClass("currentSelectToggle");
				selectDepartmentEmpInfo($(".currentSelectToggle").attr("data-dept_id"));
			}	
		}
	}
	else{ // 선택
		$(".currentSelectToggle").removeClass("currentSelectToggle");
		$(this).addClass("selectToggle");
		$(this).addClass("currentSelectToggle");
		$(".selectTeam").removeClass("selectTeam");
		$(this).find("i").attr("class","fa-solid fa-minus");
		$(this).next().css("display","block");
		
		if($(this).hasClass("dept__deptName")){
			selectDepartmentEmpInfo($(this).attr("data-dept_id"));
		}
	}

});

// 조직도 클릭했을 때 변화 (팀명)
$(document).on("click", ".team__teamName", function() {
	$(".selectTeam").removeClass("selectTeam"); // 팀선택 해제
	$(".currentSelectToggle").removeClass("currentSelectToggle"); // 부서선택 해제
	$(this).addClass("selectTeam"); // 팀 선택
	selectDetpTaskEmpInfo($(this).attr("data-task_id"));
});



// 권한 부여 전 목록에서 직원 선택 (권한을 부여하고자 선택)
$(document).on("click",".empList__emp",function(){
	if($(this).hasClass("selectEmp")){ // 해제
		$(this).removeClass("selectEmp");
	}else{
		$(this).addClass("selectEmp");
	}
});

// 권한 부여된 목록에서 직원 선택 (권한을 철회하고자 선택)
$(document).on("click",".empSelectList__emp",function(){
	if($(this).hasClass("deselectEmp")){ // 해제
		$(this).removeClass("deselectEmp");
	}else{
		$(this).addClass("deselectEmp");
	}
});

// 직원 전체 선택
$(document).on("click",".empAllList__selectAll",function(){
	$(".empList__emp").addClass("selectEmp");
});

// 직원 전체 선택 해제
$(document).on("click",".empAllList__cancelAll",function(){
	$(".selectEmp").removeClass("selectEmp");
});



// 선택한 직원 권한 등록
$(document).on("click",".selectIcon__select",function(){
	let selectEmps = $(".selectEmp").clone();
	selectEmps.addClass("empSelectList__emp").removeClass("empList__emp");
	
	let existingEmpsSet = new Set($(".empSelectList__empList .empSelectList__emp, .myEmpInfo").map(function () {
	    return $(this).attr("data-emp_id");
	}));
	
	selectEmps.each(function () {
	    let currentEmpText = $(this).attr("data-emp_id");
	
	    // Set에 이미 추가된 직원인지 확인
	    if (!existingEmpsSet.has(currentEmpText)) {
	        $(".empSelectList__empList").append($(this));
	        existingEmpsSet.add(currentEmpText);
	    }
	});

	$(".empSelectList__cnt").text(existingEmpsSet.size);
	$(".selectEmp").removeClass("selectEmp");
});

// 선택한 직원 권한 취소
$(document).on("click",".selectIcon__cancel",function(){
	$(".empSelectList__cnt").text(parseInt($(".empSelectList__cnt").text())-$(".deselectEmp").length);
	$(".deselectEmp").remove();
});


// 캘린더 추가
$(document).on("click",".calendarInsertModal__save",function(){
	
	if($(".calendarInsertName").val() == ""){
		Swal.fire({
			icon: "error",
			text: "캘린더 이름을 입력하세요."
		});
		return;
	}else{
		let empIds = $(".empSelectList__empList").children().map((i,e)=>{
			return $(e).attr("data-emp_id");
		}).toArray();
		
		let data ={
			name: $(".calendarInsertName").val(),
			color: $(".colorInput").val(),
			empIds : empIds,
			is_share : $(".calendarInsertModalTite").text().includes("공유 캘린더")?1:0
		}
	
		$.ajax({
			url:"/schedule/calendarInsert",
			data:data,
			type:"post"
		}).done(function(){
			getNavi();
			$.modal.close();
		})
	}
});



// 캘린더 수정
$(document).on("click",".calendarInsertModal__update",function(){
	
	if($(".calendarInsertName").val() == ""){
		Swal.fire({
			icon: "error",
			text: "캘린더 이름을 입력하세요."
		});
		return;
	}else{
		let empIds = $(".empSelectList__empList").children().map((i,e)=>{
			return $(e).attr("data-emp_id");
		}).toArray();
		
		let data ={
			id : parseInt($(this).attr("data-id")),
			name: $(".calendarInsertName").val(),
			color: $(".colorInput").val(),
			empIds : empIds,
			is_share : $(".calendarModal__title").html()=="공유 캘린더"?1:0
		}
	
		$.ajax({
			url:"/schedule/calendarUpdate",
			data:data,
			type:"post"
		}).done(function(){
			getNavi();
			$.modal.close();
		})
	}
});	
	
// 캘린더 삭제 
$(document).on("click",".calendarInsertModal__delete",function(){
	// 영구 삭제
	if($(this).attr("data-trash")=="1"){ 
		$.ajax({
			url:"/schedule/deleteCalendar",
			data:{id: $(this).attr("data-id")},
			type:"post"
		}).done(function(){
			getNavi();
			$.modal.close();
		});
		
	// 휴지통
	}else{
		$.ajax({
			url:"/schedule/trashCalendar",
			data:{id: $(this).attr("data-id"), trash:1},
			type:"post"
		}).done(function(){
			getNavi();
			$.modal.close();
		});
	}
});

// 캘린더 복원
$(document).on("click",".calendarInsertModal__restore",function(){
	$.ajax({
		url:"/schedule/trashCalendar",
		data:{id: $(this).attr("data-id"), trash:0},
		type:"post"
	}).done(function(){
		getNavi();
		$.modal.close();
	});
});



// 공유 캘린더 공유 대상 검색
$(document).on("input", ".empSearchKeyword", function() {
	if ($(this).val() !== "") {
		$.ajax({
			url: "/office/searchUserAjax",
			type: "POST",
			data: { keyword: $(this).val() }
		}).done(function(resp) {
			$(".deptList__companyName").css("display","none");
			$(".deptList__cover *").remove();
			$(".empAllList__empList *").remove();
			for(let i=0; i<resp.length; i++){
				let empList__emp = $("<div>").attr("class","empList__emp").attr("data-emp_id",resp[i].id);
				let name = $("<span>").text(resp[i].name);
				let taskName = $("<span>").text("("+resp[i].task_name+")");
				$(".empAllList__empList").append(empList__emp.append(name).append(taskName))
			}
		})
	} else {
		$(".deptList__companyName").css("display","block");
		reloadEmpList();
	}

})