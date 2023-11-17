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
<link rel="stylesheet" href="/css/humanresources/hrNaviBar.css">
<script type="text/javascript" src="/js/humanresources/hrNaviBar.js"></script>
</head>
<body>
	<div class="naviBar">
	   <div class="naviBar__applyBtns">
	       <button id="vacationAppBtn"><i class="fa-solid fa-plus"></i>휴가신청</button>
	       <!-- <div class="naviConp">
	           <div class="naviConp__icon"><i class="fa-solid fa-plus"></i></div>
	           <div class="naviConp__title">시차출퇴근제 신청</div>
	       </div> -->
	   </div>
	   <div class="naviScrollBox">
	   		<input type="hidden" value="${currentMenu}" id="hrCurrentMenu">
	        <div class="naviBar__regularEmployeeMenu">
	           <div class="naviConp regularToggle" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">내 근무</div>
	           </div>
	           <div class="naviConp toggleInner" id="vacationWork">
	               <div class="naviConp__icon"><i class="fa-solid fa-calendar"></i></div>
	               <div class="naviConp__title">휴가/근무</div>
	           </div>
	           <div class="naviConp toggleInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-desktop"></i></div>
	               <div class="naviConp__title">근무 현황</div>
	           </div>
	           <div class="naviConp toggleInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-chart-simple"></i></div>
	               <div class="naviConp__title">부서 근무 현황</div>
	           </div>
	           <div class="naviConp" id="employeeInfo">
	               <div class="naviConp__icon"><i class="fa-solid fa-sitemap"></i></div>
	               <div class="naviConp__title">임직원 정보</div>
	           </div>
	           <div class="naviConp" id = "profileSettings">
	               <div class="naviConp__icon"><i class="fa-regular fa-id-badge"></i></div>
	               <div class="naviConp__title">프로필 설정</div>
	           </div>
	        </div>
	        <div class="naviBar__managerMenu">
	           <div class="naviConp shiftToggle" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">근무 관리</div>
	           </div>
	           <div class="naviConp shiftInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-calendar"></i></div>
	               <div class="naviConp__title">근무 계획 수립</div>
	           </div>
	           <div class="naviConp shiftInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-chart-pie"></i></div>
	               <div class="naviConp__title">근무 통계</div>
	           </div>
	           <div class="naviConp shiftInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-building"></i></div>
	               <div class="naviConp__title">전사 근무관리</div>
	           </div>
	           <div class="naviConp org_ExeToggle" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">조직/임원 관리</div>
	           </div>
	           <div class="naviConp org_ExeInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-user-group"></i></div>
	               <div class="naviConp__title">조직 관리</div>
	           </div>
	           <div class="naviConp org_ExeInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-user-group"></i></div>
	               <div class="naviConp__title">임직원 관리</div>
	           </div>
	           <div class="naviConp org_ExeInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-address-card"></i></div>
	               <div class="naviConp__title">직위/직무 설정</div>
	           </div>
	           <div class="naviConp hrToggle" toggleView="true">
	               <div class="naviConp__icon"><i class="fa-solid fa-chevron-up"></i></div>
	               <div class="naviConp__title">인사설정</div>
	           </div>
	           <div class="naviConp hrInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-gear"></i></div>
	               <div class="naviConp__title">기본설정</div>
	           </div>
	           <div class="naviConp hrInner">
	               <div class="naviConp__icon"><i class="fa-solid fa-user-group"></i></div>
	               <div class="naviConp__title">인사관리자</div>
	           </div>
	       </div>
	    </div>
	</div>
</body>
</html>