package com.clovers.dto;

import java.sql.Timestamp;

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
		FILE,		// 사용자가 파일메시지를 채팅방에 남겼을 때
		CHAT,		// 사용자가 채팅 메시지를 보냈을 때
		EXIT		// 사용자가 채팅방에서 완전히 퇴장했을 때 (채팅그룹과의 연관관계가 해제됨)
		
	}
	
	private int id;						// 채팅 메시지가 생성될 때마다 자동으로 증가하는 레코드 값.
	private String chat_room_id;		// 채팅방 식별자 (외래키)
	private String emp_id;				// 채팅을 보낸 사람의 식별자 (외래키)
	private String content;				// 채팅내용
	private Timestamp write_date;		// 채팅이 생성된 시간 default CURRENT_TIME
	private ChatMessageStates state;	// 채팅의 상태
	
	private String emp_name;

	public ChatMessageDTO(int id, String chat_room_id, String emp_id, String content, Timestamp write_date,
			ChatMessageStates state) {
		super();
		this.id = id;
		this.chat_room_id = chat_room_id;
		this.emp_id = emp_id;
		this.content = content;
		this.write_date = write_date;
		this.state = state;
	}
	
	
}
