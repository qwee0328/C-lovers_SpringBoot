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
public class ScheduleRecurringDTO {
	private int id;
	private int during;
	private String frequency_whenOption;
	private String endKey;
	private String endValue;
	private int intervalCnt;
	private String startTime;
	private String endTime;
	private String selectWeeks;
}
