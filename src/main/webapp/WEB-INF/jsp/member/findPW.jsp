<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="/css/member/findPW.css">
<title>비밀번호 찾기</title>
</head>
<body class="findPw_body">
    <div class="container">
        <div class="findPwBox">
            <div class="findPwBox__inner">
                <div class="findPwBox__logoBox">
                    <div class="logo">
                        <div class="logo__desc-pw">비밀번호 찾기</div>
                        <div class="logo__desc-des">사번과 인증 코드를 받을 이메일을 입력해주세요.</div>
                    </div>
                </div>

                <div class="findPwBox__idBox">
                    <input class="idBox" type="text" placeholder="사번">
                </div>

                <div class="findPwBox__emailBox">
                    <div class="findPwBox__email-inner">
                        <div class="emailBox">
                            <input class="in-emailBox" type="text" id="email" placeholder="이메일">
                        </div>
                        <div class="authBtnBox">
                            <button class="authBtn">인증코드 발송</button>
                        </div>
                    </div>
                </div>
                <div class="findPwBox__emailChk">
                    	
                </div>

                <div class="findPwBox__authBox">
                    <div class="findPwBox_authBox-inner">
                        <div class="auth_codBox">
                            <input class="auth_cod" type="text" placeholder="인증 코드">
                        </div>
                        <div class="auth_chkBox">
                            <button class="auth_chk">확인</button>
                        </div>
                    </div>
                </div>

                <!-- <div class="findPwBox__authChk">
                    <div>인증되었습니다</div>
                </div> -->

                <div class="findPwBox__line">

                </div>

                <div class="findPwBox__changePwBox">
                    <input type="password" class="changePwBox" id="pw" placeholder="변경할 비밀번호">
                </div>

                <div class="findPwBox__changePwChkBox">
                    <input type="password" class="changePwChkBox" id="pw_re" placeholder="비밀번호 확인">
                </div>
                
                <div class="findPwBox__pwChk">
                	
                </div>
                
                <div class="findPwBox__pwEq">
                	
                </div>

                <div class="findPwBox__changePwBtnBox">
                    <button class="changePwBtn">비밀번호 변경</button>
                </div>
            
            </div>
        </div>
    </div>
</body>

	<script>
// 		이메일 regex
		let emailRegex = /^[a-zA-Z0-9\_]+@[a-z]+\.[a-z]{2,3}$/;
		$("#email").keyup(function(e){
			console.log($("#email").val());
			result = emailRegex.test($("#email").val());
			console.log(result);
			
			if(!result){
				$(".findPwBox__emailChk").html("이메일 형식이 올바르지 않습니다.");
			}
			if(result){
				$(".findPwBox__emailChk").html("");
			}
		});
		
// 		비밀번호 regex
		let pwRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,16}$/;
		$("#pw").keyup(function(){
			console.log($("#pw").val());
			result = pwRegex.test($("#pw").val());
			console.log(result);
			
			if(!result){
				$(".findPwBox__pwChk").html("비밀번호의 형식이 올바르지 않습니다.").css({"font-size":"12px","color":"red"});
			}
			if(result){
				$(".findPwBox__pwChk").html("");
//		 		비밀번호와 비밀번호 확인이 일치하는지 확인
				$("#pw_re").keyup(function(){
					if($("#pw").val() != $("#pw_re").val()){
						$(".findPwBox__pwEq").html("비밀번호가 일치하지 않습니다.").css({"font-size":"12px","color":"red"});
					}else{
						$(".findPwBox__pwEq").html("");
					}
				});
				
				$("#pw").keyup(function(){
					if($("#pw").val() != $("#pw_re").val()){
						$(".findPwBox__pwEq").html("비밀번호가 일치하지 않습니다.").css({"font-size":"12px","color":"red"});
					}else{
						$(".findPwBox__pwEq").html("");
					}
				});
			}
		});
		

		
		
	</script>

</html>








