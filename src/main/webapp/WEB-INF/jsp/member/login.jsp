<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/css/member/login.css">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<!-- 쿠키 관련 -->
<script src="https://cdn.jsdelivr.net/npm/js-cookie@3.0.5/dist/js.cookie.min.js"></script>
<script src="${contextPath}/resources/js/js.cookie.js"></script>

<title>로그인</title>
</head>
<body class="login_body">
	<div class="container">
		<div class="loginBox">
			<div class="loginBox__inner">
				<div class="loginBox__logo">
					<div class="logo">
						<span>C-lover</span>
					</div>
				</div>
				
					<div class="loginBox__saveBox">
						<div class="loginBox__save">
							<input class="saveBox" id="idSaveCheck" type="checkbox">
							<p>사번 저장</p>
						</div>
					</div>
					
					<div class="loginBox__idBox">
						<input class="idBox" name="id" id="id" type="text" placeholder="사번">
					</div>
					
					<div class="loginBox__pwBox">
						<input class="pwBox" name="pw" id="pw" type="password" placeholder="비밀번호">
					</div>
					
					<div class="loginFail">
						
					</div>
					
					<div class="loginBox__loginBtn">
						<button type="button" id="loginBtn" class="loginBtn">로그인</button>
					</div>
					
				<div class="loginBox__findPw">
					<button class="findPwBtn">비밀번호 찾기</button>
				</div>
			</div>
		</div>
	</div>
</body>
	<script>
		$("#loginBtn").on("click",function(){
			
//	 		login
			if($("#id").val() == ""){
				alert("사번을 입력해주세요.");
				return false;
			}
			if($("#pw").val()==""){
				alert("비밀번호를 입력해주세요.");
				return false;
			}
			
			$.ajax({
				url:"/members/login",
				type:"POST",
				data:{
					id:$("#id").val(),
					pw:$("#pw").val()
				}
			}).done(function(resp){
				console.log(resp);
				if(resp == false){
					$(".loginFail").html("로그인에 실패했습니다.");
				}else{
					location.href="/"
				}
			});
			
		});
	
// 		cookie
		$("#id").val(Cookies.get('key'));
    	if($("#id").val() != ""){
    		$("#idSaveCheck").attr("checked",true);
    	}
	    	
    	$("#idSaveCheck").change(function(){
    		
    		if($("#idSaveCheck").is(":checked")){
    			Cookies.set("key",$("#id").val(),{expires:7});
    		}else{
    			Cookies.remove("key");
    		}
    	});
    	
    	$("#userID").keyup(function(){
    		if($("#idSaveCheck").is(":checked")){
    			Cookies.set("key",$("#userId").val(),{expires:7});
    		}
    	});
    	
//     	비밀번호찾기 이동
    	$(".findPwBtn").click(function(){
    		location.href="/members/goFindPW";
    	})
    	
    	
	    </script>
</html>