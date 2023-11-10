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
<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet" href="/css/humanresources/hrMain.css">
<script type="text/javascript" src="/js/humanresources/hrMain.js"></script>
</head>
<body>
<%@ include file="../commons/header.jsp" %>
	<div class="container">
		<%@ include file="../humanresources/hrNaviBar.jsp" %>
		<div class="humanResourcesMain">
            <div class="yearWorkInfo">
                <div class="yearWorkInfo__title title">
                    올해 근무 정보
                </div>
                <div class="yearWorkInfo__confBox confBox">
                    <div class="yearWorkInfo__conf attendance">
                        <div class="conf__title">
                            <i class="fa-solid fa-list"></i>
                            근태현황
                        </div>
                        <div class="conf_detail">
                            <div>
                                <div class="detail__title">지각</div>
                                <div class="detail_conf">1회</div>
                            </div>
                            <div>
                                <div class="detail__title">조기퇴근</div>
                                <div class="detail_conf">1회</div>
                            </div>
                            <div>
                                <div class="detail__title">퇴근 미체크</div>
                                <div class="detail_conf">1회</div>
                            </div>
                            <div>
                                <div class="detail__title">결근</div>
                                <div class="detail_conf">1회</div>
                            </div>
                        </div>
                    </div>
                    <div class="yearWorkInfo__conf vacation">
                        <div class="conf__title">
                            <i class="fa-solid fa-calendar-check"></i>
                            휴가현황
                        </div>
                        <div class="conf_detail">
                            <div>
                                <div class="detail__title">잔여 휴가</div>
                                <div class="detail_conf">20일</div>
                            </div>
                            <div>
                                <div class="detail__colortitle"><a href="">휴가 현황</a></div>
                                <div class="detail_conf"><button class="whiteBtn">휴가 신청</button></div>
                            </div>
                        </div>
                    </div>
                    <div class="yearWorkInfo__conf officeHour">
                        <div class="conf__title">
                            <i class="fa-solid fa-clock"></i>
                            근무시간
                        </div>
                        <div class="conf_detail">
                            <div>
                                <div class="detail__title">근무일수</div>
                                <div class="detail_conf">2일</div>
                            </div>
                            <div>
                                <div class="detail__title">총 근무시간</div>
                                <div class="detail_conf">7시간 18분</div>
                            </div>
                            <div>
                                <div class="detail__title">보정 평균</div>
                                <div class="detail_conf">7시간 18분</div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="toDayWorkInfo">
                <div class="toDayWorkInfo__title title">
                    오늘 근무 현황
                </div>
                <div class="toDayWorkInfo__confBox confBox">
                    <div class="toDayWorkInfo__conf workPlan">
                        <div class="conf__title">
                            <i class="fa-solid fa-calendar-check"></i>
                            근무계획
                        </div>
                        <div class="conf_detail">
                            <div class="calendar">
                                <div class="month">
                                    10월
                                    <div class="day">
                                        <div class="day__num">30</div>
                                        <div class="day__week">월요일</div>
                                    </div>
                                </div>

                            </div>
                            <div class="workingTime">
                                <div class="">9시출근</div>
                                <div class="">09:00 ~ 18:00 (소정 8시간)</div>
                            </div>
                            <div class="workingPlan">
                                <div class="detail__colortitle"><a href="">내근무계획</a></div>
                                <div class="detail_conf"><button class="whiteBtn">연장근무신청</button><button
                                        class="whiteBtn">휴(무)일 근로신청</button></div>
                            </div>
                        </div>
                    </div>
                    <div class="toDayWorkInfo__conf workCheck">
                        <div class="conf__title">
                            <i class="fa-solid fa-clock"></i>
                            근무체크
                        </div>
                        <div class="conf_detail">
                            <div class="nowTime">
                                20 : 07 : 43
                                <button class="grayBtn">근무 종료</button>
                            </div>
                            <div class="commuteBtns">
                                <div class="commuteBtns__title">
                                    출근하기
                                    <div class="commuteBtns__time">00:00:00</div>
                                </div>
                                <div class="commuteBtns__title">
                                    퇴근하기
                                    <div class="commuteBtns__time">00:00:00</div>
                                </div>
                            </div>
                            <div class="workingStatusBtns">
                                <div><button class="whiteBtn">업무</button><button class="whiteBtn">외출</button></div>
                                <div><button class="whiteBtn">회의</button><button class="whiteBtn">외근</button></div>
                            </div>
                        </div>
                    </div>
                    <div class="toDayWorkInfo__conf workStatus">
                        <div class="conf__title">
                            <i class="fa-solid fa-desktop"></i>
                            근무현황
                        </div>
                        <div class="conf_detail">
                            <div class="commuteTable">
                                <div class="commuteTable__row">
                                    <div class="commute__left">
                                        <div class="commute__circle"></div>
                                        <div class="commute__time">14:47</div>
                                        <div>출근</div>
                                    </div>
                                    <div class="commute__right">
                                        <div>출근</div>
                                    </div>
                                </div>
                                <div class="commuteTable__row">
                                    <div class="commute__left">
                                        <div class="commute__circle"></div>
                                        <div class="commute__time">14:47</div>
                                        <div>업무</div>
                                    </div>
                                </div>
                                <div class="commuteTable__row">
                                    <div class="commute__left">
                                        <div class="commute__circle"></div>
                                        <div class="commute__time">14:47</div>
                                        <div>외출</div>
                                    </div>
                                </div>
                                <div class="commuteTable__row">
                                    <div class="commute__left">
                                        <div class="commute__circle"></div>
                                        <div class="commute__time">14:47</div>
                                        <div>퇴근</div>
                                    </div>
                                    <div class="commute__right">
                                        <div>퇴근</div>
                                    </div>
                                </div>
                            </div>
                            <div class="shiftEditBtn">
                                <button class="whiteBtn">근무체크 수정</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
	</div>
</body>
</html>