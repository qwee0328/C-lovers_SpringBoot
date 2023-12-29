package com.clovers.services;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.clovers.dao.HumanResourcesDAO;
import com.clovers.dto.AnnaulRestDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.dto.WorkConditionDTO;

import jakarta.servlet.http.HttpSession;

@Service
public class HumanResourcesService {
	// 인사 서비스 레이어
	@Autowired
	private HttpSession session;

	@Autowired
	private HumanResourcesDAO dao;

	public MemberDTO selectById(String id) {
		
		MemberDTO dto = dao.selectById(id);
		
		// 사내이메일에 @이후로 없애고 불러옴
		String company_email = dto.getCompany_email();
		int index = company_email.indexOf("@");
		String email = company_email.substring(0,index);
		
		dto.setCompany_email(email);
		
		return dto;
	}

	public String reChangePw(String id) {
		return dao.reChangePw(id);
	}

	// 사내 이메일 중복 확인
	public int isDupEmail(String company_email) {
		
		return dao.isDupEmail(company_email);
	}
	
	// 사진 등록한 경우
	public int update(String id, String profile_img, String company_email, String company_phone, String phone,
			String email) {

		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		param.put("profile_img", profile_img);
		param.put("company_email", company_email);
		param.put("company_phone", company_phone);
		param.put("phone", phone);
		param.put("email", email);

		return dao.update(param);
	}

	// 사진 안바꾸거나 기본이미지인 경우
	public int updateNoImg(String id, String company_email, String company_phone, String phone, String email) {

		Map<String, String> param = new HashMap<>();
		param.put("id", id);
		param.put("company_email", company_email);
		param.put("company_phone", company_phone);
		param.put("phone", phone);
		param.put("email", email);

		return dao.updateNoImg(param);
	}

	// 사용자 지각 정보 불러오기
	public int selectLateInfo(String id) {
		return dao.selectLateInfo(id);
	}

	// 사용자 조기퇴근 정보 불러오기
	public int selectEarlyLeaveInfo(String id) {
		return dao.selectEarlyLeaveInfo(id);
	}

	// 사용자 퇴근 미체크 정보 불러오기
	public int selectNotCheckedLeaveInfo(String id) {
		return dao.selectNotCheckedLeaveInfo(id);
	}

	// 사용자 결근 정보 불러오기
	public int selectAbsenteeismInfo(String id) {
		// 평일 근무일 계산하기
		List<LocalDate> weekdays = getWeekDaysForCurrentMonth();

		// 이번달 공휴일 정보 불러오기
		List<Map<String, Date>> holidays = dao.selectHoliDays();

		// 평일 중 공휴일인날을 근무 일수에서 제거하기
		for (int i = 0; i < weekdays.size(); i++) {
			LocalDate date = weekdays.get(i);
			for (Map<String, Date> holiday : holidays) {
				LocalDate holidayDate = holiday.get("holiday_date").toLocalDate();
				if (date.isEqual(holidayDate)) {
					// 해당하는 날짜가 공휴일이면 제거
					weekdays.remove(i);
					i--;
				}
			}
		}

		// 이번달 근무일 정보 불러오기
		List<Map<String, Timestamp>> workingDays = dao.selectWorkingDaysThisMonth(id);

		// 평일 중 근무를 한 날은 제거
		for (int i = 0; i < weekdays.size(); i++) {
			LocalDate date = weekdays.get(i);
			for (Map<String, Timestamp> workingDay : workingDays) {
				LocalDate workingDate = workingDay.get("work_date").toLocalDateTime().toLocalDate();
				if (date.isEqual(workingDate)) {
					weekdays.remove(i);
					i--;
				}
			}
		}
		return weekdays.size();
	}

	// 평일 근무일 계산하기
	public List<LocalDate> getWeekDaysForCurrentMonth() {
		List<LocalDate> weekdays = new ArrayList<>();
		LocalDate today = LocalDate.now();
		LocalDate firstDayOfMonth = today.withDayOfMonth(1);
		LocalDate lastDayOfMonth = today;

		LocalDate currentDate = firstDayOfMonth;
		while (!currentDate.isAfter(lastDayOfMonth)) {
			if (currentDate.getDayOfWeek() != DayOfWeek.SATURDAY && currentDate.getDayOfWeek() != DayOfWeek.SUNDAY) {
				weekdays.add(currentDate);
			}
			currentDate = currentDate.plusDays(1);
		}
		return weekdays;
	}

