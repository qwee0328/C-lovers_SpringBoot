package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public ResponseEntity<List<Map<String,Object>>> getAdminInfo(){
		return ResponseEntity.ok(aService.selectAll());
	}
	
	@GetMapping("selectAdminCount")
	public ResponseEntity<List<Map<String,Object>>> getAdminCount(){
		return ResponseEntity.ok(aService.selectAllCount());
	}
	
	@GetMapping("selectAuthorityCategories")
	public ResponseEntity<List<String>> getAuthorityCategories(){
		return ResponseEntity.ok(aService.selectAuthorityCategories());
	}
	
	@PostMapping("insert")
	public ResponseEntity<Void> insertAdmin(AdminDTO adto){
		int result = aService.insert(adto);
		if(result > 0) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(400).build();
		}
	}
	
	@PutMapping("updateAdminInfo")
	public ResponseEntity<Void> insertAdmin(String authority_category_id, int id){
		int result = aService.updateAdminInfo(AdminDTO.AuthorityCategories.valueOf(authority_category_id), id);
		if(result > 0) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(400).build();
		}
	}
	
	@DeleteMapping("deleteById")
	public ResponseEntity<Void> deleteById(int id){
		int result = aService.deleteById(id);
		if(result > 0) {
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.status(400).build();
		}
	}
	
	
	
}
