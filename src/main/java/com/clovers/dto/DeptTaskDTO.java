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
public class DeptTaskDTO {
	private String id;
	private String task_name;
	private String dept_id;
	
	private int dept_task_officer;// DB에 들어가지 않는 데이터
	
}
