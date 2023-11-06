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
	private int id;
	private int chat_id;
	private String emp_id;
	private String Content;
	private Timestamp write_date;
	private int state;
}
