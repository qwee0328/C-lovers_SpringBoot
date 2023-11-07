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
public class ChatFileDTO {
	private int id;					// 채팅에서 올라온 파일 레코드 기본키
	private int chat_message_id;	// 채팅 메시지 id값 (외래키)
	private String ori_name;		// 업로드한 파일의 원래 이름
	private String sys_name;		// 업로드한 파일이 서버에 저장될 때의 이름
}