	// 사용자 근무 시간 정보 불러오기
	public List<Map<String, Timestamp>> selectWorkingDaysThisMonth(String id) {
		return dao.selectWorkingDaysThisMonth(id);
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
		Instant instant = attend_time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault()).toInstant();

		// 두 시간 간의 차이 계산
		Duration duration = Duration.between(now, instant).abs(); // 두 시간의 차이 계산 (음수 방지를 위해 abs() 사용)
		long differenceInSeconds = duration.getSeconds(); // 차이를 초 단위로 변환

		if (differenceInSeconds / 60 >= 1) {
			user.replace("daily_work_rule_id", "지각");
			return dao.insertAttendingWork(user);
		} else {
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

		// 삽입한 업무 상태 확인
		return dao.selectWorkCondition(workConditionId);
	}

	// 업무 상태 리스트 불러오기
	public List<WorkConditionDTO> selectWorkConditionsList(String id) {
		return dao.selectWorkConditionsList(id);
	}

	// 휴가 사유 구분 불러오기
	public List<String> selectRestReasonType() {
		return dao.selectRestReasonType();
	}

	// 해당 년도 휴가 총 개수 불러오기
	public Map<String, Object> selectYearTotalAnnaul(String year) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", (String) session.getAttribute("loginID"));
		data.put("year", year);
		return dao.selectYearTotalAnnaul(data);
	}

	// 해당 년도 휴가 사용 개수 불러오기
	public int selectUsedAnnaul(@RequestParam("year") String year) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", (String) session.getAttribute("loginID"));
		data.put("year", year);
		return dao.selectUsedAnnaul(data);
	}

	// 해당 년도 휴가 생성 상세 정보 불러오기
	public List<AnnaulRestDTO> selectYearDetailAnnaul(String year) {
		Map<String, Object> data = new HashMap<>();
		data.put("id", (String) session.getAttribute("loginID"));
		data.put("year", year);
		return dao.selectYearDetailAnnaul(data);
	}

	// 사용자의 휴가 신청 상세 내역 확인하기
	public Map<String, Object> selectAnnaulAppDetails(int start, int count) {
		String id = (String) session.getAttribute("loginID");
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("start", start-1);
		param.put("count", count);
		//return ;
		Map<String, Object> result = new HashMap<>();
		result.put("detail", dao.selectAnnaulAppDetails(param));
		result.put("recordTotalCount", dao.selectAnnaulAppCount(id));
		return result;
	}

	// 사용자의 최근 1년치 신청 상세 내역 확인하기
	public Map<String, Object> selectAnnaulAppDetailsForYear(int start, int count) {
		String id = (String) session.getAttribute("loginID");
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("start", start-1);
		param.put("count", count);
		
		Map<String, Object> result = new HashMap<>();
		result.put("detail", dao.selectAnnaulAppDetailsForYear(param));
		result.put("recordTotalCount", dao.selectAnnaulAppForYearCount(id));
		return result;
	}

	// 사용자의 최근 1달치 신청 상세 내역 확인하기
	public Map<String, Object> selectAnnaulAppDetailsForMonth(int start, int count) {
		String id = (String) session.getAttribute("loginID");
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("start", start-1);
		param.put("count", count);
		
		Map<String, Object> result = new HashMap<>();
		result.put("detail", dao.selectAnnaulAppDetailsForMonth(param));
		result.put("recordTotalCount", dao.selectAnnaulAppForMonthCount(id));
		return result;
	}

	// 사용자의 최근 1주일치 신청 상세 내역 확인하기
	public Map<String, Object> selectAnnaulAppDetailsForWeek(int start, int count) {
		String id = (String) session.getAttribute("loginID");
		Map<String, Object> param = new HashMap<>();
		param.put("id", id);
		param.put("start", start);
		param.put("count", count);
		
		Map<String, Object> result = new HashMap<>();
		result.put("detail", dao.selectAnnaulAppDetailsForWeek(param));
		result.put("recordTotalCount", dao.selectAnnaulAppForWeekCount(id));
		return result;
	}

	// 임직원 정보 전부 불러오기
	public List<MemberDTO> employeeSelectAll() {
		return dao.employeeSelectAll();
	}

}
