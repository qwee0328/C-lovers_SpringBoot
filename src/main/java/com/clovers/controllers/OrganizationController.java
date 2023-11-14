package com.clovers.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.DepartmentDTO;
import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.OfficeDTO;
import com.clovers.services.DepartmentService;
import com.clovers.services.DeptTaskService;
import com.clovers.services.OrganizationService;

@RestController
@RequestMapping("/org/")
public class OrganizationController {
	//상위부서, 부서 관련 컨트롤러
	
	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	private DeptTaskService dtaskService;
	
	
	@Autowired
	private OrganizationService orgService;
	
	
	@GetMapping("office")
	public ResponseEntity<OfficeDTO> getOfficeDTO(){
		return ResponseEntity.ok(orgService.getCompleteOrganizationStructure());
	}
	
	@PostMapping("postOffice")
	public ResponseEntity<Void> postDepartment(@RequestBody DepartmentDTO deptDTO){
		String newId = deptService.generateNewId();
		deptDTO.setId(newId);
		int result = deptService.insert(deptDTO);
		if(result>0) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping("postDeptTask")
	public ResponseEntity<Void> postDeptTask(@RequestBody DeptTaskDTO dtaskDTO ){
		String newId = dtaskService.generateNewId();
		dtaskDTO.setId(newId);
		int result = dtaskService.insert(dtaskDTO);
		if(result>0) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}	

}
