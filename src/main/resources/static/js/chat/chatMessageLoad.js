$(document).ready(function() {
	$.ajax({
		url: "/chat/chatMsgLoad"
	}).done(function(resp) {
		for (let i = 0; i < resp.chat.length; i++) {
			if (resp.chat[i].emp_id == resp.loginID) {
				let chatArea__myChat = $("<div>").attr("class", "chatArea__myChat d-flex");
				let myChat__chatContents = $("<div>").attr("class", "myChat__chatContents d-flex");
				let myChat__chatMsgLeft = $("<div>").attr("class", "myChat__chatMsgLeft d-flex");
				let chatMsgLeft__align = $("<div>").attr("class", "chatMsgLeft__align");
				let myChat__readCnt = $("<div>").attr("class", "myChat__readCnt").text("2"); //채팅 안 읽은 사람 수
				let myChat__sendTime = $("<div>").attr("class", "myChat__sendTime").text("오후 8:48"); //채팅 보낸 시각


				let myChat__chatMsg = $("<div>").attr("class", "myChat__chatMsg d-flex")

				// if문 추후에 파일여부에 대한 속성 추가하든 해서 수정 필요
				if (resp.chat[i].state == "file") {// 파일이면
					let myChat__fileMsg = $("<div>").attr("class", "myChat__fileMsg");
					let fileMsg__fileName = $("<div>").attr("class", "fileMsg__fileName").text("파일명.확장자명"); //파일명.확장자명
					let fileMsg__volume = $("<div>").attr("class", "fileMsg__volume").text("파일 용량: 33.5kb"); //파일 용량
					let fileMsg__expirationDate = $("<div>").attr("class", "fileMsg__expirationDate").text("유효기간: ~ 2023.11.13"); //파일 유효기간
					let chatMsg__download = $("<div>").attr("class", "chatMsg__download align-center").html("<i class='fa-solid fa-download'></i>"); //파일 다운도르 아이콘	
					myChat__chatMsg.append(myChat__fileMsg.append(fileMsg__fileName).append(fileMsg__volume).append(fileMsg__expirationDate)).append(chatMsg__download);
				} else { // 파일아니면
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
				let otherPersonChat__userName = $("<div>").attr("class", "otherPersonChat__userName").text(resp.chat[i].emp_id); //사원이름, employee table과 join해 가져와야함.
				let otherPersonChat__chatContents = $("<div>").attr("class", "otherPersonChat__chatContents d-flex");



				let otherPersonChat__chatMsg = $("<div>").attr("class", "otherPersonChat__chatMsg d-flex");

				// if문 추후에 파일여부에 대한 속성 추가하든 해서 수정 필요
				if (resp.chat[i].content == "file") {// 파일이면
					let myChat__fileMsg = $("<div>").attr("class", "otherPersonChat__fileMsg");
					let fileMsg__fileName = $("<div>").attr("class", "fileMsg__fileName").text("파일명.확장자명"); //파일명.확장자명
					let fileMsg__volume = $("<div>").attr("class", "fileMsg__volume").text("파일 용량: 33.5kb"); //파일 용량
					let fileMsg__expirationDate = $("<div>").attr("class", "fileMsg__expirationDate").text("유효기간: ~ 2023.11.13"); //파일 유효기간
					let chatMsg__download = $("<div>").attr("class", "chatMsg__download align-center").html("<i class='fa-solid fa-download'></i>"); //파일 다운도르 아이콘	
					otherPersonChat__chatMsg.append(myChat__fileMsg.append(fileMsg__fileName).append(fileMsg__volume).append(fileMsg__expirationDate)).append(chatMsg__download);
				} else { // 파일아니면
					otherPersonChat__chatMsg.text(resp.chat[i].content); // 채팅 내용	
				}





				let otherPersonChat__chatMsgRight = $("<div>").attr("class", "otherPersonChat__chatMsgRight d-flex");
				let chatMsgRight__align = $("<div>").attr("class", "chatMsgRight__align");
				let otherPersonChat__readCnt = $("<div>").attr("class", "otherPersonChat__readCnt").text("1"); //채팅 안 읽은 사람 수
				let otherPersonChat__sendTime = $("<div>").attr("class", "otherPersonChat__sendTime").text("오후 7:46"); // 채팅 보낸 시각

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
	});
});

function formatChatTime(timestamp) {
    let date = new Date(timestamp);

    return date.toLocaleTimeString('ko-KR', {
        hour: '2-digit',
        minute: '2-digit',
        hour12: true
    });
}
