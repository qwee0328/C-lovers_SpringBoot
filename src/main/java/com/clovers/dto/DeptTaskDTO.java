package com.clovers.dto;

import java.util.List;

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
	private int dept_task_officer; //DB와 상관없는 데이터를 받기 위해 만든 함수
	private List<MemberDTO> officers;
}
