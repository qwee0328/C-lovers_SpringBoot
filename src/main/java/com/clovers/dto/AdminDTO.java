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
public class AdminDTO {
	
	public enum AuthorityCategories{
		TOTAL_MANAGER,HUMAN_RESOURCE_MANAGER,ELECTRIC_APPROVAL_MANAGER,RESERVATION_MANAGER
	}
	
	private String emp_id;
	private Timestamp reg_date;
	private AuthorityCategories autority_category_id;
}
