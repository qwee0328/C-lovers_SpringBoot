<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 메인</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css" />

<link rel="stylesheet" href="/css/chat/chat_common.css" />
<link rel="stylesheet" href="/css/chat/chat_main.css" />
<link rel="stylesheet" href="/css/chat/chat_profile.css">
<script src="/js/chat/EmpListLoad.js"></script>
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
			
			</div>
		</div>
	</div>

	<div class="chat-nav backLightGreen d-flex">
		<a href="/chat/goMain"><div
				class="chat-nav__userList align-center">
				<i class="fa-solid fa-user nav-selected"></i>
			</div></a> <a href="/chat/chatList">
			<div
				class="chat-nav__chatList align-center">
				<i class="fa-solid fa-comment"></i>
			</div></a> <a href="/chat/fileList"><div
				class="chat-nav__fileList align-center">
				<i class="fa-solid fa-folder-open"></i>
			</div></a>
	</div>

	<div class="chat-search d-flex">
		<div class="chat-search__icon align-center">
			<i class="fa-solid fa-magnifying-glass"></i>
		</div>
		<input class="chat-search__txt" type="text" placeholder="이름으로 검색해주세요.">
		<div class="chat-search__cancel fontEN align-center">
			<i class="fa-solid fa-xmark"></i>
		</div>
	</div>
	
	<div class="chat-userList">
	</div>

	<!-- 프로필보기 모달창 -->
	<div class="profile__modal">
		<div class="profile__card">
			<div class="profile__head">
				<button class="close__btn">&times;</button>
				<p>사용자 프로필</p>
			</div>
			<hr class="profile__hr">
			<div class="profile__body">
				<table class="profile__table">
					<tbody>
						<tr>
							<td rowspan="3">
								<div class="profileModalImg__cover">
									<img class="profileImg__img" src="/css/chat/profileImg.png">
									<div class="profileImg__state backLightGreen"></div>
								</div>

							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<div class="fontKR__Small profile__text">최사장</div>
							</td>
							<td class="fontKR__Small profile__text">사장 / 영업</td>
						</tr>
						<tr>
							<td>
								<div class="fontKR__Small profile__text colorGreen"></div>
							</td>
							<td>
								<div class="fontKR__Small profile__text profile__office">클로버산업 > 관리부 > 인사팀</div>
							</td>
						</tr>
					</tbody>
				</table>

				<div class="profile__details">
					<table class="profile__details__table">
						<tr>
							<td>
								<div class="fontKR__Small profile__text">
									<div class="profile__icon">
										<i class="fa-solid fa-envelope"></i>
									</div>
									<div class="profile__subject">이메일</div>
								</div>
							</td>
							<td>
								<div class="fontKR__Small profile__text profile__email">이메일 주소</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="fontKR__Small profile__text">
									<div class="profile__icon">
										<i class="fa-solid fa-phone fa-flip-horizontal"></i>
									</div>
									<div class="profile__subject">사내전화</div>
								</div>
							</td>
							<td>
								<div class="fontKR__Small profile__text profile__company__phone">101</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="fontKR__Small profile__text">
									<div class="profile__icon">
										<i class="fa-solid fa-mobile-button"></i>
									</div>
									<div class="profile__subject" >휴대전화</div>
								</div>
							</td>
							<td>
								<div class="fontKR__Small profile__text profile__phone">휴대전화 번호</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="fontKR__Small profile__text">
									<div class="profile__icon">
										<i class="fa-solid fa-calendar-days"></i>
									</div>
									<div class="profile__subject">입사일</div>
								</div>
							</td>
							<td>
								<div class="fontKR__Small profile__text profile__hiredate">2023-11-01</div>
							</td>
						</tr>

					</table>


				</div>
				<div class="profile__button">
					<button class="chat__btn">채팅하기</button>
				</div>


			</div>

		</div>

	</div>
	

	<script>
        // search input focus 시 우측에 검색 취소 버튼 생성
        $(".chat-search__txt").on("focus",function(){
            $(".chat-search__cancel").css("display","flex");
        });

        // search input focus 되어 생성된 취소 버튼 클릭 시, 검색어 삭제 및 input tag blur
        $(".chat-search__cancel").on("click",function(){
            $(".chat-search__txt").blur();
            $(".chat-search__txt").val("");
            $(".chat-search__cancel").css("display","none");
        });


        // 드롭바 접기
        $(document).on("click",".userListChevronUp",function(){

            // 만약 팀명 드롭바를 접었다면 해당 팀 내 인원에 대한 목록 숨기기
            if( $(this).closest(".chevronParent").next().attr("class") && $(this).closest(".chevronParent").next().attr("class")=="chat-team__userInfoList"){
                $(this).closest(".chevronParent").next().css("display","none");

            // 만약 부서명 드롭바를 접었다면 해당 부서 내 팀에 대한 목록 숨기기 (부서 내 모든 팀 숨김 처리)
            } else if( $(this).closest(".chevronParent").next().attr("class") && $(this).closest(".chevronParent").next().attr("class").includes("chat-userList__chat-teams")){
                $(this).closest(".chevronParent").next().css("display","none");

                // 부서명 드롭바를 접음에 따라, 숨김 처리된 팀 내의 모든 인원에 대한 목록 숨기기
                if($(this).closest(".chevronParent").next().next().attr("class")=="chat-team__userInfoList"){
                    $(this).closest(".chevronParent").next().next().css("display","none"); 
                }
            }

            // 드롭바 모양 변경
            $(this).parent().html("<i class='fa-solid fa-chevron-down userListChevronDown'></i>");
           
        })

        // 드롭바 열기
        $(document).on("click",".userListChevronDown",function(){

            // 만약 팀명 드롭바를 열었다면 해당 팀 내 인원에 대한 목록 보이기
            if( $(this).closest(".chevronParent").next().attr("class") && $(this).closest(".chevronParent").next().attr("class")=="chat-team__userInfoList"){
                $(this).closest(".chevronParent").next().css("display","block");

            // 만약 부서명 드롭바 열었다면 해당 부서 내 팀 목록 보이기 (팀 내 드롭바 보임/숨김 처리는 부서명 숨김처리했을 때 내용 기억 (창 다시 켤 때마다 초기화))
            // ex. 만약 팀1 팀2가 존재하는 부서에 대하여, 팀1은 숨김처리 팀2는 보이게 해두고 부서를 숨김 처리 했다가 열 경우, 팀1 숨김처리, 팀2 그대로 보이도록.
            } else if($(this).closest(".chevronParent").next().attr("class") &&  $(this).closest(".chevronParent").next().attr("class").includes("chat-userList__chat-teams")){
                $(this).closest(".chevronParent").next().css("display","block");
            }

            // 드롭바 모양 변경
            $(this).parent().html("<i class='fa-solid fa-chevron-up userListChevronUp'></i>");
        })


        // 컨텍스트 메뉴 관련 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 
        // 기존 컨텍스트 메뉴 막기
        $(document).on('contextmenu', function() {
			return false; 
		});

     	// 컨텍스트 메뉴 생성 함수
        function mkContextMenu(employeeId) {
            let menuBox = $("<div>").attr("class", "ctxMenuBox");
            let menuList = $("<ul>").attr("class", "ctxMenuBox__ctxMenuList");
			let employee_id = employeeId
            let goChat = $("<li>").text("채팅하기").attr("class", "ctxMenuList__goChat").attr("id", employeeId);
            menuList.append(goChat);

            goChat.on("mousedown", function() {
            	// 그룹 채팅 관련 ajax로 채팅방을 만들고 그 채팅방에 채팅그룹을 만들어서 채팅방 id값을 resp로 받아와서 window.open으로 처리.
            	// 만일 채팅방이 이미 존재한다면, 그 채팅방으로 연결해줌.
            	$.ajax({
                    url: '/chat/setChattingRoom',
                    type: 'POST',
                    data: { employee_id : employeeId},
                    success: function(data) {
                    	window.open("/chat/goChatRoom/"+data, '새창2', 'width=400,height=620');
                    },
                    error: function(error) {
                        console.log("Error: ", error);
                    }
                });
            });

            let goProfile = $("<li>").text("프로필보기").attr("class", "ctxMenuList__goProfile");
            menuList.append(goProfile);

            goProfile.on("mousedown", function() {
                // AJAX 요청을 통해 사용자 정보 가져오기
                $.ajax({
                    url: '/chat/getOfficerInfoByEmployeeId',
                    type: 'GET',
                    data: { employee_id: employeeId },
                    success: function(data) {
                        // 모달 내용 업데이트
                        updateProfileModal(data);
                        // 모달 표시
                        $(".profile__modal").modal({
                            fadeDuration: 300,
                            showClose: false
                        });
                    },
                    error: function(error) {
                        console.log("Error: ", error);
                    }
                });

                $(".close__btn").on("click", $.modal.close);
            });

            menuBox.append(menuList);
            return menuBox;
        }
     	
        function updateProfileModal(data) {
            // 프로필 이미지 업데이트 (이미지가 없는 경우 기본 이미지 사용)
            var profileImgSrc = data.profile_img ? data.profile_img : '/css/chat/profileImg.png';
            $('.profile__modal .profileImg__img').attr('src', profileImgSrc);

            // 이름과 직급/부서 업데이트
            $('.profile__modal .profile__text').first().text(data.employee_name); // 이름
            $('.profile__modal .profile__text').eq(1).text(data.job_name + ' / ' + data.task_name); // 직급 및 부서
            $('.profile__office').text(data.office_name + " > " + data.dept_name + " > " + data.task_name);

            // 이메일, 전화번호, 입사일 업데이트
            var email = data.employee_email ? data.employee_email : '이메일 없음';
            var company_phone = data.employee_company_phone ? data.employee_company_phone : '사내 전화번호 없음';
            var phone = data.employee_phone ? data.employee_phone : '전화번호 없음'
            var hireDate = data.hire_date ? data.hire_date.split(' ')[0] : '날짜 없음'; // 날짜만 추출

            $('.profile__modal .profile__details__table .profile__email').text(email); // 이메일
            $('.profile__modal .profile__details__table .profile__company__phone').text(company_phone); // 전화번호
            $('.profile__modal .profile__details__table .profile__phone').text(phone); // 전화번호
            $('.profile__modal .profile__details__table .profile__hiredate').text(hireDate); // 입사일
            $(".chat__btn").attr("id", data.employee_id);
            $(".chat__btn").on("click",function(){
            	$.ajax({
                    url: '/chat/setChattingRoom',
                    type: 'POST',
                    data: { employee_id : data.employee_id},
                    success: function(data) {
                    	window.open("/chat/goChatRoom/"+data, '새창2', 'width=400,height=620');
                    },
                    error: function(error) {
                        console.log("Error: ", error);
                    }
                });
            });
        }

    
     	// 프로필 우클릭 시 컨텍스트 메뉴 생성 함수 호출
        $(document).on("mouseup", ".chat-userList__chatUserInfo", function(e) {
            if ((e.button == 2) || e.which == 3) {  
                $(".ctxMenuBox").remove();
                let employeeId = $(this).data("employee-id"); // 여기서 employee_id를 가져옵니다.
                let menuBox = mkContextMenu(employeeId); // 이 id를 함수에 전달합니다.
                document.body.appendChild(menuBox[0]);
                menuBox.css({"top": e.pageY, "left": e.pageX});  
            }              
        });

        // 컨텍스트 메뉴 존재할 경우, 다른 곳 클릭 시 컨텍스트 메뉴 삭제, 혹은 채팅이나 프로필 버튼 눌러 해당 버튼 이벤트 실행 후 삭제
        $(document).on("mousedown", function(e) {
            if($(".ctxMenuBox").length){ 
                $(".ctxMenuBox").remove();     
            }
        });

        // 여기까지 컨텍스트 메뉴 관련 ///////////////////////////////////////////////////////////////////////////////////////////////////////

        // 윈도우 높이에 따라 인원 목록 높이 조절 (화면에 꽉 차게 나오면서 body엔 스크롤 안 생기고, 인원 목록의 스크롤 하단이 전체 크기에 딱 맞도록 하기 위함.)
        window.onresize = function() {
            let h = window.innerHeight - 251;
            $(".chat-userList").css("height",h);

            let w = window.innerWidth - 71;
            $(".chat-search__txt").css("width",w);

        }
        
        function updateMyInfo(myInfo) {
            var myInfoHtml = '';

            // myInfo 데이터를 사용하여 HTML 생성
            myInfoHtml += '<div class="infoTxt__name">' + 
                            myInfo.dept_name + '>' + myInfo.task_name + '>' + myInfo.job_name +
                            '&nbsp;&nbsp;' + myInfo.employee_name + '</div>' +
                          '<div class="infoTxt__state colorGreen">&nbsp;</div>';

            
            $('.infoTxt__align').html(myInfoHtml);
        }
        
        function updateChatUserList(officerList, myInfo) {
            var userListHtml = '';

            // myInfo를 사용하여 HTML 추가
            userListHtml += '<div class="chat-userList__chat-company chevronParent d-flex">' +
                                '<div>' + myInfo.office_name + '</div>' +
                            '</div>';

            // officerList를 순회하며 HTML 생성
            officerList.forEach(function(officer, index) {
            	
            	// 내 정보는 리스트에 출력되지 않도록 함.
            	if (officer.employee_id === myInfo.employee_id) {
                    return; // 현재 반복을 건너뛴ek.
                }
            	// 부서가 시작될 때
            	if (index === 0 || officer.department_id !== officerList[index - 1].department_id) {
            	    userListHtml += '<div class="chat-userList__chat-dept chevronParent d-flex">' +
            	                        '<div>' + officer.dept_name + '</div>' +
            	                        '<div>' +
            	                            '<i class="fa-solid fa-chevron-up userListChevronUp"></i>' + // 드롭다운 아이콘 추가
            	                        '</div>' +
            	                    '</div>' +
            	                    '<div class="chat-userList__chat-teams">';
            	}

            	// 팀이 시작될 때
            	if (index === 0 || officer.task_id !== officerList[index - 1].task_id) {
            	    userListHtml += '<div class="chat-userList__chat-team chevronParent d-flex">' +
            	                        '<div>&nbsp;&nbsp;' + officer.task_name + '</div>' +
            	                        '<div>' +
            	                            '<i class="fa-solid fa-chevron-up userListChevronUp"></i>' + // 드롭다운 아이콘 추가
            	                        '</div>' +
            	                    '</div>' +
            	                    '<div class="chat-team__userInfoList">';
            	}

                // 사용자 정보 추가
                userListHtml += '<div class="chat-userList__chatUserInfo d-flex" data-employee-id="' + officer.employee_id + '">' +
                                    '<div class="chatUserInfo__profileImg profileImg__cover">' +
                                        '<img class="profileImg__img" src="' + (officer.profile_img ? officer.profile_img : '/css/chat/profileImg.png') + '">' +
                                        '<div class="profileImgSmall__state backLightGreen"></div>' +
                                    '</div>' +
                                    '<div class="chatUserInfo__txt d-flex align-center">' +
                                        '<div class="chatUserInfo__deptName">' + officer.job_name + '</div>' +
                                        '<div class="chatUserInfo__name">' + officer.employee_name + '</div>' +
                                    '</div>' +
                                '</div>';

                // 각 섹션의 끝 처리
                if (index === officerList.length - 1 || officer.task_id !== officerList[index + 1].task_id) {
                    userListHtml += '</div>'; // chat-team__userInfoList 닫기
                }

                if (index === officerList.length - 1 || officer.department_id !== officerList[index + 1].department_id) {
                    userListHtml += '</div>'; // chat-userList__chat-teams 닫기
                }
            });

            // HTML을 DOM에 삽입
            $('.chat-userList').html(userListHtml);
        }



        $(document).ready(function() {
        	$.ajax({
        	    url: '/chat/getMainData',
        	    type: 'GET',
        	    dataType: 'json',
        	    success: function(data) {
        	        updateChatUserList(data.officerList, data.myInfo);
        	        updateMyInfo(data.myInfo);
        	    },
        	    error: function(error) {
        	        console.log(error);
        	    }
        	});
        	
        	$('.chat-search__txt').on('input', function() {
                var searchText = $(this).val().toLowerCase();

                if (searchText) {
                    // 검색어가 있을 때, 모든 부서와 오피스 숨기기
                    $('.chat-userList__chat-dept, .chat-userList__chat-team').hide();

                    $('.chat-userList__chatUserInfo').each(function() {
                        var employeeName = $(this).find('.chatUserInfo__name').text().toLowerCase();
                        if (employeeName.includes(searchText)) {
                            $(this).show(); // 검색어가 포함된 요소 표시
                            // 관련된 부서와 오피스 표시
                            $(this).closest('.chat-userList__chat-teams').prev('.chat-userList__chat-dept').show();
                            $(this).closest('.chat-team__userInfoList').prev('.chat-userList__chat-team').show();
                        } else {
                            $(this).hide(); // 그렇지 않은 요소 숨김
                        }
                    });
                } else {
                    // 검색어가 없을 때, 모든 요소 표시
                    $('.chat-userList__chat-dept, .chat-userList__chat-team, .chat-userList__chatUserInfo').show();
                }
            });
        	$('.chat-search__cancel').on("click",function() {
        		$('.chat-search__txt').val('');
        		var searchText = "";
        		
        		if (searchText) {
                    // 검색어가 있을 때, 모든 부서와 오피스 숨기기
                    $('.chat-userList__chat-dept, .chat-userList__chat-team').hide();

                    $('.chat-userList__chatUserInfo').each(function() {
                        var employeeName = $(this).find('.chatUserInfo__name').text().toLowerCase();
                        if (employeeName.includes(searchText)) {
                            $(this).show(); // 검색어가 포함된 요소 표시
                            // 관련된 부서와 오피스 표시
                            $(this).closest('.chat-userList__chat-teams').prev('.chat-userList__chat-dept').show();
                            $(this).closest('.chat-team__userInfoList').prev('.chat-userList__chat-team').show();
                        } else {
                            $(this).hide(); // 그렇지 않은 요소 숨김
                        }
                    });
                } else {
                    // 검색어가 없을 때, 모든 요소 표시
                    $('.chat-userList__chat-dept, .chat-userList__chat-team, .chat-userList__chatUserInfo').show();
                }
        	})
        	
        });
        

    </script>
</body>

</html>