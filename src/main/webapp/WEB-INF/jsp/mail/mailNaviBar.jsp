<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mailNaviBar</title>
<!-- <script src="https://code.jquery.com/jquery-3.7.1.js"></script> -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />

<!-- css, js -->
<link rel="stylesheet" href="/css/commons/basicSetting.css">
<link rel="stylesheet" href="/css/mail/mailNaviBar.css">
<script src="/js/mail/mailNaviBar.js"></script>
</head>
<body>
	 <div class="naviBar">
        <div class="naviBtn">
            <i class="fa-solid fa-plus naviBtn__icon"></i>
            <div class="naviBtn__text">편지 쓰기</div>
            <input type="hidden" id="location" value="send">
        </div>
    
        <div class="naviConp">
            <div class="naviConp__icon">
                <i class="fa-solid fa-inbox"></i>
            </div>
            <div class="naviConp__title">받은 편지함</div>
            <input type="hidden" class="naviLocation" value="inBox">
        </div>    
        <div class="naviConp">
            <div class="naviConp__icon">
                <i class="fa-solid fa-paper-plane"></i>
            </div>
            <div class="naviConp__title">보낸 편지함</div>
            <input type="hidden" class="naviLocation" value="sentBox">
        </div>
        <div class="naviConp">
            <div class="naviConp__icon">
                <i class="fa-solid fa-box-archive"></i>
            </div>
            <div class="naviConp__title">임시 편지함</div>
            <input type="hidden" class="naviLocation" value="tempBox">
        </div>
        <div class="naviConp">
            <div class="naviConp__icon">
                <i class="fa-solid fa-clock"></i>
            </div>
            <div class="naviConp__title">보낼 편지함</div>
            <input type="hidden" class="naviLocation" value="outBox">
        </div>
        <div class="naviConp">
            <div class="naviConp__icon">
                <i class="fa-solid fa-trash"></i>
            </div>
            <div class="naviConp__title">휴지통</div>
            <input type="hidden" class="naviLocation" value="trash">
        </div>
        
        <input type="hidden" id="currentMenu" value="${currentMenu }" />
	</div>
</body>
</html>