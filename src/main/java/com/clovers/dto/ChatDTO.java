package com.clovers.dto;

import java.util.UUID;

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
public class ChatDTO {
	public enum ChatRoomStates{
		ACTIVATE(0),
		INACTVATE(1);
		
		private int code;

		ChatRoomStates(int code){
			this.code = code;
		}
		
		public int getCode() {
			return code;
		}
	};
	
	private String id;				// 채팅방 아이디
	private ChatRoomStates state;	// 채팅방 상태
	
	public static ChatDTO create(ChatRoomStates state) {
        ChatDTO room = new ChatDTO();
        room.id = UUID.randomUUID().toString();
        room.state = state;
        return room;
    }

}
