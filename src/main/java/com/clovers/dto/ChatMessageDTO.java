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
		CHAT(0),
		JOIN(1),
		LEAVE(2);
		
		private int code;

		ChatMessageStates(int code){
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
	}
	
	private int id;					// 채팅 메시지가 생성될 때마다 자동으로 증가하는 레코드 값.
	private int chat_id;			// 채팅방 식별자 (외래키)
	private String emp_id;			// 채팅을 보낸 사람의 식별자 (외래키)
	private String Content;			// 채팅내용
	private Timestamp write_date;	// 채팅이 생성된 시간 default CURRENT_TIME
	private ChatMessageStates state;				// 채팅의 상태
}
