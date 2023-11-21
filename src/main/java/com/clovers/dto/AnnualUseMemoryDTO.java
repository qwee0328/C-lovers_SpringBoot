package com.clovers.dto;

import java.sql.Timestamp;

import com.clovers.dto.AdminDTO.AuthorityCategories;

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
public class AnnualUseMemoryDTO {
	private int id;
	private String emp_id;
	private String rest_reason_type_id;
	private String reason;
	private Timestamp annual_date; 
}
