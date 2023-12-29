package com.clovers.controllers;

import java.io.File;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.clovers.commons.EncryptionUtils;
import com.clovers.constants.Constants;
import com.clovers.dto.AnnaulRestDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.dto.WorkConditionDTO;
import com.clovers.services.AddressBookService;
import com.clovers.services.HumanResourcesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/humanResources")
public class HumanResourcesController {

	// 인사 컨트롤러
	@Autowired
	private HttpSession session;

	@Autowired
	private HumanResourcesService hrservice;
	
	@Autowired
	private AddressBookService adservice;
	
	private static final Logger logger = LoggerFactory.getLogger(HumanResourcesController.class);

	@RequestMapping("")
	public String main() {
		String title = "인사";
		String currentMenu = "휴가/근무";
		session.setAttribute("title", title);
		session.setAttribute("currentMenu", currentMenu);
		logger.info("logger test");
		return "humanresources/hrMain";
	}

	// view로 mypage에 필요한 정보 가져오기
	@RequestMapping("/mypage")
	public String select(Model model) {
		
		String title = "인사";
		session.setAttribute("title", title);
		
		String id = (String) session.getAttribute("loginID");

		MemberDTO list = hrservice.selectById(id);
		
		model.addAttribute("list", list);
		
		String currentMenu = "프로필 설정";
		session.setAttribute("currentMenu", currentMenu);
		
		return "humanresources/myPage";
	}

//	비밀번호 변경안했으면 변경하게..
	@ResponseBody
	@RequestMapping("/recommendChangPw")
	public String reChangePw(String id, String pw) {

		MemberDTO list = hrservice.selectById(id);

		// 사원의 이름을 암호화 한 값
		String enPw = EncryptionUtils.getSHA512(EncryptionUtils.kR_EnKeyboardConversion(list.getName()));

		// DB에 있는 비밀번호 값
		String pwdb = hrservice.reChangePw(id);

		if (pwdb.equals(enPw)) {
			return "변경추천";
		}
		return "변경완료";
	}

//	비밀번호 변경하는 페이지로 이동
	@RequestMapping("/goChangePw")
	public String goChangePw() {
		return "member/changePW";
	}
	
	
	// 이메일 중복 확인
	@ResponseBody
	@RequestMapping("/emailDup")
	public int emailDup(String company_email) {
		// 이메일 중복 확인 : 1 중복
		company_email = company_email.concat("@clovers.com");
		int isDupEmail = hrservice.isDupEmail(company_email);
		return isDupEmail;
	}
	

//	프로필 이미지,사내전화,휴대전화,개인이메일 정보 업데이트
	@RequestMapping("/update")
	public String update(MultipartFile profile_img, String company_email, String company_phone, String phone,
			String email) throws Exception {

		String id = (String) session.getAttribute("loginID");

		company_email = company_email.concat("@clovers.com");
		
		// 주소록에 사내 이메일과 휴대폰 업데이트
		int result = adservice.updateCompanyEmailPhone(id,company_email,phone);
		// 사진 등록
		if (!(profile_img.getOriginalFilename().equals(""))) {
			String path = "C:/C-lovers";

			File uploadPath = new File(path);

			if (!uploadPath.exists()) {
				uploadPath.mkdir();
			}

			String oriName = profile_img.getOriginalFilename();
			String sysName = UUID.randomUUID() + "_" + oriName;

			profile_img.transferTo(new File(uploadPath + "/" + sysName));
			
			hrservice.update(id, sysName, company_email, company_phone, phone, email);
		} else {
			// 사진 안바꾸거나 기본이미지인 경우
			hrservice.updateNoImg(id, company_email, company_phone, phone, email);
		}
			

		

		return "redirect:/humanResources/mypage";
	}

