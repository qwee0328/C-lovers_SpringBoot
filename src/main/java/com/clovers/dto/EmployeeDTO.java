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
public class EmployeeDTO {
	private String id;
	private String name;
	private String pw;
	private Timestamp birth;
	private String email;
	private String phone;
	private Timestamp hire_date;
	private Timestamp ent_date;
	private int inactivate;
	private String job_id;
	private String dept_id;
	private String emp_status_id;
	private String daily_work_rule_id;
}