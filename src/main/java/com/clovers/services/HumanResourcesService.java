package com.clovers.services;

import java.util.HashMap;
import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.HumanResourcesDAO;
import com.clovers.dto.MemberDTO;
import com.clovers.dto.WorkConditionDTO;

@Service
public class HumanResourcesService {
	// 인사 서비스 레이어
	
	@Autowired
	private HumanResourcesDAO dao;
	
//	public String selectByIdGetName(String id) {
//		return hrdao.selectByIdGetName(id);
//	}
	
	public MemberDTO selectById(String id) {
		return dao.selectById(id);
	}
	
	public String reChangePw(String id) {
		return dao.reChangePw(id);
	}
	
	public int update(String id,String profile_img,String company_phone,String phone,String email) {
		
		Map<String,String> param = new HashMap<>();
		param.put("id", id);
		param.put("profile_img", profile_img);
		param.put("company_phone", company_phone);
		param.put("phone", phone);
		param.put("email", email);
		
		return dao.update(param);
	}
	
	// 사용자 근무 규칙 정보 불러오기
	public Map<String, Object> selectEmployeeWorkRule(String id) {
		return dao.selectEmployeeWorkRule(id);
	}
	

	// 사용자 근무 규칙 정보 불러오기
//	public Map<String, Object> selectEmployeeWorkRule(String id) {
//		return dao.selectEmployeeWorkRule(id);
//	}

	// 출근전인지 확인
	public Map<String, Object> selectAttendStatus(String id) {
		return dao.selectAttendStatus(id);
	}
	
	// 출근 기록 남기기
	@Transactional
	public int insertAttendingWork(String id) {
		Map<String, Object> user = dao.selectEmployeeWorkRule(id);
		
		// 현재 시간
        Instant now = Instant.now();
        LocalTime attend_time = ((Time) user.get("attend_time")).toLocalTime();
        Instant instant = attend_time.atDate(LocalDate.now())
        	    .atZone(ZoneId.systemDefault())
        	    .toInstant();
        
        // 두 시간 간의 차이 계산
        Duration duration = Duration.between(now, instant).abs(); // 두 시간의 차이 계산 (음수 방지를 위해 abs() 사용)
        long differenceInSeconds = duration.getSeconds(); // 차이를 초 단위로 변환
        
        System.out.println(differenceInSeconds);
        if(differenceInSeconds/60>=1) {
        	user.replace("daily_work_rule_id", "지각");
        	return dao.insertAttendingWork(user);
        }else {
        	return dao.insertAttendingWork(user);
        }
	}

	// 퇴근 기록 남기기
	public int updateLeavingWork(String id) {
		return dao.updateLeavingWork(id);
	}
	
	// 업무상태 기록 남기기
	@Transactional
	public WorkConditionDTO insertWorkCondition(String id, String status) {
		int attend_status_id = (int) dao.selectAttendStatus(id).get("id");
		
		Map<String, Object> data = new HashMap<>();
	    data.put("id", id);
	    data.put("status", status);
	    data.put("attend_status_id", attend_status_id);
	    
	    // 이전에 사용하던 업무상태 종료하기
	    dao.updateWorkCondtionEndTime(attend_status_id);
	    // 업무상태 삽입 후 id 받아오기
	    int workConditionId = dao.insertWorkCondition(data);
	    System.out.println(workConditionId);
	    
	    // 삽입한 업무 상태 확인
	    return dao.selectWorkCondition(workConditionId);
	}
	
	// 업무 상태 리스트 불러오기
	public List<WorkConditionDTO> selectWorkConditionsList(String id){
		return dao.selectWorkConditionsList(id);
	}
	
	// 휴가 사유 구분 불러오기
	public List<String> selectRestReasonType() {
		return dao.selectRestReasonType();
	}
	
	// 임직원 정보 전부 불러오기
	public List<MemberDTO> employeeSelectAll() {
		return dao.employeeSelectAll();
	}
	
}
