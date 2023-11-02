<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<link rel="stylesheet" href="/css/home.css">
</head>
<body>
	<div class="container">
	
		<!-- MainNavi -->
		<div class="mainNavi">
            <div class="mainNavi__items">
                <c:forEach var="i" items="${menu}">
                    <div class="items__item"}>
                        <div class="item__curcle">
                            <FontAwesomeIcon icon={menuIcon[i]} class="curcle__icon" />
                        </div>
                        <div class="item__title">
                            i
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        
        <!-- MainContents -->
        <div class="mainContents">
        	<div class="mainContents__left">
        		
        		<!-- WorkCheck -->
        		<div class="workCheck">
		            <div class="contents__title">근무체크</div>
		            <div class="contents__box">
		                <div class="box__date">10월 26일 (목)</div>
		                <div class="box__timeline">
		                    <div class="timeline__time">15:03:48</div>
		                    <div class="timeline__status">출근전</div>
		                </div>
		                <div class="box__commute">
		                    <div class="commute__work">
		                        <div class="work__text">출근하기</div>
		                        <div class="work__time">00:00:00</div>
		                    </div>
		                    <div class="contents__line"></div>
		                    <div class="commute__work">
		                        <div class="work__text">퇴근하기</div>
		                        <div class="work__time">00:00:00</div>
		                    </div>
		                </div>
		                <div class="box__btns">
		                    <div class="btns__line">
		                        <button>업무</button>
		                        <button>외출</button>
		                    </div>
		                    <div class="btns__line">
		                        <button>회의</button>
		                        <button>외근</button>
		                    </div>
		                </div>
		            </div>
		        </div>
		        
		        <!-- Approval -->
                <div class="approval">
		            <div class="contents__title">전자결재</div>
		            <div class="contents__box">
		                <div class="box__btns">
		                    <div class="btns__line">
		                        <button>대기</button>
		                        <button>확인</button>
		                    </div>
		                    <div class="btns__line">
		                        <button>예정</button>
		                        <button>진행</button>
		                    </div>
		                </div>
		            </div>
		        </div>
		        
		        <!-- Mail -->
		        <div class="mail">
		            <div class="contents__title">메일함 바로가기</div>
		            <div class="contents__box">
		                <div class="box__mailTitle">받은 메일함</div>
		                <div class="box__mailTitle">예약 메일함</div>
		                <hr></hr>
		                <div class="box__mailTitle">오늘 온 메일함</div>
		                <div class="box__mailTitle">중요 메일함</div>
		            </div>
		        </div>
		        
        	</div>
        	<div class="mainContents__right">
        	
        		<!-- Schedule -->
        		<div class="schedule">
		            <div class="contents__title">일정</div>
		            <div class="contents__box">
		                <div class="box__calendar"></div>
		                <hr></hr>
		                <div class="box__scheduleList">
		                    {planDateNum.map((e, i) => (
		                        <div key={i} class="scheduleList__item">
		                            <div class="item__date">
		                                <div class="date__dayNum">{e}</div>
		                                <div class="date__dayText">{planDateText[i]}</div>
		                            </div>
		                            <div class="contents__line"></div>
		                            <div class="item__plan">
		                                <div class="plan__title">{planTitle[i]}</div>
		                                <div class="plan__time">{planTime[i]}</div>
		                            </div>
		                        </div>
		                    ))}
		                </div>
		            </div>
		        </div>
		        
        	</div>
        </div>
	</div>
</body>
</html>