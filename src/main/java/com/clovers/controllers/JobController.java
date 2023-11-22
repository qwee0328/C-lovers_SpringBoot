package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.JobDTO;
import com.clovers.services.JobService;

@RestController
@RequestMapping("/job/")
public class JobController {
	
	@Autowired
	private JobService jService;
	
	
	@GetMapping("getJobInfo")
	public ResponseEntity<List<Map<String,Object>>> getJobInfo(){
		return ResponseEntity.ok(jService.selectAllAcsSecLevelWithOutJobId());
	}
	
	@GetMapping("getNewestJobInfo")
	public ResponseEntity<Map<String,Object>> getNewestJobInfo(){
		return ResponseEntity.ok(jService.selectNewestSecLevelWithOutJobId());
	}
	
	@PostMapping("insertNewJob")
	public ResponseEntity<Map<String,Object>> insertJob(@RequestBody String job_name){
		jService.insertNewJob(job_name);
		return ResponseEntity.ok(jService.selectNewestSecLevelWithOutJobId());
	}
	
	@PutMapping("updateJobNameById")
	public ResponseEntity<Void> updateJobNameById(@RequestBody JobDTO dto){
		jService.updateJobNameById(dto);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("updateSecLevelById")
	public ResponseEntity<Void> updateSecLevelById(@RequestBody JobDTO dto){
		jService.updateSecLevelById(dto);
		return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("deleteById")
	public ResponseEntity<Void> deleteById(@RequestBody int id){
		jService.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	

}
