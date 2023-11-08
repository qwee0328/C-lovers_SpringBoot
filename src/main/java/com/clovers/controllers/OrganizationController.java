package com.clovers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.DepartmentDTO;
import com.clovers.dto.DeptTaskDTO;
import com.clovers.services.DepartmentService;
import com.clovers.services.DeptTaskService;

@RestController
@RequestMapping("/org/")
public class OrganizationController {
	//부서, 직무 관련 컨트롤러
	
	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	private DeptTaskService dtaskService;
	
	
	@GetMapping("office")
	public ResponseEntity<DepartmentDTO> getOfficeDTO(){
		return ResponseEntity.ok(deptService.selectOffice());
	}
	
	@GetMapping("getDepartment")
	public ResponseEntity<List<DepartmentDTO>> getDepartmentDTO(){
		return ResponseEntity.ok(deptService.selectAllWithOutOfficeId());
	}
	
	@GetMapping("getDeptTask/{dept_id}")
	public ResponseEntity<List<DeptTaskDTO>> getDeptTaskDTOByDeptId(@PathVariable String dept_id){
		return ResponseEntity.ok(dtaskService.selectByDeptId(dept_id));
	}
	
	@PostMapping("postDepartment")
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
