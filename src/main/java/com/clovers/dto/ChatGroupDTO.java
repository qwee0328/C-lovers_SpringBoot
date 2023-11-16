package com.clovers.dto;

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
public class ChatGroupDTO {
	private int id;				// 레코드 고유 식별자. 채팅 그룹에 사용자 추가 시 새 레코드 
	private String emp_id;		// 채팅 그룹에 추가된 사용자의 식별자
	private int chat_id;		// 이 필드는 채팅 그룹의 식별자로, 같은 chat_id를 가진 레코드들은 동일한 채팅 그룹
	private int msg_start_id;	// 이 필드는 사용자가 그룹에 참여한 후 받은 첫 번째 메시지의 식별자
	private int msg_end_id;		// 이 필드는 사용자가 그룹에서 받은 마지막 메시지의 식별자
	private String name;		// 채팅방 이름(각자 바꿀 수 있음)
}
