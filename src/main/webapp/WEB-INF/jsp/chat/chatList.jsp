<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅방 목록</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />
<link rel="stylesheet" href="/css/chat/chat_common.css" />
<link rel="stylesheet" href="/css/chat/chat_chatList.css" />
<link rel="stylesheet" href="/css/chat/chat_invite.css">
<link rel="stylesheet" href="/css/chat/chat_rename.css">
<script src="/js/chat/chat_invite.js"></script>
<script src="/js/chat/chatListLoad.js"></script>
</head>



<body>
	<div class="header">
		<div class="header__text fontEN colorGreen">C-lovers</div>
	</div>

	<div class="myInfo d-flex">
		<div class="myInfo__profileImg d-flex">
			<div class="profileImg__cover">
				<img class="profileImg__img" src="/css/chat/profileImg.png">
				<div class="profileImg__state backLightGreen"></div>
			</div>
		</div>

		<div class="myInfo__infoTxt d-flex">
			<div class="infoTxt__align">
				<div class="infoTxt__name">대표이사</div>
				<div class="infoTxt__state colorGreen">업무중</div>
			</div>
		</div>
	</div>

	<!-- 초대하기 모달 -->
	<div class="invite__modal">
		<div class="invite__header d-flex">
			<div class="invite__name">그룹채팅 시작</div>
			<div class="invite__cancel">
				<i class="fa-solid fa-xmark"></i>
			</div>
		</div>
		<div class="invite-search d-flex">
			<div class="invite-search__icon align-center">
				<i class="fa-solid fa-magnifying-glass"></i>
			</div>
			<input class="invite-search__txt" type="text"
				placeholder="이름으로 검색해주세요.">
		</div>
		<div class="invite__body">
			<div class="invite__selectList"></div>
			<div class="invite__allList">
				<div class="allList__companyName">c-lover</div>
				<div class="allList__list">
					<div class="allList__employee d-flex">
						<div class="allList__empProfileImgCover">
							<div class="allList__empProfileImg"></div>
						</div>
						<div class="allList__empName">이름임</div>
						<div class="allList__empcheckBox">
							<input type="checkBox">
						</div>
					</div>

				</div>
			</div>
		</div>
		<div class="invite__footer align-center">
			<button class="invite__cancelBtn">취소</button>
			<div class="invite_margin"></div>
			<button class="invite__submitBtn">확인</button>
		</div>
	</div>

	<div class="chat-nav backLightGreen d-flex">
		<a href="/chat/goMain"><div
				class="chat-nav__userList align-center">
				<i class="fa-solid fa-user"></i>
			</div></a> <a href="/chat/chatList"><div
				class="chat-nav__chatList align-center">
				<i class="fa-solid fa-comment nav-selected"></i>
			</div></a> <a href="/chat/fileList"><div
				class="chat-nav__fileList align-center">
				<i class="fa-solid fa-folder-open"></i>
			</div></a>
	</div>


	<div class="chat-search d-flex">
		<div class="chat-search__icon align-center">
			<i class="fa-solid fa-magnifying-glass"></i>
		</div>
		<input class="chat-search__txt" type="text" placeholder="참여자, 대화내용 검색">
		<div class="chat-search__cancel fontEN align-center">
			<i class="fa-solid fa-xmark"></i>
		</div>
	</div>

	<div class="chat-chatList"></div>


	<div class="chat__add__button">
		<i class="fa-solid fa-plus chat__plus"></i>
	</div>
	<script>
		// 채팅목록 누르면 채팅방으로 이동 -> 임의로 작성한 코드임. 추후에 코드 수정 필요
		$(document).on("click",".chat-chatList__chatRoom",function(){
			let option ="height=585, width=400";
	        let openUrl = "/chat/goChatRoom";
	        window.open(openUrl,"chatRoom",option);
		})

        // search input focus 시 우측에 검색 취소 버튼 생성
        $(".chat-search__txt").on("focus", function () {
            $(".chat-search__cancel").css("display", "flex");
        });

        // search input focus 되어 생성된 취소 버튼 클릭 시, 검색어 삭제 및 input tag blur
        $(".chat-search__cancel").on("click", function () {
            $(".chat-search__txt").blur();
            $(".chat-search__txt").val("");
            $(".chat-search__cancel").css("display", "none");
        });

        // 컨텍스트 메뉴 관련 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // 기존 컨텍스트 메뉴 막기
        $(document).on('contextmenu', function () {
            return false;
        });

        // 컨텍스트 메뉴 생성 함수
        function mkContextMenu(id) {
            let menuBox = $("<div>").attr("class", "ctxMenuBox");
            let menuList = $("<ul>").attr("class", "ctxMenuBox__ctxMenuList");
            let goChat = $("<li>").text("채팅하기").attr("class", "ctxMenuList__goChat").attr("id", id);
            menuList.append(goChat);

            goChat.on("mousedown", function () {
                window.open("goChat.html", '새창2', 'width=400,height=585');
            });

            let goProfile = $("<li>").text("프로필보기").attr("class", "ctxMenuList__goProfile");
            menuList.append(goProfile);
            goProfile.on("mousedown", function () {
                $(".profile__modal").modal({
                    fadeDuration: 300,
                    showClose: false
                });

                $(".close__btn").on("click", $.modal.close);
            });

            menuList.append($("<li>").text("취소하기").attr("class", "ctxMenuList__chatCancel"));
            menuBox.append(menuList);
            return menuBox;
        }

        // 프로필 우클릭 시 컨텍스트 메뉴 생성 함수 호출
        $(document).on("mouseup", ".chat-userList__chatUserInfo", function (e) {
            if ((e.button == 2) || e.which == 3) {
                $(".ctxMenuBox").remove();
                let menuBox = mkContextMenu($(this).attr("id"));
                document.body.appendChild(menuBox[0]);
                menuBox.css({ "top": e.pageY, "left": e.pageX });
            }
        });

        // 컨텍스트 메뉴 존재할 경우, 다른 곳 클릭 시 컨텍스트 메뉴 삭제, 혹은 채팅이나 프로필 버튼 눌러 해당 버튼 이벤트 실행 후 삭제
        $(document).on("mousedown", function (e) {
            if ($(".ctxMenuBox").length) {
                $(".ctxMenuBox").remove();
            }
        });
        
        $(".chat__add__button").on("click",function(){
            $(".invite__modal").modal({
                fadeDuration:300,
                showClose:false
            });
            $(".invite__cancel").on("click",$.modal.close);
            $(".invite__cancelBtn").on("click",$.modal.close);
        })

        // // 컨텍스트 메뉴 취소하기 클릭 시 메뉴 삭제 (넣을가요 말까요.. 바로 윗줄 코드를 추가해서 없애두 될 것 같기두 해)
        // $(document).on("click", ".ctxMenuList__chatCancel", function(e) {
        // 	$("#ctxMenuBox").remove();
        // });

        // 여기까지 컨텍스트 메뉴 관련 ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // 윈도우 높이에 따라 인원 목록 높이 조절 (화면에 꽉 차게 나오면서 body엔 스크롤 안 생기고, 인원 목록의 스크롤 하단이 전체 크기에 딱 맞도록 하기 위함.)
        window.onresize = function () {
            let h = window.innerHeight - 251;
            $(".chat-chatList").css("height", h);

            let w = window.innerWidth;

            $(".chatRoom__txt").css("width",w-140);
            $(".chatRoom_txtalignUp").css("width",w-200);
            $(".chatRoom__lastChat").css("width",w-200);
            $(".chat-search__txt").css("width",w-72);
        }
        
        function updateMyInfo(myInfo) {
            var myInfoHtml = '';

            // myInfo 데이터를 사용하여 HTML 생성
            myInfoHtml += '<div class="infoTxt__name">' + 
                            myInfo.dept_name + '>' + myInfo.task_name + '>' + myInfo.job_name +
                            '&nbsp;&nbsp;' + myInfo.employee_name + '</div>' +
                          '<div class="infoTxt__state colorGreen">업무중</div>';

            
            $('.infoTxt__align').html(myInfoHtml);
        }
        
        
        
        $(document).ready(function() {
        	$.ajax({
        	    url: '/chat/getMyInfo',
        	    type: 'GET',
        	    dataType: 'json',
        	    success: function(data) {
        	        updateMyInfo(data);
        	    },
        	    error: function(error) {
        	        console.log(error);
        	    }
        	});
        	$.ajax({
        	    url: '/chat/getMainData',
        	    type: 'GET',
        	    dataType: 'json',
        	    success: function(data) {
        	        var allListHtml = '';
        	        data.officerList.forEach(function(officer) {
        	        	if(officer.employee_id===data.myInfo.employee_id){
        	        		return;
        	        	}
        	            allListHtml += '<div class="allList__employee d-flex" data-employee-name=' + officer.employee_name + '>' +
        	                               '<div class="allList__empProfileImgCover">' +
        	                                   '<div class="allList__empProfileImg" style="background-image: url(' + getProfileImageUrl(officer.profile_img) + ');"></div>' +
        	                               '</div>' +
        	                               '<div class="allList__deptName">' + officer.dept_name + " > " + officer.task_name +"<br>" +"  >  "+officer.job_name+ '</div>' +
        	                               '<div class="allList__empName">' + officer.employee_name + '</div>' +
        	                               '<div class="allList__empcheckBox"><input type="checkBox" value="' + officer.employee_id + '"></div>' +
        	                           '</div>';
        	        });
        	        $('.invite__allList .allList__list').html(allListHtml);
        	    },
        	    error: function(error) {
        	        console.log(error);
        	    }
        	});
        	
        	// 선택된 직원들을 저장할 객체
        	var selectedEmployees = {};

        	// 부모 요소에 이벤트 위임을 사용하여 체크박스 변경 감지
        	$(document).on('change', 'input[type="checkBox"]', function() {
        	    var empId = $(this).val();
        	    var empName = $(this).closest('.allList__employee').find('.allList__empName').text();
				let i = 0;
        	    if (this.checked) {
        	        // 체크된 경우, 선택된 직원 목록에 추가
        	        selectedEmployees[empId] = empName;
        	        console.log(selectedEmployees);
        	    } else {
        	        // 체크 해제된 경우, 선택된 직원 목록에서 제거
        	        delete selectedEmployees[empId];
        	    }

        	    updateSelectedEmployeesList();

        	});


        	// 선택된 직원 목록 업데이트
        	function updateSelectedEmployeesList() {
        	    var listHtml = '';
        	    $.each(selectedEmployees, function(empId, empName) {
        	        listHtml += '<div class="invite__selectEmp" data-emp-id="' + empId + '">' + empName + '</div>';
        	    });
        	    $('.invite__selectList').html(listHtml);
        	}
			
        	function getProfileImageUrl(imageUrl) {
                return imageUrl ? imageUrl : '/css/chat/profileImg.png'; // 기본 이미지 경로 지정
            }
        	
        	$('.invite-search__txt').on('input', function() {
                var searchText = $(this).val().toLowerCase();

                // 모든 직원 목록 항목에 대해 반복
                $('.allList__employee').each(function() {
                    var employeeName = $(this).data('employee-name').toLowerCase();

                    // 검색어가 직원 이름에 포함되어 있으면 표시, 그렇지 않으면 숨김
                    if (employeeName.includes(searchText)) {
                        $(this).show();
                    } else {
                        $(this).hide();
                    }
                });
            });
        	
        	
        	// '확인' 버튼 클릭 이벤트 리스너
            $('.invite__submitBtn').click(function() {
                // 서버로 보낼 데이터 준비
                var dataToSend = {
                    // 데이터 예시: 선택된 직원들의 ID를 서버로 전송
                    selectedEmployees: Object.keys(selectedEmployees)
                };   

                // AJAX 요청
                $.ajax({
                	url: '/chat/setGroupChattingRoom', // 서버의 URL
                    type: 'POST', // 요청 방식
				    contentType: 'application/json', // JSON 형식으로 데이터 전송
				    data: JSON.stringify({ selectedEmployees: Object.keys(selectedEmployees) }),
				    success: function(data) {
                    	window.open("/chat/goChatRoom/"+data, '새창2', 'width=400,height=585');
                    },
				    error: function(xhr, status, error) {
				        //console.error('Error:', error);
				    }
				});
            });
        		
        });
    </script>
</body>

</html>