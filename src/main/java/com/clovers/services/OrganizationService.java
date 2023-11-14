package com.clovers.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dto.DepartmentDTO;
import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.OfficeDTO;

@Service
public class OrganizationService {
	
	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	private DeptTaskService dtaskService;
	
	@Autowired
	private OfficeService oService;
	
	@Transactional
	public OfficeDTO getCompleteOrganizationStructure() {
		OfficeDTO office = oService.selectOfficeInfo();
		office.setTotal_officer(oService.selectEmpCount());
		List<DepartmentDTO> departments = deptService.selectAll();
		for(DepartmentDTO dept : departments) {
			List<DeptTaskDTO> depttasks = dtaskService.selectByDeptId(dept.getId());
			for(DeptTaskDTO depttask : depttasks) {
				depttask.setDept_task_officer(dtaskService.selectEmpCountById(depttask.getId()));
			}
			dept.setDeptTask(depttasks);
			dept.setDept_officer(deptService.selectEmpCountById(dept.getId()));;
		}
		office.setDepartment(departments);
		return office;
	}

}
