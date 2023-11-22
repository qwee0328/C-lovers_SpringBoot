package com.clovers.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.DepartmentDTO;
import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.OfficeDTO;
import com.clovers.services.DepartmentService;
import com.clovers.services.DeptTaskService;
import com.clovers.services.OfficeService;
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
	
	@Autowired
	private OfficeService oService;
	
	
	
	@GetMapping("office")
	public ResponseEntity<OfficeDTO> getOfficeDTO(){
		return ResponseEntity.ok(orgService.getCompleteOrganizationStructure());
	}
	
	
	
	@PostMapping("insertNewDepartment")
	public ResponseEntity<DepartmentDTO> postDepartment(@RequestBody Map<String,String> param){
		String dept_name = param.get("dept_name");
		int result = deptService.insertNewDepartment(dept_name);
		if(result>0) {
			return ResponseEntity.ok(deptService.selectById(deptService.selectLastestIdForUpdate()));
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
	
	@PutMapping("updateOfficeName")
	public ResponseEntity<OfficeDTO> updateOfficeName(@RequestBody Map<String,String> param){
		String office_name = param.get("office_name");
		OfficeDTO odto = new OfficeDTO();
		odto.setOffice_name(office_name);
		oService.updateOfficeName(odto);
		return ResponseEntity.ok(orgService.getCompleteOrganizationStructure());
	}
	
	@PutMapping("updateOfficeEmail")
	public ResponseEntity<OfficeDTO> updateOfficeEmail(@RequestBody Map<String,String> param){
		String office_email = param.get("office_email");
		OfficeDTO odto = new OfficeDTO();
		odto.setOffice_email(office_email);
		oService.updateOfficeEmail(odto);
		return ResponseEntity.ok(orgService.getCompleteOrganizationStructure());
	}

}
