<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="/css/member/findPW.css">
<title>비밀번호 변경</title>
</head>
<body class="findPw_body">
    <div class="container">
        <div class="findPwBox">
            <div class="findPwBox__inner">
                <div class="findPwBox__logoBox">
                    <div class="logo">
                        <div class="logo__desc-pw">비밀번호 변경</div>
                        <div class="logo__desc-des">변경할 비밀번호를 입력해주세요</div>
                    </div>
                </div>
				<form action="/members/changePW" method="post">
                	<div class="findPwBox__changePwBox">
						<input type="password" class="changePwBox" id="pw" name="pw"
							placeholder="변경할 비밀번호">
					</div>
	
					<div class="findPwBox__changePwChkBox">
						<input type="password" class="changePwChkBox" id="pw_re"
							placeholder="비밀번호 확인">
					</div>
	
					<div class="findPwBox__pwChk"></div>
	
					<div class="findPwBox__pwEq"></div>
	
					<div class="findPwBox__changePwBtnBox">
						<button type="button" class="changePwBtn">비밀번호 변경</button>
					</div>
				</form>
            </div>
        </div>
    </div>
</body>

<script>
	//	비밀번호 regex
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
	// 		비밀번호와 비밀번호 확인이 일치하는지 확인
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
	})

	// 비밀번호 변경 버튼
		$(".changePwBtn").click(function(){
			
	 		if($("#pw").val()=="" || $("#pw_re").val()==""){
				alert("빈칸을 입력해주세요");
				return;
			}
			
			
			if($("#pw").val() != $("#pw_re").val()){
				alert("비밀번호가 일치하지 않습니다");
				return;
			}
			
			$(".changePwBtn").attr("type","submit");
		});
		

</script>
</html>