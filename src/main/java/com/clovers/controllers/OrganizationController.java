package com.clovers.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	
	@PostMapping("insertNewDeptTask")
	public ResponseEntity<DeptTaskDTO> insertNewDeptTask(@RequestBody Map<String,String> param ){
		String newId = dtaskService.generateNewId();
		String task_name = param.get("task_name");
		String dept_id = param.get("dept_id");
		
		int result = dtaskService.insert(newId,task_name,dept_id);
		if(result>0) {
			return ResponseEntity.ok(dtaskService.selectById(newId));
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
	
	@PutMapping("updateDeptNameModify")
	public ResponseEntity<DepartmentDTO> updateDeptNameModify(@RequestBody Map<String,String> param){
		String dept_name = param.get("dept_name");
		String id = param.get("id");
		int result = deptService.update(dept_name, id);
		if(result > 0) {
			return ResponseEntity.ok(deptService.selectById(id));
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}	
	}
	
	@PutMapping("updateTaskNameModify")
	public ResponseEntity<DeptTaskDTO> updateTaskNameModify(@RequestBody Map<String,String> param){
		String id = param.get("id");
		String task_name = param.get("task_name");
		int result =  dtaskService.updateTaskName(task_name, id);
		if(result>0) {
			return ResponseEntity.ok(dtaskService.selectById(id));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PutMapping("updateTaskDeptIdModify")
	public ResponseEntity<DeptTaskDTO> updateTaskDeptIdModify(@RequestBody Map<String,String> param){
		String id = param.get("id");
		String dept_id = param.get("dept_id");
		int result =  dtaskService.updateDeptId(dept_id, id);
		if(result>0) {
			return ResponseEntity.ok(dtaskService.selectById(id));
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("deleteDepartmentById")
	public ResponseEntity<Void> deleteDepartmentById(@RequestBody Map<String,String> param){
		String id = param.get("id");
		int result = deptService.deleteById(id);
		if(result > 0) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}	
	}
	
	@DeleteMapping("deleteTaskById")
	public ResponseEntity<Void> deleteTaskById(@RequestBody Map<String,String> param){
		String id = param.get("id");
		int result = dtaskService.delete(id);
		if(result > 0) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}	
	}
	
	
	
	

}
