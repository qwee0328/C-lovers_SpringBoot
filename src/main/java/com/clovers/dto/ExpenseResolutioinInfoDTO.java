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
public class ExpenseResolutioinInfoDTO {
	private int id;
	private String document_id;
	private String expense_category;
	private Timestamp expense_date;
	private String spender_id;
	private String summary;
}
