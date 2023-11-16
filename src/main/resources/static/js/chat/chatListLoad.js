$(document).ready(function() {
	$.ajax({
		url: "/chat/chatListLoad"
	}).done(function(resp) {

		for (let i = 0; i < resp.length; i++) {
			//만약 인원이 2명이면 (개인채팅)
			let chatList__chatRoom = $("<div>").attr("class", "chat-chatList__chatRoom d-flex");




			let chatRoom__txt = $("<div>").attr("class", "chatRoom__txt d-flex");
			let chatRoom__txtalign = $("<div>").attr("class", "chatRoom__txtalign");

			let chatRoom__chatName = $("<div>").attr("class", "chatRoom__chatName d-flex").html(resp[i].name); // 채팅방 이름

			let chatRoom__lastChat = $("<div>").attr("class", "chatRoom__lastChat").html(resp[i].content); // 채팅방 마지막 채팅 내역
			let chatRoom__lastChatDate = $("<div>").attr("class", "chatRoom__lastChatDate").html(formatTimestamp(resp[i].write_date)) // 마지막으로 보낸 메시지 시각 -> 나중에 이걸 기준으로 data 받아와서 최신내역이 상단에 표시되도록

			let chatRoom__img;
			if (resp[i].emp_cnt <= 2) {
				chatRoom__img = $("<img>").attr("class", "chatRoom__img").attr("src", "/css/chat/chatRoomImg.png"); // 개인 채팅일 때 프로필 사진
				chatRoom__txt.append(chatRoom__txtalign.append(chatRoom__chatName).append(chatRoom__lastChat));
			} else {
				chatRoom__img = $("<div>").attr("class", "chatRoom__img").html("<i class='fa-solid fa-users'></i>"); // 그룹 채팅일 땐 프로필 사진 대신 아이콘
				let chatRoom__txtalign__in = $("<div>").attr("class", "chatRoom__txtalign d-flex"); // 정렬용
				let chatRoom__numOfPPL = $("<div>").attr("class", "chatRoom__numOfPPL fontEN").html("<i class='fa-solid fa-user'></i>&nbsp;&nbsp;" + resp[i].emp_cnt); // 인원수
				chatRoom__txtalign__in.append(chatRoom__chatName).append(chatRoom__numOfPPL);
				chatRoom__txt.append(chatRoom__txtalign.append(chatRoom__txtalign__in).append(chatRoom__lastChat));
			}


			chatList__chatRoom.append(chatRoom__img).append(chatRoom__txt).append(chatRoom__lastChatDate);
			chatList__chatRoom.attr("data-chat-room-id", resp[i].chat_room_id);
			$(".chat-chatList").append(chatList__chatRoom);

			// 클릭 이벤트 핸들러 수정
			$(document).on("click", ".chat-chatList__chatRoom", function() {
				let chatRoomId = $(this).data("chat-room-id"); // data 속성에서 chat_room_id 가져오기
				let option = "height=585, width=400";
				let openUrl = "/chat/goChatRoom/" + chatRoomId;
				window.open(openUrl, "chatRoom", option);
			});

		}




		/*		// 그룹 채팅
				for(let i=0; i<resp.length; i++){
					
					//만약 인원이 2명이면 (개인채팅)
					let chatList__chatRoom = $("<div>").attr("class","chat-chatList__chatRoom d-flex");
					
					let chatRoom__img = $("<div>").attr("class","chatRoom__img").html("<i class='fa-solid fa-users'></i>");
					
					let chatRoom__txt  = $("<div>").attr("class","chatRoom__txt d-flex");
					let chatRoom__txtalign = $("<div>").attr("class","chatRoom__txtalign");
					
					let chatRoom__txtalign__in = $("<div>").attr("class","chatRoom__txtalign d-flex");
					let chatRoom__chatName = $("<div>").attr("class","chatRoom__chatName d-flex").html(resp[i].name); // 채팅방 이름
					// 그룹 채팅이면 인원 수 출력
					let chatRoom__numOfPPL = $("<div>").attr("class","chatRoom__numOfPPL fontEN").html("<i class='fa-solid fa-user'></i>&nbsp;&nbsp;3")
					chatRoom__txtalign__in.append(chatRoom__chatName).append(chatRoom__numOfPPL)
					
					let chatRoom__lastChat = $("<div>").attr("class","chatRoom__lastChat").html("메시지 내용: 나중에 msg랑 조인해서 불러오기"); // 채팅방 마지막 채팅 내역
				
					// 그룹일 때
					chatRoom__txt.append(chatRoom__txtalign.append(chatRoom__txtalign__in).append(chatRoom__lastChat)); 
					
					let chatRoom__lastChatDate = $("<div>").attr("class","chatRoom__lastChatDate").html("시간"); // 마지막으로 보낸 메시지 시각 -> 나중에 이걸 기준으로 data 받아와서 최신내역이 상단에 표시되도록
					chatList__chatRoom.append(chatRoom__img).append(chatRoom__txt).append(chatRoom__lastChatDate);
					$(".chat-chatList").append(chatList__chatRoom);
					
				}*/






	});
});

function formatTimestamp(timestamp) {
    const messageDate = new Date(timestamp);
    const now = new Date();

    const isToday = now.toDateString() === messageDate.toDateString();
    const isYesterday = new Date(now.setDate(now.getDate() - 1)).toDateString() === messageDate.toDateString();
    const isThisYear = now.getFullYear() === messageDate.getFullYear();

    const timeString = messageDate.toLocaleTimeString('ko-KR', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
    }).toUpperCase();

    if (isToday) {
        // 오늘 보낸 메시지
        return timeString;
    } else if (isYesterday) {
        // 어제 보낸 메시지
        return `어제 ${timeString}`;
    } else if (isThisYear) {
        // 올해 이전에 보낸 메시지
        return `${messageDate.getMonth() + 1}.${messageDate.getDate()} ${timeString}`;
    } else {
        // 작년 이전에 보낸 메시지
        return `${messageDate.getFullYear().toString().slice(2)}.${messageDate.getMonth() + 1}.${messageDate.getDate()} ${timeString}`;
    }
}