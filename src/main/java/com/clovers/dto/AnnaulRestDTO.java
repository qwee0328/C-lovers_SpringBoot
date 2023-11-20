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
public class AnnaulRestDTO {
	private int id;
	private String emp_id;
	private String rest_type_id;
	private int rest_cnt;
	private Timestamp reg_date;
}
