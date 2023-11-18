package com.clovers.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.web.socket.WebSocketSession;

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
		ACTIVEPERSONAL,
		ACTIVEGROUP,
		INACTIVE;
	};
	
	private String id;				// 채팅방 아이디
	private ChatRoomStates state;	// 채팅방 상태
	private Set<WebSocketSession> sessions = new HashSet<>();
	
	
	public static ChatRoomDTO create(ChatRoomStates state) {
        ChatRoomDTO room = new ChatRoomDTO();
        room.id = UUID.randomUUID().toString();
        room.state = state;
        return room;
    }
	
//	public void handleAction(WebSocketSession session, )

}
