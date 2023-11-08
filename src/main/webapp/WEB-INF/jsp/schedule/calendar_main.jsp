<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>제목</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<link rel="stylesheet" href="/css/commons/naviBar.css">
<script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.9/index.global.min.js'></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css"/>
<script type="text/javascript" src="http://www.google.com/jsapi?key=AIzaSyC0HDcPeTEdFVF1yhS3A14hYQTKPhKghnE"></script>
<link rel="stylesheet" href="/css/schedule/calendarModal.css">
<link rel="stylesheet" href="/css/schedule/calendar__main.css">
   
    <title>캘린더 테스트</title>
    <style>
      .fc-toolbar-chunk {display: flex;}
      .fc .fc-next-button, .fc .fc-prev-button{
        background-color: transparent;
        border: 0;
        color:#6C6C72;
      }
      .fc .fc-next-button:hover, .fc .fc-prev-button:hover{
        background-color: transparent;
        color:#6C6C7250;
      }
 
      .fc .fc-next-button:focus,.fc .fc-prev-button:focus{
        box-shadow:none;
      }
      
      .fc .fc-button-primary:disabled{
        background-color: #75B47D;
        border:0;
      }

      .fc .fc-button-primary:disabled:hover{
        background-color: #75B47D;
        opacity: 0.65;
        border:0;
      }
      .fc .fc-button-primary{
        background-color: #75B47D;
        border:0;
      }

      .fc .fc-button-primary:hover{
        background-color: #20412E;
        opacity: 1.0;
        border:0;
      }
      .fc .fc-button-primary:not(:disabled).fc-button-active, .fc .fc-button-primary:not(:disabled):active {
        background-color: #20412E;
        border:0;
      }
      .modal{max-width: 900px;}
  
    </style>
