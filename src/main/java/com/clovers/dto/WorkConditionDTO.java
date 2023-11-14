package com.clovers.dto;

import java.sql.Timestamp;

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
public class WorkConditionDTO {
	private int id;
	private String attend_status_id;
	private String work_condition_status_id;
	private String emp_id;
	private Timestamp start_time;
	private Timestamp end_time;
	private Timestamp work_date;
}
