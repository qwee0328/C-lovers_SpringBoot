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
	private int id;
	private int chat_message_id;
	private String ori_name;
	private String sys_name;
}
