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

				
				let selectCal = $(".selectNavi").map((i,e)=>{
					return parseInt($(e).attr("data-id"));
				}).toArray();	
				if(selectCal.includes(parseInt(eventData.calNameVal))){
					events.push(ed);
				}
				
				

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
			
			// 현재 등록한 이벤트의 캘린더가 활성화된 캘린더이면 캘린더에 이벤트 추가, 아니면 DB에만 추가
			
			let selectCal = $(".selectNavi").map((i,e)=>{
				return parseInt($(e).attr("data-id"));
			}).toArray();
			if(selectCal.includes(parseInt(eventData.calNameVal))){
				events.push(eventData);
			}
		}

		return events;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	$(".calendarModal__update").on("click", function() { // 일정 수정 모달 띄우기 (기존 내용 불러오기)
		$.modal.close();
		
		console.log(calendar.getEventById($("#eventId").val()));
		
		$.ajax({
			url:"/schedule/selectById",
			data:{id:$("#eventId").val()},
			type:"post",
			async:false
		}).done(function(resp){
			modalInitail("modify",calendar.getEventById($("#eventId").val()));
			
			
			console.log("수정할 내용 resp:");
			console.log(resp);
			
			

			let startDate, startTime, endDate, endTime;
			startDate = resp.start_date.slice(0,10);
			startTime = resp.start_date.slice(11,16);
			endDate = resp.end_date.slice(0,10);
			endTime = resp.end_date.slice(11,16);

			$(".calendarModal__calNameList").val(resp.calendar_id).prop("selected",true);

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

		$(".scheduleInsertModal").modal({
			showClose: false
		});
	});
	
	
	
	
	
	
	$(".calendarModal__updateSave").on("click", function() { 
		
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

		console.log($(".insertSchedule__allDay").is(":checked"));
		sche.setProp('title', $(".insertSchedule__title").val());
		sche.setDates(startDate, endDate);
		sche.setProp("allDay", $(".insertSchedule__allDay").is(":checked"))
		sche.setExtendedProp('content', $(".insertSchedule__content").html());
		sche.setExtendedProp('calNameVal', $(".calendarModal__calNameList option:selected").val());
		sche.setProp("color",$(".calendarModal__calNameList option:selected").attr("color"));
		
		// 반복 이벤트 수정
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
					    recurring_id : sche.groupId===""?0:parseInt(sche.groupId,10)
					}
				}).done(function(resp){
					console.log("반복 변경 성공~~~");
				});
				
				
			}
			
		}
		
		
		
		// 일반 이벤트 수정
		
		else{
			console.log($(".insertSchedule__allDay").is(":checked"))
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
					recurring_id : sche.groupId===""?0:parseInt(sche.groupId,10)
				}
			}).done(function(){
				console.log("업데이트 성공!");
			});
		}
		$.modal.close();
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 일정 생성 (입력한 일정 정보를 이용해 달력 및 DB에 일정 삽입)
	function generateEvent() {

		// 일정 시작일
		let eventStartDate = new Date($(".insertSchedule__startDate").val());
		eventStartDate.setHours( parseInt($(".insertSchedule__startTime").val().slice(0, 2)),  parseInt($(".insertSchedule__startTime").val().slice(3, 5)));
		
		// 일정 종료일
		let eventEndDate = new Date($(".insertSchedule__endDate").val());
		eventEndDate.setHours( parseInt($(".insertSchedule__endTime").val().slice(0, 2)),  parseInt($(".insertSchedule__endTime").val().slice(3, 5)));
				
		// 이벤트 등록일자
		let reg_date = new Date();
		reg_date.setHours(reg_date.getHours()+9);
		
		// 일정 (이벤트) 정보 배열
		let events = [];
		
		
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


			// 1일 이상의 반복 이벤트 일 때 (반복 연속이벤트)
			} else {
				Swal.fire({
					icon: "error",
					text: "반복이벤트는 2일 이상 설정할 수 없습니다."
				});
				return false;
			}
	
				
		}else{ // 일반 이벤트 이면 (1일 혹은 연속이벤트)
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
	
	
	
	
	
	/*
	
	
	function setScheduleData(){
		// 일정 시작일
		let eventStartDate = new Date($(".insertSchedule__startDate").val());
		eventStartDate.setHours( parseInt($(".insertSchedule__startTime").val().slice(0, 2)),  parseInt($(".insertSchedule__startTime").val().slice(3, 5)));
		
		// 일정 종료일
		let eventEndDate = new Date($(".insertSchedule__endDate").val());
		eventEndDate.setHours( parseInt($(".insertSchedule__endTime").val().slice(0, 2)),  parseInt($(".insertSchedule__endTime").val().slice(3, 5)));
				
		// 이벤트 등록일자
		let reg_date = new Date();
		reg_date.setHours(reg_date.getHours()+9);
		
		// 일정 (이벤트) 정보 배열
		let events = [];
		
		
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


			// 1일 이상의 반복 이벤트 일 때 (반복 연속이벤트)
			} else {
				Swal.fire({
					icon: "error",
					text: "반복이벤트는 2일 이상 설정할 수 없습니다."
				});
				return false;
			}
	
				
		}else{ // 일반 이벤트 이면 (1일 혹은 연속이벤트)
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
	
	*/
	
	
	// 현재 등록한 이벤트의 캘린더가 활성화된 캘린더이면 캘린더에 이벤트 추가, 아니면 DB에만 추가
		let selectCal = $(".selectNavi").map((i,e)=>{
			return parseInt($(e).attr("data-id"));
		}).toArray();	
		if(selectCal.includes(parseInt(eventData.calNameVal))){
			return events;
		}else{
			return [];
		}
		
		