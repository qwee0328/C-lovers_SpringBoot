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
public class DocumentApprovalsDTO {
	private int id;
	private String document_id;
	private String emp_id;
	private String approval;
	private int sec_level;
}
