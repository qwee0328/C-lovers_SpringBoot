package com.clovers.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.services.ElectronicSignatureService;

@Controller
@RequestMapping("/electronicSignature")
public class ElectronicSignatureController {
	// 전자결재 컨트롤러
	@Autowired
	private ElectronicSignatureService esservices;

	// 멤버의 전자 결재를 위한 전자선 정렬 -> job_id의 순서대로 정렬
	@ResponseBody
	@RequestMapping("/selectEmpJobLevel")
	public List<Map<String, Object>> selectEmpJobLevel(
			@RequestParam("processUserID[]") List<String> processUserIDList) {
		// List<String> userList = requestBody.get("userIdList");
		System.out.println(processUserIDList);
		// return null;
		return esservices.selectEmpJobLevel(processUserIDList);
	}
}
