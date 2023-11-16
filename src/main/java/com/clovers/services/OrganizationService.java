package com.clovers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dto.OfficeDTO;

@Service
public class OrganizationService {
	
	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	private DeptTaskService dtaskService;
	
	@Autowired
	private OfficeService oService;
	
//	public OfficeDTO getCompleteOrganizationStructure() {
//		OfficeDTO office = oService.selectOfficeInfo();
//		
//	}

}
