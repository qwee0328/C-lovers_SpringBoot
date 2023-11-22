document.addEventListener('DOMContentLoaded', function() {

	let currentDate =  new Date();
	currentDate.setHours(currentDate.getHours()+9);
	
	let calendarEl = document.getElementById('calendarSearch');
	const calendar = new FullCalendar.Calendar(calendarEl, {
		initialView: 'list',
       	views: {
            list: { buttonText: 'List' },
          },
	    visibleRange: {
	    	start: currentDate.getFullYear() + '-' + (currentDate.getMonth()).toString().padStart(2, '0') + '-01',
            end: currentDate.getFullYear() + '-' + (currentDate.getMonth()+3).toString().padStart(2, '0') + '-01',
	    },
	    // 높이 스크롤바 자동 생성
		height: true,
		
		// 캘린더 header
		headerToolbar: { 
			left: '',
			center: 'title',
			right: ''
		},
		
		// 타이틀 형식
		views: {
			list:{
				titleFormat:function(date){
					return `${date.start.year}. ${date.start.month +1}. ${date.start.day} - ${date.end.year}. ${date.end.month +1}. ${date.end.day}`;
				}
			}
		},

		// 캘린더 한국어로 출력
		locale: "ko", 
		
	    // 일정 클릭 시 일정 상세보기
		eventClick: function(e) {	
			// 일정 더보기 팝업찹 가리기 (일정 3개 이상인 경우 + more 눌렀을 때 뜨는 팝업창)
			$(".fc-popover").css("display", "none"); 
			
			
			let { event: { _instance: { range } } } = e;
									
			$.ajax({
				url:"/schedule/selectById",
				data:{id:e.event._def.publicId},
				type:"post",
				async:false
			}).done(function(resp){
				
				// 선택한 이벤트에 대한 정보 모달창에 삽입
				
				// 캘린더명
				$(".calendarModal__calName").text(resp.calendar_name);
				
				// 일정명
				$(".calendarModal__scheTitle").text(resp.title);
				
				// 일정 내용
				$(".calendarModal__scheContent").text(resp.content);
				
				
				// 최초 등록인 및 등록일자
				let regDate = resp.reg_date.slice(0,10) + " " +resp.reg_date.slice(11,16);
				$(".calendarModal__scheReg").text(`${resp.name} (${regDate})`);
				
				// 현재 선택된 이벤트 아이디
				$("#eventId").val(resp.id);
				
				// 반복 여부
				$(".calendarModal__repeatYN").text(resp.recurring_id != 0 ? "반복 있음" : "반복 없음");
				
				
				
				// 일정 시작일자 (시간대가 달라 +9)
				let startDate = new Date(resp.start_date);
				startDate.setHours(startDate.getHours()+9);
				
				// 일정 종료일자 (시간대가 달라 +9)
				let endDate = new Date(resp.end_date);
				endDate.setHours(endDate.getHours()+9);
		
				// 반복 일정이 아닌 경우 DB 내용 이용
				if(resp.recurring_id == 0){
					startDate = startDate.toISOString().slice(0, 10) + " " + startDate.toISOString().slice(11, 16);
					endDate = endDate.toISOString().slice(0, 10) + " " +  endDate.toISOString().slice(11, 16);
				
				// 반복 일정의 경우 이벤트 객체에서 날짜 값 불러와야 함				
				}else{
					startDate = range.start.toISOString().slice(0, 10) + " " + startDate.toISOString().slice(11, 16);
					endDate = range.end.toISOString().slice(0, 10) + " " + endDate.toISOString().slice(11, 16);
				}
				
				// 일정 시작일 및 종료일 
				$(".calendarModal__scheDate").text(`${startDate} ~ ${endDate}`);
			});
			
			
			// 일정 상세보기 모달창 띄우기
			$(".scheduleViewModal").modal({
				showClose: false
			});

		}
      
	});
    calendar.render();

	// 캘린더 선택 시 선택된 캘린더에 포함된 이벤트만 출력
	function reloadEvent(){ 
		
		// 검색어 이용하여 등록된 일정 목록 불러오기
		$.ajax({
			url:"/schedule/selectByKeyword",
			data:{keyword:$(".searchBar__input").val()},
			type:"post"
		}).done(function(resp){
			// 이벤트 전체 지우고
			calendar.removeAllEvents();
			let eventDatas = [];
	       	for(let i=0; i<resp.length; i++){

				// 일정 시작일
				let sdate = new Date(resp[i].start_date);
				// 일정 종료일
				let edate = new Date(resp[i].end_date);
				
				// allDay 이면 일정 종료일 + 1 (full calendar error issue로 굳혀진 convention 같은 것)
				if(resp[i].all_day == true){
					edate.setDate(edate.getDate()+1);
				}
					
				// 이벤트 데이터 객체 생성
				let eventData = {
					id: resp[i].id, // 일정 아이디
					title: resp[i].title, // 일정 제목
					allDay: resp[i].all_day, // 일정 하루종일 여부
					color: resp[i].color, // 캘린더 색깔
					content: resp[i].content, // 일정 내용
					calNameVal: resp[i].calendar_id, // 캘린더 아이디
					registor: resp[i].emp_id, // 최초 등록인 정보
					reg_date: resp[i].reg_date, // 최초 등록일 정보
					repeat: false, // 반복 여부
					start:sdate, // 시작일
					end: edate // 종료일
				}
				
				
				// 반복이벤트이면
				if(resp[i].recurring_id != 0){
					eventData.repeat = true; // 반복 여부
					
					// 종료 옵션 key (반복 횟수 or 종료일)
					let endKey = resp[i].endKey;
					
					// 반복 옵션 (일, 주, 월 , 년도)
					let frequency_whenOption = resp[i].frequency_whenOption;
					
					// 주 반복 이벤트에서 선택된 요일 정보
					let chkWeekDayList = resp[i].selectWeeks && resp[i].selectWeeks !== "" ? resp[i].selectWeeks.split(",") : [];
					if(chkWeekDayList.length>=1)
						chkWeekDayList = chkWeekDayList.map((e)=>{ return parseInt(e); });
					
					// 종료 옵션 value
					let endValue = resp[i].endValue;
					// 주 반복 이벤트의 경우, 반복횟수만큼 요일이 나올 수 있도록 설정 -> ex. 월, 화 선택 후 3으로 지정하면 월 화 월 이 기본 옵션이라서 월 화 월 화 월 화 되도록 설정해주는 것
					if (endKey == "count" &&  frequency_whenOption == "weekly") endValue *= chkWeekDayList.length; 
					
					// 반복 일정 시작일
					let dtstart = new Date(resp[i].start_date);
					dtstart.setHours(dtstart.getHours()+9);
					
					// 반복 일정 정보
					let rrule = {
						freq:  frequency_whenOption, // 반복 옵션
						[endKey]: endValue, // 반복 횟수 or 종료 일자
						interval: resp[i].intervalCnt, // 반복 간격
						dtstart:dtstart // 반복 일정 시작일
					}
					
					// 주 반복 이벤트일 경우, 요일 정보 추가
					if(frequency_whenOption == "weekly"){
						rrule.byweekday = chkWeekDayList;
					}
					
					eventData.rrule =rrule // 반복 일정 정보 객체에 추가
					eventData.groupId = resp[i].recurring_id; // 반복 이벤트 그룹 아이디 객체에 추가
				}
				eventDatas.push(eventData);
				
			}
			calendar.addEventSource(eventDatas);
	        calendar.render();
		});
	}
	
	// enter key로 검색
	$(document).on("keydown",".searchBar__input",function(e){
		if(e.keyCode==13){ // enter key로 검색
			if($(this).val()!=""){
				$("#calendarSearch").css("display","flex");
				$(".searchBar__icon").removeClass("searchBar__search").addClass("searchBar__cancel");
				$(".searchBar__icon").find("i").attr("class","fa-solid fa-xmark align-center")
				reloadEvent();		
			}
		}
	});

	// button으로 검색
	$(document).on("click",".searchBar__icon",function(){
		if($(".searchBar__input").val()!=""){
			if($(this).hasClass("searchBar__search")){
				console.log("g");
				$(this).removeClass("searchBar__search").addClass("searchBar__cancel");
				$(this).find("i").attr("class","fa-solid fa-xmark align-center")
			}else if($(this).hasClass("searchBar__cancel")){
				$(this).removeClass("searchBar__cancel").addClass("searchBar__search");
				$(this).find("i").attr("class","fa-solid fa-magnifying-glass align-center")
			}
			$("#calendarSearch").css("display","flex");
			reloadEvent();		
		}
	});
	
	
/*	// 값 바꾸면 다시 출력
	$(document).on("change",".inputSearchDate_start, .inputSearchDate_end",function(){
		
		if($(".inputSearchDate_start").val() > $(".inputSearchDate_end").val()){
			
				Swal.fire({
					icon: "error",
					text: "시작일보다 종료일이 앞설 수 없습니다."
				});
				return false;
		}else{
			let currentVisibleRange = calendar.getOption("visibleRange");
		
			let startDate = new Date($(".inputSearchDate_start").val() + 'T00:00:00');
	        let endDate = new Date($(".inputSearchDate_end").val() + 'T23:59:59');
	
	        currentVisibleRange.start = startDate.toISOString().split('T')[0];
	        currentVisibleRange.end = endDate.toISOString().split('T')[0];
        
 			//console.log(currentVisibleRange);

	        calendar.setOption('visibleRange', currentVisibleRange);
	        
	        

			console.log("Before title change:", calendar.getOption("title"));
			let newCalendarTitle = "헤헤";
			calendar.setOption('headerToolbar', {
			    start: '',
			    center: 'title',
			    end: ''
			});

			calendar.setOption('title', newCalendarTitle);
			console.log("After title change:", calendar.getOption("title"));
			
			
			console.log(calendar.getOption("visibleRange"));
	        calendar.render();
		}
	
	});*/
	
	
	// 검색 취소
	$(document).on("click",".searchBar__cancel",function(){
		calendar.removeAllEvents();
		$(".searchBar__input").val("");
		$("#calendarSearch").css("display","none");
	})

});