	// 사용자 지각 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectLateInfo")
	public int selectLateInfo() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.selectLateInfo(id);
	}

	// 사용자 조기퇴근 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectEarlyLeaveInfo")
	public int selectEarlyLeaveInfo() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.selectEarlyLeaveInfo(id);
	}

	// 사용자 퇴근 미체크 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectNotCheckedLeaveInfo")
	public int selectNotCheckedLeaveInfo() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.selectNotCheckedLeaveInfo(id);
	}

	// 사용자 결근 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectAbsenteeismInfo")
	public int selectAbsenteeismInfo() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.selectAbsenteeismInfo(id);
	}

	// 사용자 근무 시간 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectWorkingDaysThisMonth")
	public List<Map<String, Timestamp>> selectWorkingDaysThisMonth() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.selectWorkingDaysThisMonth(id);
	}

	// 사용자 근무 규칙 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectEmployeeWorkRule")
	public Map<String, Object> selectEmployeeWorkRule() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.selectEmployeeWorkRule(id);
	}

	// 출근 전인지 확인
	@ResponseBody
	@RequestMapping("/selectAttendStatus")
	public Map<String, Object> selectAttendStatus() {
		String id = (String) session.getAttribute("loginID");
		Map<String, Object> result = hrservice.selectAttendStatus(id);
		if (result != null) {
			return result;
		} else {
			return new HashMap<>();
		}
	}

	// 출근 기록 남기기
	@ResponseBody
	@RequestMapping("/insertAttendingWork")
	public int insertAttendingWork() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.insertAttendingWork(id);
	}

	// 퇴근 기록 남기기
	@ResponseBody
	@RequestMapping("/updateLeavingWork")
	public int updateLeavingWork() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.updateLeavingWork(id);
	}

	// 업무 상태 기록 남기기
	@ResponseBody
	@RequestMapping("/insertWorkCondition")
	public WorkConditionDTO insertWorkCondition(@RequestParam("status") String status) {
		String id = (String) session.getAttribute("loginID");
		return hrservice.insertWorkCondition(id, status);
	}

	// 업무 상태 리스트 불러오기
	@ResponseBody
	@RequestMapping("/selectWorkConditionsList")
	public List<WorkConditionDTO> selectWorkConditionsList() {
		String id = (String) session.getAttribute("loginID");
		return hrservice.selectWorkConditionsList(id);
	}

	// 휴가 신청 페이지로 이동
	@RequestMapping("/showVacationApp")
	public String showVacationApp() {
		String currentMenu = "";
		session.setAttribute("currentMenu", currentMenu);
		return "humanresources/vacationApplication";
	}

	// 휴가 사유 구분 불러오기
	@ResponseBody
	@RequestMapping("/selectRestReasonType")
	public List<String> selectRestReasonType() {
		return hrservice.selectRestReasonType();
	}

	// 해당 년도 휴가 총 개수 불러오기
	@ResponseBody
	@RequestMapping("/selectYearTotalAnnaul")
	public Map<String, Object> selectYearTotalAnnaul(@RequestParam("year") String year) {
		return hrservice.selectYearTotalAnnaul(year);
	}

	// 해당 년도 휴가 생성 상세 정보 불러오기
	@ResponseBody
	@RequestMapping("/selectYearDetailAnnaul")
	public List<AnnaulRestDTO> selectYearDetailAnnaul(@RequestParam("year") String year) {
		return hrservice.selectYearDetailAnnaul(year);
	}

	// 해당 년도 휴가 사용 개수 불러오기
	@ResponseBody
	@RequestMapping("/selectUsedAnnaul")
	public int selectUsedAnnaul(@RequestParam("year") String year) {
		return hrservice.selectUsedAnnaul(year);
	}

	// 사용자의 휴가 신청 상세 내역 확인하기
	@ResponseBody
	@RequestMapping("/selectAnnaulAppDetails")
	public Map<String, Object> selectAnnaulAppDetails(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		Map<String, Object> param = hrservice.selectAnnaulAppDetails((currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		
		Map<String,Object> result = new HashMap<>();
		result.put("detail", param.get("detail"));
		result.put("recordTotalCount", param.get("recordTotalCount"));
		result.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		result.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		result.put("lastPageNum", currentPage);
		return result;
	}

	// 사용자의 최근 1년치 신청 상세 내역 확인하기
	@ResponseBody
	@RequestMapping("/selectAnnaulAppDetailsForYear")
	public Map<String, Object> selectAnnaulAppDetailsForYear(@RequestParam("cpage") String cpage) {
		logger.info("logger test");
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		Map<String, Object> param = hrservice.selectAnnaulAppDetailsForYear((currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		
		Map<String,Object> result = new HashMap<>();
		result.put("detail", param.get("detail"));
		result.put("recordTotalCount", param.get("recordTotalCount"));
		result.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		result.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		result.put("lastPageNum", currentPage);
		return param;
	}

	// 사용자의 최근 1달치 신청 상세 내역 확인하기
	@ResponseBody
	@RequestMapping("/selectAnnaulAppDetailsForMonth")
	public Map<String, Object> selectAnnaulAppDetailsForMonth(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		Map<String, Object> param = hrservice.selectAnnaulAppDetailsForMonth((currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		
		Map<String,Object> result = new HashMap<>();
		result.put("detail", param.get("detail"));
		result.put("recordTotalCount", param.get("recordTotalCount"));
		result.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		result.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		result.put("lastPageNum", currentPage);
		return param;
	}

	// 사용자의 최근 1주일치 신청 상세 내역 확인하기
	@ResponseBody
	@RequestMapping("/selectAnnaulAppDetailsForWeek")
	public Map<String, Object> selectAnnaulAppDetailsForWeek(@RequestParam("cpage") String cpage) {
		int currentPage = (cpage.isEmpty()) ? 1 : Integer.parseInt(cpage);
		Map<String, Object> param =  hrservice.selectAnnaulAppDetailsForMonth((currentPage * Constants.RECORD_COUNT_PER_PAGE - (Constants.RECORD_COUNT_PER_PAGE-1)), (currentPage * Constants.RECORD_COUNT_PER_PAGE));
		param.put("recordCountPerPage", Constants.RECORD_COUNT_PER_PAGE);
		param.put("naviCountPerPage", Constants.NAVI_COUNT_PER_PAGE);
		param.put("lastPageNum", currentPage);
		return param;
	}

	// 임직원 관리 페이지 이동
	@RequestMapping("/employeeInfo")
	public String employeeInfo() {
		String currentMenu = "임직원 정보";
		session.setAttribute("currentMenu", currentMenu);
		return "humanresources/employeeInfo";
	}

//	// view로 헤더에 필요한 정보 가져오기
	@ResponseBody
	@RequestMapping("/headerProfile")
	public MemberDTO selectProfile() {
		String id = (String) session.getAttribute("loginID");
		MemberDTO profile = hrservice.selectById(id);
		profile.setBirth(new Timestamp(System.currentTimeMillis()));
		return profile;
	}

	// 근무 현황 이동
	@RequestMapping("/workStatus")
	public String workStatus() {
		String currentMenu = "휴가 현황";
		session.setAttribute("currentMenu", currentMenu);
		return "humanresources/workStatus";
	}
}
