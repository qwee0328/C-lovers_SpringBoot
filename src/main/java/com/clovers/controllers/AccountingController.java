package com.clovers.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.AccountingDTO;
import com.clovers.services.AccountingService;

@Controller
@RestController
@RequestMapping("/accounting")
public class AccountingController {
	// 회계지원 컨트롤러
	
	@Autowired
	private AccountingService acService;
	
	// 계좌 리스트 전부 불러오기
	@GetMapping("/selectAll")
	public ResponseEntity<List<AccountingDTO>> selectAccountAll(){
		List<AccountingDTO> list = acService.selectAccountAll();
		System.out.println("컨트롤러 리스트"+list);
		return ResponseEntity.ok(list);
	}
	
}
