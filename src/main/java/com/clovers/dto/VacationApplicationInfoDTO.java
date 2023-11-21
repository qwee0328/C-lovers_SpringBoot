package com.clovers.dto;

import java.sql.Timestamp;

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
public class VacationApplicationInfoDTO {
	private int id;
	private String document_id;
	private Timestamp vacation_date;
	private String rest_reason_type;
	private String vacation_reason;
}
