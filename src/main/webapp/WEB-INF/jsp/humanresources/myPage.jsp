<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                <div class="mypageBox__profileBoxTop">
                    <div class="profileBoxTop">
                        <span class="profileSetting">프로필 설정</span>
                    </div>
                </div>
                <div class="mypageBox__profileBoxMid">
                    <div class="profileBoxMid">
                        <div class="profileImageBox">
                            <img src="/assets/profile.png" alt="" class="profileImage">
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
                                        ${loginID }
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
                    <!-- 클릭시 변경되는 곳 -->
                    <div class="profileBoxClick">
                        <div class="profileBox">
                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>프로필 사진</span>
                                </div>
                                <div class="profileImageBox">
                                	<div>
	                                    <div>
	                                    	<img src="/assets/profile.png" alt="프로필 사진" class="profileImage">
	                                    </div>
	                                    <button type="button" class="profileImageBtn">프로필 수정</button>
                                	</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>이름</span>
                                </div>
                                <div class="profileRight">
                                    <div>ㅇㅇㅇ</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>소속</span>
                                </div>
                                <div class="profileRight">
                                    <div>하이웍스 산업</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>근로형태</span>
                                </div>
                                <div class="profileRight">
                                    <div>일반직</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>직위</span>
                                </div>
                                <div class="profileRight">
                                    <div>대표이사</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>직무</span>
                                </div>
                                <div class="profileRight">
                                    <div>관리</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>사내전화</span>
                                </div>
                                <div class="profileRight">
                                    <div><input type="text"></div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>휴대전화</span>
                                </div>
                                <div class="profileRight">
                                    <div><input type="text"></div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>개인이메일</span>
                                </div>
                                <div class="profileRight">
                                    <div><input type="text"></div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>사번</span>
                                </div>
                                <div class="profileRight">
                                    <div>20191234231</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>입사일</span>
                                </div>
                                <div class="profileRight">
                                    <div>2023년 11월 11일</div>
                                </div>
                            </div>

                            <div class="profileBox__inner">
                                <div class="profileLeft">
                                    <span>생년월일</span>
                                </div>
                                <div class="profileRight">
                                    <div>
                                        2000년 11월 11일
                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>
                </div>
                <div class="mypageBox__profileBoxBottom">
                    <button type="button" class="profileBtn">내 정보 관리</button>
                    <button type="button" class="profileBtnCancle">취소</button>
                    <button type="button" class="profileBtnSave">저장</button>
                </div>
            </div>
        </div>
    </div>
</body>

	<script>
		//내 정보 관리 클릭
		$(".profileBtn").click(function () {
		    $(".profileBtn").css("display", "none");
		    $(".profileBtnCancle").css("display", "inline");
		    $(".profileBtnSave").css("display", "inline");
		    $(".profileBoxClick").css("display","inline");
		    
		    console.log($(".mypageBox__profileBoxTop").find(".profileSetting"));
		    $(".mypageBox__profileBoxTop").find(".profileSetting").html("내 정보 관리");
		    $(".profileBoxMid").css("display", "none");
		
		    // 에이젝스로 데이터 불러오기
		    $.ajax({
		    	
		    }).done(function(resp){
		    	
		    	});
		    }
		
		// 취소 클릭
		$(".profileBtnCancle").click(function () {
		    $(".profileBtn").css("display", "inline");
		    $(".profileBtnCancle").css("display", "none");
		    $(".profileBtnSave").css("display", "none");
		
		    $(".profileBoxMid").css("display","flex");
		    $(".profileBoxClick").css("display","none");
		})
	</script>

</html>