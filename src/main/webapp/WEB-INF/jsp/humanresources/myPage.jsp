<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="/css/commons/basicSetting.css">
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
                        	<c:choose>
                        		<c:when test="${empty list.profile_img}">
                        			<img src="/assets/profile.png" alt="프로필 사진" class="profileImage">
                        		</c:when>
                        		<c:otherwise>
                        			<img src="/uploads/${list.profile_img }" alt="프로필 사진" class="profileImage">
                        		</c:otherwise>
                        	</c:choose>
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
		                                    	<c:choose>
					                        		<c:when test="${empty list.profile_img}">
					                        			<img src="/assets/profile.png" alt="프로필 사진" class="profileImage" name="profileImage">
					                        		</c:when>
					                        		<c:otherwise>
					                        			<img src="/uploads/${list.profile_img }" alt="프로필 사진" class="profileImage" name="profileImage">
					                        		</c:otherwise>
					                        	</c:choose>
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
	                                    <span>사내이메일</span>
	                                </div>
	                                <div class="profileRight">
	                                    <div><input type="text" name="company_email" id="company_email" value=${list.company_email }></div>
	                                </div>
	                                <div>
										<button id="emailDup" type="button">중복확인</button>
									</div>
	                                <!-- 아이디 regex -->
									<div class="profileRexBox">
										<div class="company_email__regex">

										</div>
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
	                                    	<c:if test="${list.formatBirth != '1900년 01월 01일'}">
	                                    		${list.formatBirth }
	                                    	</c:if>
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
	let emailResult = true;
	// 이메일 중복확인
	$("#emailDup").click(function(){
		$.ajax({
			url:"/humanResources/emailDup",
			data:{
				company_email : $("#company_email").val()
			}
		}).done(function(resp){
			console.log(resp);
			
			if(resp == 0){
				$(".company_email__regex").html("사용가능한 이메일입니다.").css({ "font-size": "12px", "color": "green" });
				emailResult = true;
			}else{
				$(".company_email__regex").html("이메일이 중복됩니다.").css({ "font-size": "12px", "color": "red" });
				emailResult = false;
			}
		})
	})
	
	// 이미지 임시저장
	let saveImg;
	
	// 내 정보 관리 클릭
    $(".profileBtn").click(function () {
    	// 이미지 임시저장
    	saveImg = $(".profileImage").attr("src");
       
  		$(".profileBtn").css("display", "none");
        $(".profileBtnCancle").css("display", "inline");
        $(".profileBtnSave").css("display", "inline");
        $(".profileBoxClick").css("display","inline");

        $(".mypageBox__profileBoxTop").find(".profileSetting").html("내 정보 관리");
        $(".profileBoxMid").css("display", "none");
    });
	


    // 취소 클릭
    $(".profileBtnCancle").click(function () {
    	// 취소클릭하면 임시저장 해놓은 이미지 보여주기
    	$(".profileImage").attr("src",saveImg);
    	
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
			$(".profileImage").attr("src",event.target.result);
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

	//		아이디 regex 대소문자랑 숫자만 포함해서 8글자 이상
	let idRegex = /^[a-zA-Z0-9]{7,15}$/;
	$("#company_email").keyup(function(e){
		result = idRegex.test($("#company_email").val());
		
		emailResult = false;
		
		if (!result) {
			$(".company_email__regex").html("아이디 형식이 올바르지 않습니다.").css({ "font-size": "12px", "color": "red" });
			regexResult = false;
		}
		if (result) {
			$(".company_email__regex").html("");
			regexResult=true;
		}
	});

	
	// 
	$(".profileBtnSave").click(function(){
		if (!regexResult) {
			alert("형식에 맞추어 다시 입력해주세요.");
			return;
		}
		if(!emailResult){
			alert("이메일 중복 여부를 다시 확인해주세요.");
			return;
		}
		
		$(".profileBtnSave").attr("type","submit");
		
	})
    
	</script>

</html>