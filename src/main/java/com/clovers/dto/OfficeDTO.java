package com.clovers.dto;

import java.util.List;

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
public class OfficeDTO {
	private String id;
	private String office_name;
	private String office_email;
	private int total_officer;				// 실제 DB에 들어가지 않는 데이터
	private List<DepartmentDTO> department; // 실제 DB에 들어가지 않는 데이터

}
