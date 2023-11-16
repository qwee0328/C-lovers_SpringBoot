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
<link rel="stylesheet" href="/css/addressbook/abNaviBar.css">
<script type="text/javascript" src="/js/addressbook/abNaviBar.js"></script>
</head>
<body>
	<div class="naviBar">
	   <div class="naviBar__applyBtns">
	       <button id="addressAddBtn"><i class="fa-solid fa-plus"></i>주소 추가</button>
	       <div class="naviConp">
	           <div class="naviConp__icon"><i class="fa-solid fa-star"></i></div>
	           <div class="naviConp__title">중요 주소록</div>
	       </div>
	   </div>
	   <div class="naviScrollBox">
	   		<input type="hidden" value="${currentMenu}" id="abCurrentMenu">
	        <div class="naviBar__personalAddress">
	           <div class="naviConp personalToggle" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">개인 주소록</div>
	           </div>
	           <div class="naviConp toggleInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-address-book"></i></div>
	               <div class="naviConp__title naviConp__titleMini">개인 전체</div>
	           </div>
	        </div>
	        <div class="naviBar__sharedAddress">
	           <div class="naviConp sharedToggle" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">공유 주소록</div>
	           </div>
	           <div class="naviConp sharedInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-address-book"></i></div>
	               <div class="naviConp__title naviConp__titleMini">공유 전체</div>
	           </div>
	       </div>
	    </div>
	</div>
</body>
</html>