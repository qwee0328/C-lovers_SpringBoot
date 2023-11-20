<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>electronicsignatureNaviBar</title>
<!-- <script src="https://code.jquery.com/jquery-3.7.1.js"></script> -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- css, js -->
<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet" href="/css/electronicsignature/electronicsignatureNaviBar.css">
<script src="/js/electronicsignature/electronicsignatureNaviBar.js"></script>
</head>
<body>
	 <div class="naviBar">
        <div class="naviBtnBox">
        	<div class="naviBtn" id="electronicSignatureWriteBtn">
	            <i class="fa-solid fa-plus naviBtn__icon"></i>
	            <div class="naviBtn__text">작성하기</div>
	            <input type="hidden" id="location" value="write">
            </div>
        </div>
    
	    <div class="naviScrollBox">
	        <div class="naviConp">
	            <div class="naviConp__icon">
	                <i class="fa-solid fa-chevron-up"></i>
	            </div>
	            <div class="naviConp__title">진행 중인 문서</div>
	            <input type="hidden" class="naviLocation" value="progressDocument">
	        </div>
	        <div class="innerNavi progressDocument">  
		        <div class="naviConp progressTotal">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-file"></i>
		            </div>
		            <div class="naviConp__title">전체</div>
		            <input type="hidden" class="naviLocation" value="progressTotal">
		        </div>
		        <div class="naviConp">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-hourglass-start"></i>
		            </div>
		            <div class="naviConp__title">대기</div>
		            <input type="hidden" class="naviLocation" value="progressWait">
		        </div>
		        <div class="naviConp">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-check"></i>
		            </div>
		            <div class="naviConp__title">확인</div>
		            <input type="hidden" class="naviLocation" value="progressCheck">
		        </div>
		        <div class="naviConp">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-spinner"></i>
		            </div>
		            <div class="naviConp__title">진행</div>
		            <input type="hidden" class="naviLocation" value="progress">
		        </div>
	        </div>
	        
	        <div class="naviConp">
	            <div class="naviConp__icon">
	                <i class="fa-solid fa-chevron-up"></i>
	            </div>
	            <div class="naviConp__title">문서함</div>
	            <input type="hidden" class="naviLocation" value="document">
	        </div>
	        <div class="innerNavi document">  
		        <div class="naviConp documentTotal">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-file"></i>
		            </div>
		            <div class="naviConp__title">전체</div>
		            <input type="hidden" class="naviLocation" value="documentTotal">
		        </div>
		        <div class="naviConp">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-pen"></i>
		            </div>
		            <div class="naviConp__title">기안</div>
		            <input type="hidden" class="naviLocation" value="documentDrafting">
		        </div>
		        <div class="naviConp">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-signature"></i>
		            </div>
		            <div class="naviConp__title">결재</div>
		            <input type="hidden" class="naviLocation" value="documentApproval">
		        </div>
		        <div class="naviConp">
		            <div class="naviConp__icon">
		                <i class="fa-solid fa-ban"></i>
		            </div>
		            <div class="naviConp__title">반려</div>
		            <input type="hidden" class="naviLocation" value="documentRejection">
		        </div>
	        </div>
	        
	        <div class="naviConp">
	            <div class="naviConp__icon">
	                <i class="fa-solid fa-box-archive"></i>
	            </div>
	            <div class="naviConp__title">임시저장</div>
	            <input type="hidden" class="naviLocation" value="temporary">
	        </div>
        </div>
        
        <input type="hidden" id="currentMenu" value="${currentMenu }" />
	</div>
</body>
</html>