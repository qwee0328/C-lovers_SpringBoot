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
public class NoticeDTO {
	private int id;
	private Timestamp create_date;
	private Timestamp check_date;
	private int isChecked;
	private String status;
	private String title;
	private String content;
	private String emp_recv_id;
	private String emp_send_id;
	private String notice_type_id;

}
