<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="/css/humanresources/mypage.css">
<title>내 정보 관리</title>
</head>
<body>
	<%@ include file="../commons/header.jsp" %>
	
	<div class="container">
        <!-- 네비바 자리 -->
        <div class="naviBar">
        	<%@ include file="../humanresources/hrNaviBar.jsp" %>
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
                        <div class="profileImageBox align_center">
                            <img src="/uploads/${list.profile_img }" alt="프로필 사진" class="profileImage">
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
                                    <input type="hidden" value="${loginID }" class="inputIdNum">
                                </div>
                            </div>
                            <div class="profileDesBox__nameBox">
                                <div class="nameKrBox">
                                    <div class="nameKr">
                                        이름
                                    </div>
                                </div>
                                <div class="nameNumBox">
                                    <div class="nameNum">
                                        ${list.name }
                                    </div>
                                    <input type="hidden" value="${list.name }" class="inputNameNum">
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 클릭시 변경되는 곳 -->
                    <form action="/humanResources/update" method="post" enctype="multipart/form-data">
	                    <div class="profileBoxClick">
	                        <div class="profileBox">
	                             <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>프로필 사진</span>
	                                </div>
	                                <div class="profileImageBox">
	                                	<div>
		                                    <div>
		                                    	<img src="/uploads/${list.profile_img }" id="profile_img" name="profile_img" alt="프로필 사진" class="profileImage">
		                                    </div>
                                            <div style="padding-top: 17px;">
                                                <label for="input-image" class="profileImageBtn">프로필 수정</label>
		                                        <input type="file" id="input-image" name="profile_img" value="프로필 수정" onchange="setThumbnail(event);">
                                            </div>
	                                	</div>
	                                </div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>이름</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div>${list.name }</div>
	                                </div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>소속</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div>${list.task_name }</div>
	                                </div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>직위</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div>${list.job_name }</div>
	                                </div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>직무</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div>${list.dept_name }</div>
	                                </div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>사내전화</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div><input type="text" name="company_phone" id="company" value=${list.company_phone }></div>
	                                </div>
	                                <!-- 사내전화 regex -->
									<div class="profileRexBox">
										<div class="company__regex">

										</div>
									</div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>휴대전화</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div><input type="text" name="phone" id="phone" value=${list.phone }></div>
	                                </div>
	                                <!-- 휴대전화 regex -->
									<div class="profileRexBox">
										<div class="phone__regex">

										</div>
									</div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>개인이메일</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div><input type="text" name="email" id="email" value=${list.email }></div>
	                                </div>
	                                <!-- 이메일 regex -->
									<div class="profileRexBox">
										<div class="email__regex">

										</div>
									</div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>사번</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div>${loginID }</div>
	                                </div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>입사일</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div>${list.formatHireDate }</div>
	                                </div>
	                            </div>
	
	                            <div class="profileBox__inner">
	                                <div class="profileLeft">
	                                    <span>생년월일</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div>
	                                        ${list.formatBirth }
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
                </form>
            </div>
        </div>
    </div>
</body>

	<script>
	
	
	// 내 정보 관리 클릭
    $(".profileBtn").click(function () {
        
    	// 비밀번호가 사원의 이름과 같으면 변경하는 페이지로 이동시킴
        $.ajax({
        	url:"/humanResources/recommendChangPw",
        	type:"POST",
        	data : {
        		id : $(".inputIdNum").val(),
        		name : $(".inputNameNum").val()
        	}
        }).done(function(resp){
        	console.log(resp);
        	if(resp == "변경추천"){
        		alert("비밀번호를 변경해주세요.");
        		location.href="/humanResources/goChangePw";
        	}else{
        		$(".profileBtn").css("display", "none");
                $(".profileBtnCancle").css("display", "inline");
                $(".profileBtnSave").css("display", "inline");
                $(".profileBoxClick").css("display","inline");

                $(".mypageBox__profileBoxTop").find(".profileSetting").html("내 정보 관리");
                $(".profileBoxMid").css("display", "none");
        	}
        })
    });

    // 취소 클릭
    $(".profileBtnCancle").click(function () {
        $(".profileBtn").css("display", "inline");
        $(".profileBtnCancle").css("display", "none");
        $(".profileBtnSave").css("display", "none");

        $(".profileBoxMid").css("display","flex");
        $(".profileBoxClick").css("display","none");
    })
    
//    미리보기
	function setThumbnail(event){
		var reader = new FileReader();

		reader.onload = function(event){
			$("#profile_img").attr("src",event.target.result);
			console.log(event.target.result);
		}

		reader.readAsDataURL(event.target.files[0]);
	}
    
    let regexResult=true;
	//     Regex검사
	// 		이메일 regex
	let emailRegex = /^[a-zA-Z0-9\_]+@[a-z]+\.[a-z]{2,3}$/;
	$("#email").keyup(function (e) {
		result = emailRegex.test($("#email").val());

		if (!result) {
			$(".email__regex").html("이메일 형식이 올바르지 않습니다.").css({ "font-size": "12px", "color": "red" });
			regexResult = false;
		}
		if (result) {
			$(".email__regex").html("");
			regexResult=true;
		}
	});

	// 		휴대전화 regex
	let phoneRegex = /^(010)-?\d{3,4}-?\d{4}$/;
	$("#phone").keyup(function (e) {
		result = phoneRegex.test($("#phone").val());

		if (!result) {
			$(".phone__regex").html("전화번호 형식이 올바르지 않습니다.").css({ "font-size": "12px", "color": "red" });
			regexResult = false;
		}
		if (result) {
			$(".phone__regex").html("");
			regexResult=true;
		}
	});

	//		사내전화 regex -> 사내전화가 어떤 형식인지 몰라서 못함


	// 
	$(".profileBtnSave").click(function(){
		if (!regexResult) {
			alert("형식에 맞추어 다시 입력해주세요.");
			return;
		}
		
		$(".profileBtnSave").attr("type","submit");
		
	})
    
	</script>

</html>