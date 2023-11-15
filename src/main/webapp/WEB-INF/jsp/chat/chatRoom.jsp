<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅창</title>
<script src="https://code.jquery.com/jquery-3.7.1.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-modal/0.9.2/jquery.modal.min.css"/>

<link rel="stylesheet" href="/css/chat/chat_common.css" />
<link rel="stylesheet" href="/css/chat/chat_invite.css">
<link rel="stylesheet" href="/css/chat/chat_rename.css">
<link rel="stylesheet" href="/css/chat/chat_profile.css">
<link rel="stylesheet" href="/css/chat/chat_chatRoom.css">
<script src="/js/chat/chat_invite.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1.6.1/dist/sockjs.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/stomp-websocket@2.3.4-next/lib/stomp.min.js"></script>
</head>

<body>
    <div class="container">
        <div class="reName__modal">
            <div class="reName__header d-flex">
                <div class="reName__name">채팅방 이름 바꾸기</div><div class="reName__cancel"><i class="fa-solid fa-xmark"></i></div>
            </div>
            <div class="reName__body">
                <div class="reName__input" contenteditable="true">
                    채팅방 이름
                </div>
            </div>
            <div class="reName__footer align-center">
                <button class="reName__cancelBtn">취소</button><div class="reName_margin"></div><button class="reName__submitBtn">확인</button>
            </div>
           
        </div>

        <!-- 최하단으로 이동 -->
        <div class="container__chat-down align-center"><i class="fa-solid fa-arrow-down"></i></div>
        <script>
            $(".container__chat-down").on("click",function(){
                $(".chatContainer__chatArea").animate({scrollTop:$(".chatContainer__chatArea")[0].scrollHeight},1000);
            })
        </script>
        <!-- 최하단으로 이동 끝 -->

        <div class="chatHeader">
            <div class="header d-flex">
                <div class="header__chatNameCover d-flex"><div class="header__chatName">${personalChatRoomInfo.name}</div>
                <div class="header__numOfPPL fontEN"><i class="fa-solid fa-user"></i>&nbsp;&nbsp;${personalChatRoomInfo.emp_cnt}</div>
                </div>
                <div class="header__menuIcon d-flex">
                    <div class="menuIcon__searchBtn"><i class="fa-solid fa-magnifying-glass"></i></div>
                    <div class="menuIcon__hamBtn"><i class="fa-solid fa-bars"></i></div>
                </div>
                <div class="hamBtn__hamMenu">
                    <div class="hamMenu__invite hamMenu_menu">초대하기</div>
                    <div class="hamMenu__reName hamMenu_menu">이름 변경</div>
                    <div class="hamMenu__exit hamMenu_menu">나가기</div>
                </div>
            </div>
            <div class="header__chatSearch d-flex">
                <div class="chatSearch__inputCover d-flex">
                    <i class="fa-solid fa-magnifying-glass"></i><input type="text" class="chatSearch__input" placeholder="대화 내용 검색">
                </div>
                <div class="chatSearch__cancel"><i class="fa-solid fa-xmark"></i></div>
            </div>
            <div class="offlineCnt">최사장님 외 4명은 현재 부재중 입니다.</div>
        </div>

        <div class="chatContainer">
            <div class="chatContainer__chatArea">
            	<!-- 날짜는 아래와 같이 작성 
            		$(".chatContainer__chatArea").append($("<div>").attr("class","chatArea__DayLine align-center").text("날짜"));
            	-->
                
            </div>

            <div class="chatContainer__inputArea">
                <div class="inputArea__msg" contenteditable="true"></div>
                <div class="inputArea__btns d-flex">
                    <div class="inputArea__fileIcon"><i class="fa-solid fa-paperclip"></i></div>
                    <div class="inputArea__subminBtnCover"><button class="inputArea__subminBtn">전송</button></div>
                </div>
            </div>
        </div>
    </div>

   

    <!-- 초대하기 모달 -->
    <div class="invite__modal">
        <div class="invite__header d-flex">
            <div class="invite__name">대화 상대 추가</div>
            <div class="invite__cancel"><i class="fa-solid fa-xmark"></i></div>
        </div>
        <div class="invite-search d-flex">
            <div class="invite-search__icon align-center"><i class="fa-solid fa-magnifying-glass"></i></div>
            <input class="invite-search__txt" type="text" placeholder="이름으로 검색해주세요.">
        </div>
        <div class="invite__body">
            <div class="invite__selectList">
                <div class="invite__selectEmp">황민경</div>
                <div class="invite__selectEmp">장유나</div>
                <div class="invite__selectEmp">김서영</div>
                <div class="invite__selectEmp">이윤진</div>
                <div class="invite__selectEmp">김창균</div>
                <div class="invite__selectEmp">오부장</div>
                <div class="invite__selectEmp">최사장</div>
                <div class="invite__selectEmp">김이사</div>
                <div class="invite__selectEmp">오부장</div>
                <div class="invite__selectEmp">최사장</div>
                <div class="invite__selectEmp">김이사</div>
                <div class="invite__selectEmp">오부장</div>
            </div>
            <div class="invite__allList">
                <div class="allList__companyName">c-lover</div>
                <div class="allList__list">
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                    <div class="allList__employee d-flex">
                        <div class="allList__empProfileImgCover"><div class="allList__empProfileImg"></div></div>
                        <div class="allList__empName">이름임</div>
                        <div class="allList__empcheckBox"><input type="checkBox"></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="invite__footer align-center">
            <button class="invite__cancelBtn">취소</button>
            <div class="invite_margin"></div><button class="invite__submitBtn">확인</button>
        </div>
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
                                <div class="fontKR__Small profile__text colorGreen">업무중</div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">클로버산업 > 관리부 > 인사팀</div>
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
                                    <div class="profile__subject">
                                        이메일
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">이메일 주소</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">
                                    <div class="profile__icon">
                                        <i class="fa-solid fa-phone fa-flip-horizontal"></i>
                                    </div>
                                    <div class="profile__subject">
                                        사내전화
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">101</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">
                                    <div class="profile__icon">
                                        <i class="fa-solid fa-mobile-button"></i>
                                    </div>
                                    <div class="profile__subject">
                                        휴대전화
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">휴대전화 번호</div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div class="fontKR__Small profile__text">
                                    <div class="profile__icon">
                                        <i class="fa-solid fa-calendar-days"></i>
                                    </div>
                                    <div class="profile__subject">
                                        입사일
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div class="fontKR__Small profile__text">2023-11-01</div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script>
    	let roomId = "${chatRoomId}";
    	let loginId = "${loginID}";
    	
    	let sock = new SockJS("/ws/chat");
    	let ws = Stomp.over(sock);
    	
    	function connect() {
    	    ws.connect({}, function(frame) {
    	        ws.subscribe("/sub/chat/room/" + roomId, function(message) {  // 여기에 닫는 괄호 추가
    	            let recv = JSON.parse(message.body);
    	            recvMessage(recv);
    	        });
    	        console.log("/sub/chat/room/"+roomId);

    	        // 이벤트 리스너를 통해 다시한번 확인해볼것. 방법이 있을거다.
//     	        $.ajax({
//     	            url: '/chat/isExistThisRoom',
//     	            method: 'GET',  // 여기에 쉼표 추가
//     	            data: { chat_room_id: roomId },  // 객체 형태로 수정
//     	            success: function(resp) {
//     	                if (!resp) {
//     	                    ws.send("/pub/chat/message", {}, JSON.stringify({
//     	                        state: "JOIN",
//     	                        chat_room_id: roomId,
//     	                        emp_id: loginId
//     	                    }));
//     	                } else {
//     	                    ws.send("/pub/chat/message", {}, JSON.stringify({
//     	                        state: "REJOIN",
//     	                        chat_room_id: roomId,
//     	                        emp_id: loginId
//     	                    }));
//     	                }
//     	            },
//     	            error: function(error) {
//     	                // 에러처리 코드
//     	            }
//     	        });
    	    });
    	}

    	
    	function sendMessage() {
    	    var message = $(".inputArea__msg").text();
    	    if (message.trim() === '') {
    	        alert('메시지를 입력하세요');
    	        return;
    	    }
    	    ws.send('/pub/chat/message', {}, JSON.stringify({  // 여기에 중괄호 추가
    	        state: 'CHAT',
    	        chat_room_id: roomId,
    	        emp_id: loginId,
    	        content: message
    	    }));  // 여기에 있는 괄호 하나 제거
    	    $(".inputArea__msg").text('');
    	}

    	// 여기 스타일 한번 더 수정해야됨...
    	function recvMessage(recv) {
    		console.log(recv);
    	    var messageItem;
    	    if (recv.emp_id === loginId) { // 본인이 보낸 메시지
    	        messageItem = '<div class="chatArea__myChat d-flex">' +
    	                      '<div class="myChat__chatContents d-flex">' +
    	                      '<div class="myChat__chatMsg d-flex">' + recv.content + '</div>' +
    	                      '</div></div>';
    	    } else { // 타인이 보낸 메시지
    	        messageItem = '<div class="chatArea__otherPersonChat d-flex">' +
    	                      '<div class="otherPersonChat__profileImg"></div>' +
    	                      '<div class="otherPersonChat__chatInfo">' +
    	                      '<div class="otherPersonChat__userName">' + recv.emp_name + '</div>' +
    	                      '<div class="otherPersonChat__chatContents d-flex">' +
    	                      '<div class="otherPersonChat__chatMsg d-flex">' + recv.content + '</div>' +
    	                      '</div></div></div>';
    	    }
    	    $('.chatContainer__chatArea').append(messageItem);
    	    $('.chatContainer__chatArea').scrollTop($('.chatContainer__chatArea')[0].scrollHeight);
    	}


    	
    	
    	function handleKeyPress(event){
    		if (event.keyCode === 13 && !event.shiftKey) {
				event.preventDefault();
				sendMessage();
			}
    	}
    	
    	
        window.onresize = function () {
            let h = window.innerHeight - 165;
            $(".chatContainer__chatArea").css("height", h - 0.1);

            let w = window.innerWidth - 30;
            $(".chatSearch__input").css("width",w);
        };

        $(".chatSearch__cancel").on("click",function(){
            $(".header__chatSearch").css("display","none");
            $(".chatSearch__input").val("");
        })

        $(".menuIcon__searchBtn").on("click",function(){
            if($(".hamBtn__hamMenu").css("display")=="block") $(".hamBtn__hamMenu").css("display","none"); 
            if($(".header__chatSearch").css("display")=="flex") $(".header__chatSearch").css("display","none");
            else $(".header__chatSearch").css("display","flex");
        });

        $(".menuIcon__hamBtn").on("click",function(){
            if($(".hamBtn__hamMenu").css("display")=="none") $(".hamBtn__hamMenu").css("display","block");
            else $(".hamBtn__hamMenu").css("display","none");
            if($(".header__chatSearch").css("display")=="flex") $(".header__chatSearch").css("display","none");
        });

        $(".hamMenu__reName").on("click",function(){
            $(".reName__modal").modal({
                fadeDuration:300,
                showClose:false
            });
            $(".reName__cancel").on("click",$.modal.close);
            $(".reName__cancelBtn").on("click",$.modal.close);
        });

        $(".hamMenu__invite").on("click",function(){
            $(".invite__modal").modal({
                fadeDuration:300,
                showClose:false
            });
            $(".invite__cancel").on("click",$.modal.close);
            $(".invite__cancelBtn").on("click",$.modal.close);
        })


        // 컨텍스트 메뉴 존재할 경우, 다른 곳 클릭 시 컨텍스트 메뉴 삭제, 메뉴 누르면 해당 버튼 이벤트 실행
        $(document).on("mousedown", function(e) {   
            if($(".hamBtn__hamMenu").css("display")=="block") {
                if(!$(e.target).hasClass("hamMenu_menu")) $(".hamBtn__hamMenu").css("display","none");  
            }
        });

        // 프로필 누르면 프로필 모달창 띄우기
        $(document).on("click",".otherPersonChat__profileImg",function(e){
            $(".profile__modal").modal({
                fadeDuration:300,
                showClose:false
            });

            $(".close__btn").on("click",$.modal.close);
        });
        
        function scrollToBottom() {
            var chatArea = $(".chatContainer__chatArea")[0];

            // chatArea가 존재하는 경우에만 실행
            if (chatArea) {
                chatArea.scrollTop = chatArea.scrollHeight;
            }
        }

        
        $(document).ready(function() {
        	$.ajax({
        		url: "/chat/chatMsgLoad",
        		data:{
        			emp_id : loginId,
        			chat_room_id : roomId
        		}
        	}).done(function(resp) {
        		console.log(resp);
        		//
        		
        		let lastDate = null;
        		
        		
        		for (let i = 0; i < resp.chat.length; i++) {
        			let formattedTime = formatChatTime(resp.chat[i].write_date);
        			let currentDate = new Date(resp.chat[i].write_date).toDateString();
        			
        			// 날짜가 변경되었거나 첫 메시지인 경우
                    if (lastDate !== currentDate) {
                        let formattedDate = formatDate(resp.chat[i].write_date);
                        $(".chatContainer__chatArea").append(
                            $("<div>").addClass("chatArea__DayLine align-center").text(formattedDate)
                        );
                        lastDate = currentDate;
                    }
        			if (resp.chat[i].emp_id == resp.group.emp_id) {
        				
        				
        				let chatArea__myChat = $("<div>").attr("class","chatArea__myChat d-flex");
        				let myChat__chatContents = $("<div>").attr("class","myChat__chatContents d-flex");
        				let myChat__chatMsgLeft = $("<div>").attr("class","myChat__chatMsgLeft d-flex");
        				let chatMsgLeft__align = $("<div>").attr("class","chatMsgLeft__align");
        				let myChat__readCnt = $("<div>").attr("class","myChat__readCnt").text("2"); //채팅 안 읽은 사람 수
        				let myChat__sendTime = $("<div>").attr("class","myChat__sendTime").text(formattedTime); //채팅 보낸 시각
        				
        				
        				let myChat__chatMsg = $("<div>").attr("class","myChat__chatMsg d-flex")				
        				
        				// if문 추후에 파일여부에 대한 속성 추가하든 해서 수정 필요
        				if(resp.chat[i].state=="FILE") {// 파일이면
        					let myChat__fileMsg = $("<div>").attr("class","myChat__fileMsg");
        					let fileMsg__fileName = $("<div>").attr("class","fileMsg__fileName").text("파일명.확장자명"); //파일명.확장자명
        					let fileMsg__volume = $("<div>").attr("class","fileMsg__volume").text("파일 용량: 33.5kb"); //파일 용량
        					let fileMsg__expirationDate = $("<div>").attr("class","fileMsg__expirationDate").text("유효기간: ~ 2023.11.13"); //파일 유효기간
        					let chatMsg__download = $("<div>").attr("class","chatMsg__download align-center").html("<i class='fa-solid fa-download'></i>"); //파일 다운도르 아이콘	
        					myChat__chatMsg.append(myChat__fileMsg.append(fileMsg__fileName).append(fileMsg__volume).append(fileMsg__expirationDate)).append(chatMsg__download);
        				}else{ // 파일아니면
        					myChat__chatMsg.text(resp.chat[i].content); // 채팅 내용	
        				}
        				
        						
        				
        				// 채팅 좌측 읽지 않은 사람 수 및 보낸 시각
        				myChat__chatMsgLeft.append(chatMsgLeft__align.append(myChat__readCnt).append(myChat__sendTime));
        				
        				// 최종 본인 메시지 형태
        				chatArea__myChat.append(myChat__chatContents.append(myChat__chatMsgLeft).append(myChat__chatMsg));
        				
        				$(".chatContainer__chatArea").append(chatArea__myChat);
        			} else {
        				let chatArea__otherPersonChat = $("<div>").attr("class", "chatArea__otherPersonChat d-flex");
        				let otherPersonChat__profileImg = $("<div>").attr("class", "otherPersonChat__profileImg");
        				let otherPersonChat__chatInfo = $("<div>").attr("class", "otherPersonChat__chatInfo");
        				let otherPersonChat__userName = $("<div>").attr("class", "otherPersonChat__userName").text(resp.chat[i].emp_name); //사원이름, employee table과 join해 가져와야함.
        				let otherPersonChat__chatContents = $("<div>").attr("class", "otherPersonChat__chatContents d-flex");
        				
        				
        				
        				let otherPersonChat__chatMsg = $("<div>").attr("class", "otherPersonChat__chatMsg d-flex");

        				// if문 추후에 파일여부에 대한 속성 추가하든 해서 수정 필요
        				if(resp.chat[i].content=="file") {// 파일이면
        					let myChat__fileMsg = $("<div>").attr("class","otherPersonChat__fileMsg");
        					let fileMsg__fileName = $("<div>").attr("class","fileMsg__fileName").text("파일명.확장자명"); //파일명.확장자명
        					let fileMsg__volume = $("<div>").attr("class","fileMsg__volume").text("파일 용량: 33.5kb"); //파일 용량
        					let fileMsg__expirationDate = $("<div>").attr("class","fileMsg__expirationDate").text("유효기간: ~ 2023.11.13"); //파일 유효기간
        					let chatMsg__download = $("<div>").attr("class","chatMsg__download align-center").html("<i class='fa-solid fa-download'></i>"); //파일 다운도르 아이콘	
        					otherPersonChat__chatMsg.append(myChat__fileMsg.append(fileMsg__fileName).append(fileMsg__volume).append(fileMsg__expirationDate)).append(chatMsg__download);
        				}else{ // 파일아니면
        					otherPersonChat__chatMsg.text(resp.chat[i].content); // 채팅 내용	
        				}
        				
        				let otherPersonChat__chatMsgRight = $("<div>").attr("class", "otherPersonChat__chatMsgRight d-flex");
        				let chatMsgRight__align = $("<div>").attr("class", "chatMsgRight__align");
        				let otherPersonChat__readCnt = $("<div>").attr("class", "otherPersonChat__readCnt").text("1"); //채팅 안 읽은 사람 수
        				let otherPersonChat__sendTime = $("<div>").attr("class", "otherPersonChat__sendTime").text(formattedTime); // 채팅 보낸 시각

        				// 채팅 우측 읽지 않은 사람 수 및 보낸 시각
        				otherPersonChat__chatMsgRight.append(chatMsgRight__align.append(otherPersonChat__readCnt).append(otherPersonChat__sendTime));

        				// 채팅 메시지 및 읽지 않은 사람 수, 보낸 시각
        				otherPersonChat__chatContents.append(otherPersonChat__chatMsg).append(otherPersonChat__chatMsgRight); arguments

        				// 보낸 사람 정보 + 메시지 정보
        				otherPersonChat__chatInfo.append(otherPersonChat__userName).append(otherPersonChat__chatContents);

        				// 최종 상대방 메시지 형태
        				chatArea__otherPersonChat.append(otherPersonChat__profileImg).append(otherPersonChat__chatInfo);

        				$(".chatContainer__chatArea").append(chatArea__otherPersonChat);
        			}
        		}
        		scrollToBottom();
        	});
        	
        	function formatChatTime(timestamp) {
        	    let date = new Date(timestamp);

        	    return date.toLocaleTimeString('ko-KR', {
        	        hour: '2-digit',
        	        minute: '2-digit',
        	        hour12: true
        	    });
        	}
        	
        	function formatDate(timestamp) {
        	    let date = new Date(timestamp);
        	    let options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
        	    return date.toLocaleDateString('ko-KR', options);
        	}

        	
        	$(".inputArea__msg").on("keypress", handleKeyPress);
        	
        	
        	
        	connect();
        });

</script>
</body>

</html>