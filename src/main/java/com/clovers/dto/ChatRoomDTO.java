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
public class ChatRoomDTO {
	public enum ChatRoomStates{
		ACTIVATE,
		INACTVATE;
	};
	
	private String id;				// 채팅방 아이디
	private ChatRoomStates state;	// 채팅방 상태
	
	public static ChatRoomDTO create(ChatRoomStates state) {
        ChatRoomDTO room = new ChatRoomDTO();
        room.id = UUID.randomUUID().toString();
        room.state = state;
        return room;
    }

}
