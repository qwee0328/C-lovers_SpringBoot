package com.clovers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.services.OfficeService;

@RestController
@RequestMapping("/office/detpTask")
public class UserRegistraionController {
	@Autowired
	private OfficeService oservice;
	
//	@GetMapping
//	public ResponseEntity<List<String>> selectDeptTaskAll(){
//		List<String> list = oservice.selectDeptTaskAll();
//		System.out.println("1");
//		for(String item:list) {
//			System.out.println(item);
//		}
//		return ResponseEntity.ok(list);
//	}
}
