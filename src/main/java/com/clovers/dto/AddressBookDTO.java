package com.clovers.dto;

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
public class AddressBookDTO {
	private int id;
	private String name;
	private int is_share;
	private String emp_id;
	private String email;
	private String numberType;
	private String number;
	private String company_name;
	private String dept_name;
	private String job_name;
	private String addressType;
	private String address;
	private String birthType;
	private String birth;
	private String memo;
	private int trash;
}