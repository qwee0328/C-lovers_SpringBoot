<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<link rel="stylesheet" href="/css/home.css">
</head>
<body>
	<%@ include file="./commons/header.jsp"%>

	<div class="container">

	<!-- 채팅 테스트용 conflict 날 시 지워도 상관없음. -->
	<button id="goChat">채팅 열기</button>
	
	<button><a href="/members/goLogin">로그인</a></button>
	<script>
		$("#goChat").on("click",function(){
			let option ="height=700, width=400";
	        let openUrl = "/chat/goMain";
	        window.open(openUrl,"chatMain",option);
		})
	</script>
	<!-- 채팅 테스트용 여기까지임.-->
	
		<!-- MainNavi -->
		<div class="mainNavi">
			<div class="mainNavi__naviItems">
				
				<div class="naviItems__naviItem">
					<div class="naviItem__itemCurcle">
						<div class="itemCurcle__Icon">
							<i class="fa-regular fa-envelope"></i>
						</div>
					</div>
					<div class="naviItem__title">메일</div>
				</div>
				<div class="naviItems__naviItem">
					<div class="naviItem__itemCurcle">
						<div class="itemCurcle__Icon">
							<i class="fa-regular fa-clipboard"></i>
						</div>
					</div>
					<div class="naviItem__title">게시판</div>
				</div>
				<div class="naviItems__naviItem">
					<div class="naviItem__itemCurcle">
						<div class="itemCurcle__Icon">
							<i class="fa-regular fa-calendar"></i>
						</div>
					</div>
					<div class="naviItem__title">일정</div>
				</div>
				<div class="naviItems__naviItem">
					<div class="naviItem__itemCurcle">
						<div class="itemCurcle__Icon">
							<i class="fa-regular fa-address-book"></i>
						</div>
					</div>
					<div class="naviItem__title">주소록</div>
				</div>
				<div class="naviItems__naviItem">
					<div class="naviItem__itemCurcle">
						<div class="itemCurcle__Icon">
							<i class="fa-regular fa-clock"></i>
						</div>
					</div>
					<div class="naviItem__title">예약</div>
				</div>
				<div class="naviItems__naviItem">
					<div class="naviItem__itemCurcle">
						<div class="itemCurcle__Icon">
							<i class="fa-solid fa-sitemap"></i>
						</div>
					</div>
					<div class="naviItem__title">인사</div>
				</div>
				<div class="naviItems__naviItem">
					<div class="naviItem__itemCurcle">
						<div class="itemCurcle__Icon">
							<i class="fa-regular fa-file-lines"></i>
						</div>
					</div>
					<div class="naviItem__title">전자결재</div>
				</div>
			</div>
		</div>
		
		<script>
			$(document).on("click", ".naviItem__itemCurcle", function() {
				if($(this).siblings().html() == "메일") {
					location.href = "/mail";
				}
			})
			
			$(document).on("click", ".naviItem__itemCurcle", function() {
				if($(this).siblings().html() == "일정") {
					location.href = "/schedule";
				}
			})
		</script>

		<!-- MainContents  -->
		<div class="mainContents">
			<div class="mainContents__left">

				<!-- WorkCheck -->
				<div class="workCheck">
					<div class="mainContents__title">근무체크</div>
					<div class="mainContents__contentBox">
						<div class="contentBox__date">10월 26일 (목)</div>
						<div class="contentBox__timeline">
							<div class="timeline__time">15:03:48</div>
							<div class="timeline__status">출근전</div>
						</div>
						<div class="contentBox__commute">
							<div class="commute__work">
								<div class="work__text">출근하기</div>
								<div class="work__time">00:00:00</div>
							</div>
							<div class="mainContents__line"></div>
							<div class="commute__work">
								<div class="work__text">퇴근하기</div>
								<div class="work__time">00:00:00</div>
							</div>
						</div>
						<div class="contentBox__btns">
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
					<div class="mainContents__title">전자결재</div>
					<div class="mainContents__contentBox">
						<div class="contentBox__btns">
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
					<div class="mainContents__title">메일함 바로가기</div>
					<div class="mainContents__contentBox">
						<div class="contentBox__mailTitle">받은 메일함</div>
						<div class="contentBox__mailTitle">예약 메일함</div>
						<hr></hr>
						<div class="contentBox__mailTitle">오늘 온 메일함</div>
						<div class="contentBox__mailTitle">중요 메일함</div>
					</div>
				</div>

			</div>
			<div class="mainContents__right">

				<!-- Schedule -->
				<div class="schedule">
					<div class="mainContents__title">일정</div>
					<div class="mainContents__contentBox">
						<div class="contentBox__calendar"></div>
						<hr></hr>
						<div class="contentBox__scheduleList">
							<c:forEach var="test" begin="1" end="3" step="1">
								<div class="scheduleList__scheduleItem">
									<div class="scheduleItem__date">
										<div class="date__dayNum">${test }</div>
										<div class="date__dayText">${test }</div>
									</div>
									<div class="mainContents__line"></div>
									<div class="scheduleItem__plan">
										<div class="plan__title">${test }</div>
										<div class="plan__time">${test }</div>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>