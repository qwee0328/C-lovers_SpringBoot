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

import com.clovers.dto.AdminDTO;
import com.clovers.services.AdminService;

@RestController
@RequestMapping("/adminmanage/")
public class AdminRestController {

	@Autowired
	private AdminService aService;

	@GetMapping("selectAdmin")
	public ResponseEntity<List<Map<String, Object>>> getAdminInfo() {
		return ResponseEntity.ok(aService.selectAll());
	}

	@GetMapping("selectAdminCount")
	public ResponseEntity<List<Map<String, Object>>> getAdminCount() {
		return ResponseEntity.ok(aService.selectAllCount());
	}

	@GetMapping("selectAuthorityCategories")
	public ResponseEntity<List<String>> getAuthorityCategories() {
		return ResponseEntity.ok(aService.selectAuthorityCategories());
	}

	@PostMapping("insert")
	public ResponseEntity<Map<String,Object>> insertAdmin(@RequestBody AdminDTO dto) {
		aService.insert(dto);
		Map<String,Object> response = aService.selectByAdminId(aService.newestId());
		
		return ResponseEntity.ok(response);

	}

	@PutMapping("updateAdminInfo")
	public ResponseEntity<Void> updateAdmin(@RequestBody List<Map<String, Object>> params) {
		aService.updateAdminInfo(params);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("deleteById")
	public ResponseEntity<Void> deleteById(@RequestBody List<Integer> checkItems) {
		aService.deleteById(checkItems);
		return ResponseEntity.ok().build();
	}

}
