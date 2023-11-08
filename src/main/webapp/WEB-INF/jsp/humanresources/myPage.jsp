<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/humanresources/mypage.css">
<title>내 정보 관리</title>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
        <!-- 네비바 자리 -->
        <div class="naviBar">
            <div class="navi">네비</div>
        </div>

        <div class="mypageBox">
            <div class="mypageBox__inner">
                <div class="mypageBox__profileBox1">
                    <div class="profile1">
                        <span class="profileSetting">프로필 설정</span>
                    </div>
                </div>
                <div class="mypageBox__profileBox2">
                    <div class="profileBox2">
                        <div class="profileImageBox">
                            <div class="profileImage">
                                <img src="" alt="">
                            </div>
                        </div>
                        <div class="profileDesBox">
                            <div class="profileDesBox__idBox">
                                <div class="idKrBox">
                                    <div class="idKr">
                                        사번
                                    </div>
                                </div>
                                <div class="idNumBox">
                                    <div class="idNum">
                                        2019012312
                                    </div>
                                </div>
                            </div>
                            <div class="profileDesBox__nameBox">
                                <div class="nameKrBox">
                                    <div class="nameKr">
                                        이름
                                    </div>
                                </div>
                                <div class="nameNumBox">
                                    <div class="numeNum">
                                         ㅇㅇㅇ
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mypageBox__profileBox3">
                    <button type="button" class="profileBtn" >내 정보 관리</button>
                    <button type="button" class="profileBtnCancle">취소</button>
                    <button type="button" class="profileBtnSave">저장</button>
                </div>
            </div>
        </div>
    </div>
</body>

<script>
    $(".profileBtn").click(function(){
        $(".profileBtn").css("display","none");
        $(".profileBtnCancle").css("display","inline");
        $(".profileBtnSave").css("display","inline");
        
    });
</script>

</html>