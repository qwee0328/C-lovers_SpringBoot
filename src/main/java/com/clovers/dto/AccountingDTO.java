package com.clovers.dto;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

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
public class AccountingDTO {
	private int num;
	private String id;
	private String emp_id;
	private String bank;
	private String name;
	private Timestamp reg_date;
	
	public String getFormatRegDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		
		return sdf.format(reg_date);
	}

}


