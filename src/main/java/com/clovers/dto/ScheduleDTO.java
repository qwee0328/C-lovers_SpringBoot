package com.clovers.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScheduleDTO {
	private int id;
	private String emp_id;
	private int calendar_id;
	private String title;
	private String content;
	private String start_date;
	private String end_date;
	private boolean all_day;
	private String reg_date;
	private int recurring_id;
}
