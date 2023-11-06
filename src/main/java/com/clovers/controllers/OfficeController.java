package com.clovers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.JobDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.services.OfficeService;

@Controller
@RestController
@RequestMapping("/office")
public class OfficeController {
	// 오피스 관리 컨트롤러
	@Autowired
	private OfficeService oservice;
	
	@GetMapping("/detpTask")
	public ResponseEntity<List<DeptTaskDTO>> selectDeptTaskAll(){
		List<DeptTaskDTO> list = oservice.selectDeptTaskAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/position")
	public ResponseEntity<List<JobDTO>> selectPositionAll(){
		List<JobDTO> list = oservice.selectPositionAll();
		return ResponseEntity.ok(list);
	}
	
	@PostMapping("/userInsert")
	public ResponseEntity<Integer> insertUser(@RequestBody MemberDTO dto){
		int result = oservice.insertUser(dto);
		return ResponseEntity.ok(result);
	}
}