</head>
<body>
<%@ include file="../commons/header.jsp" %>
	
	<div class="container d-flex">
		<div class="naviBar">
			<c:if test="${naviBtn != '' }">
				<div class="naviBtn">
					<i class="fa-solid fa-plus naviBtn__icon"></i>
					<div class="naviBtn__text">${naviBtn }</div>
					<input type="hidden" id="location" value="${naviBtnLocation }" />
				</div>
			</c:if>
			<c:forEach var="i" begin="0" end="${naviMenuLength - 1 }">
				<div class="naviConp">
					<div class="naviConp__icon">
						<i class="fa-solid ${naviIcon[0] }"></i>
					</div>
					<div class="naviConp__title">${naviMenu[i] }</div>
					<div class="naviConp__icon">
						<i class="fa-solid ${naviIcon[1] }"></i>
					</div>
				</div>
			</c:forEach>
			<input type="hidden" id="currentMenu" value="${currentMenu }" />
		</div>
		 <div class="container">
      <!-- <div class="menubar"><button class="calendarAdd">캘린더 +</button></div> -->
        <div id='calendar'></div>
      </div>
      

      <!-- 일정 추가 모달창 -->
      <div class="scheduleInsertModal">
        <div class="calendarModal__title">일정 추가</div>
        <div class="calendarModal__body">
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">캘린더</div>
                <div class="calendarModal__bodyContent"><select><option value="나의 프로젝트">나의 프로젝트</option></select></div>
            </div>
            <div class="calendarModal__bodyLine d-flex">
              <div class="calendarModal__bodyTitle">일정 제목</div>
              <div class="calendarModal__bodyContent"><input type="text" class="schedule__title"></div>
          </div>
          <div class="calendarModal__bodyLine d-flex">
            <div class="calendarModal__bodyTitle">시작</div>
            <div class="calendarModal__bodyContent calendarModal__dateInfo d-flex">
                <input type="date" class="schedule__startDate"><input type="time" class="schedule__startTime"><label><input type="checkbox" class="schedule__allDay"><div class="checkbox__content">종일</div></label>
            </div>
        </div>
        <div class="calendarModal__bodyLine d-flex">
            <div class="calendarModal__bodyTitle">종료</div>
            <div class="calendarModal__bodyContent calendarModal__dateInfo d-flex">
                <input type="date" class="schedule__endDate"><input type="time" class="schedule__endTime"><label><input type="checkbox" class="schedule__repeat"><div class="checkbox__content">반복</div></label>
            </div>
        </div>
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">내용</div>
                <div class="calendarModal__bodyContent calendarModal__content" contenteditable="true">dd</div>
            </div>
        </div>
        <div class="calendarModal__buttons align-center">
            <button class="calendarModal__cancel">취소</button>
            <div class="buttons__margin"></div>
            <button class="calendarModal__save">저장</button>
        </div>
      </div>

      <!-- 일정 확인 모달창 -->
      <div class="scheduleViewModal">
        <div class="calendarModal__title">일정 내용</div>
        <div class="calendarModal__body">
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">캘린더</div>
                <div class="calendarModal__bodyContent">나의 프로젝트</div>
            </div>
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">일정 제목</div>
                <div class="calendarModal__bodyContent">오전 회의</div>
            </div>
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">일저 시간</div>
                <div class="calendarModal__bodyContent">2023-10-27 오전 10:00 ~ 오전 10:30</div>
            </div>
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">최초 등록</div>
                <div class="calendarModal__bodyContent">대표이사 (2023-10-24 오후 4:00)</div>
            </div>
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">반복</div>
                <div class="calendarModal__bodyContent">반복 없음</div>
            </div>
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle">내용</div>
                <div class="calendarModal__bodyContent">회의하기 시렁</div>
            </div>
        </div>
        <div class="calendarModal__buttons align-center">
            <button class="calendarModal__delete">삭제</button>
            <div class="buttons__margin"></div>
            <button class="calendarModal__update">수정</button>
        </div>  
      </div>


      <!-- 캘린더 추가 모달창 -->
      <div class="calendarInsertModal">
        <div class="calendarModal__title">공유 캘린더</div>
        <div class="calendarModal__body">
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle w-15">캘린더 이름</div>
                <div class="calendarModal__bodyContent w-60"><input type="text"></div>
            </div>
            <div class="calendarModal__bodyLine d-flex">
                <div class="calendarModal__bodyTitle w-15">색깔</div>
                <div class="calendarModal__bodyContent w-60"><input type="color" class="colorInput"></div>
            </div>
            <div class="calendarModal__bodyLine">
                <div class="calendarModal__bodyTitle w-15">공유 대상</div>
                <div class="calendarModal__bodyContentShare d-flex">
                    <div class="calendarModal__bodyTitle w-15 d-flex">
                        <label class="align-center"><input type="radio" name="share" value="name"><div> 이름</div></label>
                        <label class="align-center"><input type="radio" name="share" value="group"><div> 조직</div></label>
                    </div>

                    <div class="calendarModal__bodyContent w-60"><input type="text"></div> 
                    <button class="bodyContentShare__searchBtn">검색</button>
                </div>
            </div>


            <div class="calendarModal__bodyBottom d-flex">
                <div class="calendarModal__deptList">
                    클로버 산업
                    <div class="calendarModal__dept">
                        <div class="dept__deptName">관리부</div>
                        <div class="dept__team">
                            <div class="team__teamName">인사팀</div>
                            <div class="team__teamName">구매총무팀</div>
                            <div class="team__teamName">재무회계팀</div>
                        </div>
                        <div class="dept__deptName">생산부</div>
                        <div class="dept__team">
                            <div class="team__teamName">생산1팀</div>
                            <div class="team__teamName">생산2팀</div>
                            <div class="team__teamName">품질관리팀</div>
                        </div>
                    </div>
                    
                </div>
                <div class="calendarModal__empAllList">
                    <div class="empAllList__empList">
                        <div class="empList__emp"> 강과장 &lt;영업2팀&gt;</div>
                        <div class="empList__emp"> 김이사 &lt;생산1팀&gt;</div>
                    </div>
                    <div class="empAllList__bottomBtn">
                        <button class="empAllList__selectAll">전체선택</button>
                        <button class="empAllList__cancelAll">선택취소</button>
                    </div>
                </div>
                <div class="calendarModal__selectIcon align-center">
                    <div>
                        <div class="selectIcon__select">
                            <i class="fa-solid fa-angle-right"></i>
                        </div>
                        <div class="selectIcon__margin"></div>
                        <div class="selectIcon__cancel">
                            <i class="fa-solid fa-angle-left"></i>
                        </div>
                    </div>      
                </div>
                <div class="calendarModal__empSelectList">
                    <div class="empSelectList__title d-flex">조회/등록 권한<div class="empSelectList__cnt">&nbsp;2</div></div>
                    <div class="empSelectList__empList">
                        <div class="empSelectList__emp">강과장</div>
                        <div class="empSelectList__emp">대표이사</div>
                    </div>
                </div>
              
            </div>
          </div>
          <div class="calendarModal__buttons align-center">
              <button class="calendarModal__cancel">취소</button>
              <div class="buttons__margin"></div>
              <button class="calendarModal__save">저장</button>
          </div>
      </div>
  </div>
	<script>
      $(document).ready(function(){
        $(".fc-next-button").removeClass("fc-button-primary");
        $(".fc-prev-button").removeClass("fc-button-primary");
      });

      $(".calendarAdd").on("click",function(){
        $(".calendarInsertModal").modal({
          fadeDuration:300,
          showClose:false
        });
        $(".calendarModal__cancel").on("click",$.modal.close);
      });

   
      $(".schedule__allDay").on("click",function(){
        if($(this).is(":checked")){
            $("input[type=time]").attr("disabled",true);
        }else{
            $("input[type=time]").removeAttr("disabled");
        }
      })
      
      window.onresize=function(){
    	  let w = window.innerWidth - 250;
    	  $("#calendar").css("width",w);
    	  $(".fc-view-harness").css("width",w);
      }
      
      $(document).ready(function(){
    	  let w = window.innerWidth - 250;
    	  $("#calendar").css("width",w);
    	  $(".fc-view-harness").css("width",w);
      })
      
    </script>
     <script>
        document.addEventListener('DOMContentLoaded', function() {
          let calendarEl = document.getElementById('calendar');
          let calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth',  
            googleCalendarApiKey: "AIzaSyC0HDcPeTEdFVF1yhS3A14hYQTKPhKghnE",
            eventSources:[{
                googleCalendarId:"45fd5e0fc65fd77c91ff24a46f3b7b726de9984323a7d933f3d53436717ce309@group.calendar.google.com",
                className:"koHolidays",
                color:"white",
                textColor:"red",
                editable:false
            }],

            headerToolbar:{
              left:'dayGridMonth,timeGridWeek,timeGridDay,listMonth',
              center:'prev title next today',
              right:''
            },
            locale:"ko",
            buttonText:{
              today:"오늘"
            },
            dateClick:function(e){
              $(".schedule__startDate").val(e.dateStr);
              $(".scheduleInsertModal").modal({
                fadeDuration:300,
                showClose:false
              });

                $(".calendarModal__cancel").on("click",$.modal.close);
                $(".calendarModal__save").on("click",function(){
                  calendar.addEvent({
                    title: $(".schedule__title").val(),
                    start:  $(".schedule__startDate").val(),
                    end: $(".schedule__endDate").val(),
                    color: "#FF00FF"
                  });
                  $(".schedule__title").val("");
                  $(".schedule__startDate").val("");
                  $(".schedule__startTime").val("");
                  $(".schedule__endDate").val("");
                  $(".schedule__endTime").val("");
                  $(".calendarModal__content").html("");
                  $.modal.close();
              });
            },
            eventClick:function(e){
            	console.log(e);
              $(".scheduleViewModal").modal({
                fadeDuration:300,
                showClose:false
              });

            }
        });
          calendar.render();
        });
  
      </script>
</body>
</html>