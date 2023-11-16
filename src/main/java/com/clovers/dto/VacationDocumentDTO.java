package com.clovers.dto;

import java.sql.Timestamp;
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
public class VacationDocumentDTO {
	private int id;
	private String document_type_id;
	private String emp_id;
	private int save_period;
	private String security_grade;
	private Timestamp report_date;
	private Timestamp approval_date;
	private Timestamp edit_date;
}
