package com.clovers.controllers;

import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.AccountingDTO;
import com.clovers.services.AccountingService;


@Controller
@RestController
@RequestMapping("/api/accounting")
public class AccountingController {
	// 회계지원 컨트롤러
	
	@Autowired
	private AccountingService acService;
	
	/////// 직원 계좌 ///////
	// 계좌 리스트 전부 불러오기
	@GetMapping("/accountAll")
	public ResponseEntity<List<AccountingDTO>> selectAccountAll(){
		List<AccountingDTO> list = acService.selectAccountAll();
		return ResponseEntity.ok(list);
	}
	//이름, 사번으로 검색
	@GetMapping("/search")
	public ResponseEntity<List<AccountingDTO>> searchBy(String keyword){
		System.out.println(keyword);
		List<AccountingDTO> list = acService.searchBy(keyword);
		System.out.println(list);
		return ResponseEntity.ok(list);
	}
	// 계좌 추가
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody AccountingDTO dto){
		acService.insert(dto);
		return ResponseEntity.ok("성공");
	}
	// id로 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccount(@PathVariable String id){
		acService.deleteAccount(id);
		return ResponseEntity.ok("삭제완료");
	}
	// 계좌 수정
	@PutMapping
	public ResponseEntity<String> update(@RequestBody AccountingDTO dto){
		acService.update(dto);
		
		return ResponseEntity.ok("수정 완료");
	}
	
	////// 법인 카드 ////
	// 카드 리스트 전부 부르기
	@GetMapping("/cardAll")
	public ResponseEntity<List<AccountingDTO>> selectCardAll(){
		List<AccountingDTO> list = acService.selectCardAll();
		return ResponseEntity.ok(list);
	}
	// 검색
	@GetMapping("/searchCard")
	public ResponseEntity<List<AccountingDTO>> searchCard(String keyword){
		System.out.println(keyword);
		List<AccountingDTO> list = acService.searchCard(keyword);
		return ResponseEntity.ok(list);
	}
	// 카드 추가
	@PostMapping("/cardInsert")
	public ResponseEntity<String> insertCard(@RequestBody AccountingDTO dto){
		acService.insertCard(dto);
		return ResponseEntity.ok("성공");
	}
	// 카드 삭제
	@DeleteMapping("/deleteCard/{id}")
	public ResponseEntity<String> deleteCard(@PathVariable String id){
		acService.deleteCard(id);
		return ResponseEntity.ok("삭제완료");
	}
	// 카드 수정
	@PutMapping("/updateCard")
	public ResponseEntity<String> updateCard(@RequestBody AccountingDTO dto){
		acService.updateCard(dto);
		return ResponseEntity.ok("수정 완료");
	}
}
