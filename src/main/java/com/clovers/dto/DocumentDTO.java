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
public class DocumentDTO {
	private String id;
	private String document_type_id;
	private int save_period;
	private String security_grade;
	private Timestamp report_date;
	private Timestamp approval_date;
	private String title;
	private String content;
	private boolean temporary;
	private String status;
	private String emp_id;
}
