package com.clovers.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class AdminDTO {
	
	public enum AuthorityCategories{
		총괄,
		인사,
		전자결재,
		회계
	}
	private int id;
	private String emp_id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Seoul")
	private Timestamp reg_date;
	private AuthorityCategories authority_category_id;
	private int exists;
}
