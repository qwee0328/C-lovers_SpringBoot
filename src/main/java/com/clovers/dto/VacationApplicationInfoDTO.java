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
	private int vacation_document_id;
	private Timestamp vacation_date;
	private String rest_reson_type;
	private String vacation_reason;
}
