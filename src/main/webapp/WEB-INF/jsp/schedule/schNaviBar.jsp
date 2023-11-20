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
<link rel="stylesheet" href="/css/schedule/schNaviBar.css">
<script type="text/javascript" src="/js/schedule/schNaviBar.js"></script>
</head>
<body>
	<div class="naviBar">
	   <div class="naviBar__applyBtns">
	       <button id="scheduleAddBtn"><i class="fa-solid fa-plus"></i>일정 추가</button>
	   </div>
	   <div class="naviScrollBox">
	   		<input type="hidden" value="${currentMenu}" id="abCurrentMenu">

	        <div class="naviBar__personalCalendar">
	           <div class="naviConp toggleMenu" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">내 캘린더</div>
	               <div class="naviConp__addTag" data-isShare="0"><i class="fa-solid fa-plus"></i></div>
	           </div>
	        </div>
	        <div class="naviBar__sharedCalendar">
	           <div class="naviConp toggleMenu" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">공유 캘린더</div>
	               <div class="naviConp__addTag" data-isShare="1"><i class="fa-solid fa-plus"></i></div>
	           </div>
	       </div>
	       
	       <div class="naviBar__trashCalendar">
	           <div class="naviConp toggleMenu" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">휴지통</div>
	           </div>
	       </div>
	       
	      <!--  <div class="naviConp toggleInner trashMenuNavi" authority="trash" data-title="휴지통" data-id="-3">
               <div class="naviConp__icon"><i class="fa-solid fa-trash"></i></div>
               <div class="naviConp__title naviConp__titleMini">휴지통</div>
           </div> -->
	    </div>
	</div>
</body>
</html>