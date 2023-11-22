package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<Map<String,Object>> insertJob(@RequestBody Map<String, String> jobData){
		String job_name = jobData.get("job_name");
		jService.insertNewJob(job_name);
		return ResponseEntity.ok(jService.selectNewestSecLevelWithOutJobId());
	}
	
	@PutMapping("updateById")
	public ResponseEntity<Void> updateJobNameById(@RequestBody JobDTO dto){
		jService.updateById(dto);
		return ResponseEntity.ok().build();
	}
	
	
	@DeleteMapping("deleteById/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable String id){
	    jService.deleteById(id);
	    return ResponseEntity.ok().build();
	}

	
	

}
