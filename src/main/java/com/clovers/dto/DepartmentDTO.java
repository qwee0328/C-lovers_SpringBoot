package com.clovers.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class DepartmentDTO {
	private String id;
	private String dept_name;
	private String office_id;
	private List<DeptTaskDTO> deptTask;	// 실제 DB에 들어가지 않는 모델들
	private int dept_officer;
	
}
