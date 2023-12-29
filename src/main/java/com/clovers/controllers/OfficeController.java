package com.clovers.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.JobDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.services.OfficeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RestController
@RequestMapping("/office")
public class OfficeController {
	// 오피스 관리 컨트롤러
	@Autowired
	private OfficeService oservice;
	
	@Autowired
	private HttpSession session;
	
	// 부서 명 불러오기
	@GetMapping("/detpTask")
	public ResponseEntity<List<DeptTaskDTO>> selectDeptTaskAll(){
		List<DeptTaskDTO> list = oservice.selectDeptTaskAll();
		return ResponseEntity.ok(list);
	}
	
	// 직급 명 불러오기
	@GetMapping("/job")
	public ResponseEntity<List<JobDTO>> selectPositionAll(){
		List<JobDTO> list = oservice.selectPositionAll();
		return ResponseEntity.ok(list);
	}
	
	// 실제 db에 저장된 실 사용자 수 불러오기
	@GetMapping("/empCount")
	@RequestMapping("/empCount")
	public ResponseEntity<Integer> selectRealEmpCount(){
		int count= oservice.selectRealEmpCount();
		return ResponseEntity.ok(count);
	}
	
	// 사용자 리스트 불러오기
	@GetMapping("/userList")
	public ResponseEntity<List<Map<String, String>>> selectUserList(){
		List<Map<String, String>> list = oservice.selectUserList();
		return ResponseEntity.ok(list);
	}
	
	// 사내 전화번호 사용중인번호인지 체크
	@GetMapping("/usingCompanyPhoneCheck")
	public ResponseEntity<Boolean> usingCompanyPhoneCheck(@RequestParam("company_phone") String companyPhone){
		int count = oservice.usingCompanyPhoneCheck(companyPhone);
		boolean result = count>0;
		return ResponseEntity.ok(result);
	}
	
	// 사용자 등록하기
	@PostMapping("/userInsert")
	public ResponseEntity<Integer> insertUser(@RequestBody MemberDTO dto) throws Exception{
		int result = oservice.insertUser(dto);
		return ResponseEntity.ok(result);
	}
	
	// 사용자 삭제하기
	@PostMapping("/userDelete")
	public ResponseEntity<Integer> deleteUser(@RequestBody List<String> userID){
		int result = oservice.deleteUser(userID);
		return ResponseEntity.ok(result);
	}
	
	// 사용자 직위 수정하기
	@PostMapping("/updateUserJob")
	public ResponseEntity<Integer> updateUserJob(@RequestBody List<MemberDTO> dtoList){
		oservice.updateUserJob(dtoList);
		return ResponseEntity.ok().build();
	}
	
	// 사용자 소속 조직 수정하기
	@PostMapping("/updateUserDeptTask")
	public ResponseEntity<Integer> updateUserDeptTask(@RequestBody List<MemberDTO> dtoList){
		oservice.updateUserDeptTask(dtoList);
		return ResponseEntity.ok().build();
	}
	
	// 사용자 이름, id 검색하기
	@GetMapping("/searchUser")
	public ResponseEntity<List<Map<String, String>>> searchUser(@RequestParam("keyword")String keyword){
		List<Map<String, String>> list = oservice.searchUser(keyword);
		return ResponseEntity.ok(list);
	}
	
	@ResponseBody
	@RequestMapping("/searchUserAjax")
	public List<Map<String, String>> searchUserAjax(@RequestParam("keyword") String keyword){
		return oservice.searchUser(keyword);
	}
	
	// 부서별 부서명, 인원 수 불러오기
	@ResponseBody
	@RequestMapping("/selectDeptInfo")
	public List<Map<String, Object>> selectDeptInfo(){
		return oservice.selectDeptInfo();
	}
	
	// 부서별 팀별 인원 수 불러오기
	@ResponseBody
	@RequestMapping("/selectTaskInfo")
	public List<Map<String, Object>> selectTaskInfo(){
		return oservice.selectTaskInfo();
	}
	
	// 부서별 인원 정보 불러오기 - 이름, 부서명, id
	@ResponseBody
	@RequestMapping("/selectDepartmentEmpInfo")
	public List<Map<String, Object>> selectDepartmentEmpInfo(String dept_id){
		return oservice.selectDepartmentEmpInfo(dept_id);
	}
	
	// 모든 부서별 정보 불러오기 - 이름, 부서명, id
	@ResponseBody
	@RequestMapping("/selectAllEmpInfo")
	public List<Map<String, Object>> selectAllEmpInfo(){
		return oservice.selectAllEmpInfo();
	}
	
	// 팀별 인원 정보 불러오기 - 이름, 부서명, id
	@ResponseBody
	@RequestMapping("/selectDetpTaskEmpInfo")
	public List<Map<String, Object>> selectDetpTaskEmpInfo(String task_id){
		return oservice.selectDetpTaskEmpInfo(task_id);
	}
	

	// 팀별(생산1팀,2팀..)인원수, 부서명
	@ResponseBody
	@RequestMapping("/selectAllTaskNameEmpo")
	public List<Map<String, Object>> selectAllTaskNameEmpo(){
		return oservice.selectAllTaskNameEmpo();
	}
	
	// 임직원 정보에서 부서 클릭하면 정보
	@ResponseBody
	@RequestMapping("/selectDeptEmpo")
	public List<Map<String, Object>> selectDeptEmpo(){
		return oservice.selectDeptEmpo();
	}
	
	// 부서 클릭하면 정보 
	@ResponseBody
	@RequestMapping("/selectByDeptName")
	public List<Map<String, Object>> selectByDeptName(String dept_name){
		return oservice.selectByDeptName(dept_name);
	}
	
	// 팀 클릭하면 정보
	@ResponseBody
	@RequestMapping("/selectByTaskName")
	public List<Map<String, Object>> selectByTaskName(String task_name){
		return oservice.selectByTaskName(task_name);
	}
	
	// 임직원 검색
	@ResponseBody
	@RequestMapping("/searchByName")
	public List<Map<String, Object>> searchByName(String name){
		return oservice.searchByName(name);
	}
	
	// 회사 이름 불러오기
	@ResponseBody
	@RequestMapping("/selectOfficeName")
	public String selectOfficeName() {
		return oservice.selectOfficeName();
	}
	
	
	// 내 정보 불러오기 - 이름, 부서명, id, session 정보 이용 (일정 메뉴 - 공유 캘린더 insert modal에서 사용)
	@RequestMapping("/getMyInfo")
	public Map<String, String> searchUser(){
		return oservice.searchUser((String)session.getAttribute("loginID")).get(0);
	}
}
