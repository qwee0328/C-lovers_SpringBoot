package com.clovers.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

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
	private String name;
	private String pw;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp birth;
	private String email;
	private String phone;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp hire_date;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp ent_date;
	private String inactivate;
	private String job_id;
	private String dept_task_id;
	private String emp_status_id;
	private String profile_img;
	private String daily_work_rule_id;
	private String company_email;
	private String company_phone;
	
	// mypage view 에 필요한 데이터
	private String job_name;
	private String task_name;
	private String dept_id;
	private String dept_name;
	
	// 임직원 정보에 필요한 데이터
	private int sec_level; 
	
	
	
	public MemberDTO(String id, String name, String pw, Timestamp birth, String email, String phone,
			Timestamp hire_date, Timestamp ent_date, String inactivate, String job_id, String dept_task_id,
			String emp_status_id, String profile_img, String daily_work_rule_id, String company_email,
			String company_phone) {
		super();
		this.id = id;
		this.name = name;
		this.pw = pw;
		this.birth = birth;
		this.email = email;
		this.phone = phone;
		this.hire_date = hire_date;
		this.ent_date = ent_date;
		this.inactivate = inactivate;
		this.job_id = job_id;
		this.dept_task_id = dept_task_id;
		this.emp_status_id = emp_status_id;
		this.profile_img = profile_img;
		this.daily_work_rule_id = daily_work_rule_id;
		this.company_email = company_email;
		this.company_phone = company_phone;
	}
	
	
	
//	생일 형식 바꿈
	public String getFormatBirth() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		
		return sdf.format(birth);
	}
	
//	입사일 형식 바꿈
	public String getFormatHireDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		
		return sdf.format(hire_date);
	}

	
}
