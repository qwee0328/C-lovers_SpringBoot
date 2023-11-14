package com.clovers.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessageDTO {
	
	public enum ChatMessageStates{
		JOIN,		// 사용자가 채팅방에 입장했을 때
		LEAVE,		// 사용자가 채팅방에서 일시적으로 나갔을 때
		FILE,		// 사용자가 파일메시지를 채팅방에 남겼을 때
		CHAT,		// 사용자가 채팅 메시지를 보냈을 때
		REJOIN,		// 사용자가 채팅방에 다시 들어 왔을 때 
		EXIT		// 사용자가 채팅방에서 완전히 퇴장했을 때 (채팅그룹과의 연관관계가 해제됨)
		
	}
	
	private int id;						// 채팅 메시지가 생성될 때마다 자동으로 증가하는 레코드 값.
	private String chat_room_id;		// 채팅방 식별자 (외래키)
	private String emp_id;				// 채팅을 보낸 사람의 식별자 (외래키)
	private String content;				// 채팅내용
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "a hh:mm", timezone = "Asia/Seoul")
	private Timestamp write_date;		// 채팅이 생성된 시간 default CURRENT_TIME
	private ChatMessageStates state;	// 채팅의 상태
}
