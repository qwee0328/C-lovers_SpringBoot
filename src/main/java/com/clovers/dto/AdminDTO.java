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
		TOTAL_MANAGER,				// 전체 관리자
		HUMAN_RESOURCE_MANAGER,		// 인사 관리자
		ELECTRIC_APPROVAL_MANAGER,	// 전자결재 관리자
		RESERVATION_MANAGER			// 예약 관리자
	}
	
	private String emp_id;
	private Timestamp reg_date;
	private AuthorityCategories autority_category_id;
}
