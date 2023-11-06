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
public class MemberDTO {
	private String id;
	private String job_id;
	private String dept_id;
	private String emp_status_id;
	private String daily_work_rule_id;
	private String name;
	private String pw;
	private String birth;
	private String email;
	private String phone;
	private String hire_date;
	private String ent_date;
	private String inactivate;
}
