package com.clovers.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.commons.EncryptionUtils;
import com.clovers.dao.AddressBookDAO;
import com.clovers.dao.MemberDAO;
import com.clovers.dao.OfficeDAO;
import com.clovers.dto.AdminDTO;
import com.clovers.dto.DeptTaskDTO;
import com.clovers.dto.JobDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.dto.OfficeDTO;

@Service
public class OfficeService {
	// 오피스 관리 서비스 레이어
	@Autowired
	private OfficeDAO dao;
	
	@Autowired
	private AddressBookDAO adao;
	
	@Autowired
	private MemberDAO mdao;

	// 부서 명 불러오기
	public List<DeptTaskDTO> selectDeptTaskAll() {
		return dao.selectDeptTaskAll();
	}

	// 직급 명 불러오기
	public List<JobDTO> selectPositionAll() {
		return dao.selectPositionAll();
	}

	// 실제 db에 저장된 실 사용자 수 불러오기
	public int selectRealEmpCount() {
		return dao.selectRealEmpCount();
	}

	// 사번 입력할때 사용할 사용자 수 불러오기 -> 삭제된 사용자까지 합쳐서 숫자 셈
	public int selectEmpCount() {
		return dao.selectEmpCount();
	}

	// 오피스 정보 불러오기
	public OfficeDTO selectOfficeInfo() {
		return dao.selectOfficeInfo();
	}

	// 사용자 리스트 불러오기
	public List<Map<String, String>> selectUserList() {
		return dao.selectUserList();
	}
	
	// 전체 사용자 수 불러오기
	// 사용자 수 불러오기
	public int selectUserCount() {
		return dao.selectUserCount();
	}

	// 사내 전화번호 사용중인번호인지 체크
	public int usingCompanyPhoneCheck(String companyPhone) {
		return dao.usingCompanyPhoneCheck(companyPhone);
	}

	// 사용자 등록하기
	@Transactional
	public int insertUser(MemberDTO dto) throws Exception {
		// 입사 년도 구하기
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		String year = sdf.format(dto.getHire_date());

		// 입사 순서 구하기
		int emp_num = dao.selectEmpCount() + 1;
		String joiningNumber = String.format("%03d", emp_num);

		// 사번 생성 및 아이디 설정
		// 사번은 입사년도+부서번호+입사 순서로 구성
		String id = year + dto.getDept_task_id() + joiningNumber;
		dto.setId(id);

		String engKeyboardConversionName = EncryptionUtils.kR_EnKeyboardConversion(dto.getName());

		// 초기 비밀번호는 이름으로 저장
		dto.setPw(EncryptionUtils.getSHA512(engKeyboardConversionName));

		// 초기 사내 이메일은 id랑 똑같이 저장
		dto.setCompany_email(dto.getId()+"@clovers.com");
		
		// 생일 값을 입력하지 않으면 기본값입력
		if(dto.getBirth()==null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parseDate = dateFormat.parse("1900-01-01");
			Timestamp timestamp = new Timestamp(parseDate.getTime());
			dto.setBirth(timestamp);
		}
		
		// 직급이 대표이사, 사장, 상무, 이사인 경우 총괄 관리자 등록
		String jobName = dao.selectJobName(dto.getJob_id());
		String taskName = dao.selectDeptTaskName(dto.getDept_task_id());
		dto.setJob_name(jobName);
		dto.setTask_name(taskName);
		if(jobName.equals("대표이사") || jobName.equals("사장")||jobName.equals("상무")||jobName.equals("이사")) {
			dao.insertTotalAdmin(id);
		}else {
			if(taskName.contains("인사")) { // 인사 관련 부서인 경우 관리자 등록
				dao.insertHRAdmin(id);
			}
			if(taskName.contains("회계")||taskName.contains("총무")) { // 회계 관련 부서인 경우 관리자 등록
				dao.insertACAdmin(id);
			}
		}
		
		// 처음 입사 시 15일 연차 지급
		dao.insertFirstAnnaul(dto.getId());
		// 공유 주소록에 사용자 정보를 추가하기 위한 내용
		dao.insertAddressBook(dto.getId());
		// 사용자 등록
		return dao.insertUser(dto);
	}

	// 사용자 삭제하기
	public int deleteUser(List<String> userID) {
		return dao.deleteUser(userID);
	}

	// 오피스 이름 수정
	@Transactional
	public void updateOfficeName(OfficeDTO dto) {
		dao.updateOfficeName(dto);
		adao.updateOfficeName(dto.getOffice_name());
	}
	
	// 오피스 이메일 수정
	public void updateOfficeEmail(OfficeDTO dto) {
		dao.updateOfficeEmail(dto);
	}

	// 사용자 직위 수정하기
	@Transactional
	public void updateUserJob(List<MemberDTO> dtoList) {
		for (MemberDTO dto : dtoList) {
			dao.updateUserJob(dto);
			List<AdminDTO> authority = mdao.getAuthorityInfo(dto.getId());
			String jobName = dao.selectJobName(dto.getJob_id());
			// 내가 바꾸고 싶은 직급이 고위직인데
			if(jobName.equals("대표이사") || jobName.equals("사장")||jobName.equals("상무")||jobName.equals("이사")) {
				boolean flag = false;
				for(AdminDTO info : authority) {
					if(info.getAuthority_category_id()==AdminDTO.AuthorityCategories.총괄) {
						flag=true; // 기존에 고위직이였다면
					}
				}
				// 고위직이 아니였을 경우에만 총괄 등록
				if(!flag) {
					dao.insertTotalAdmin(dto.getId());
				}

			}else {// 내가 바꾸고 싶은 직급이 고위직이 아닌데
				boolean flag=false;
				for(AdminDTO info : authority) {
					if(info.getAuthority_category_id()==AdminDTO.AuthorityCategories.총괄) {
						flag=true; // 기존에 고위직이였다면
					}
				}
				//기존 총괄 권한 삭제
				if(flag) {
					dao.deleteTotalAdmin(dto.getId());
				}
			}
		}
	}

	// 사용자 소속 조직 수정하기
	public void updateUserDeptTask(List<MemberDTO> dtoList) {
		for (MemberDTO dto : dtoList) {
			dao.updateUserDeptTask(dto);
			List<AdminDTO> authority = mdao.getAuthorityInfo(dto.getId());
			String taskName = dao.selectDeptTaskName(dto.getDept_task_id());
			
			// 내가 바꾸고 싶은 팀 이름에 인사가 포함된다면
			if(taskName.contains("인사")) {
				boolean flag = false;
				// 내가 바꾸고 싶은 task가 인사팀인데
				for(AdminDTO info:authority) {
					// 기존에 인사팀이라면
					if(info.getAuthority_category_id()==AdminDTO.AuthorityCategories.인사) {
						flag=true;
					}
				}
				// 결국 인사팀이 아니였다면
				if(!flag) {
					dao.insertHRAdmin(dto.getId());
				}
			}else {// 만약에 내가 바꾸고 싶은 팀이 인사팀이 아닌데
				boolean flag = false;
				for(AdminDTO info:authority) {
					if(info.getAuthority_category_id()==AdminDTO.AuthorityCategories.인사) {
						flag=true; // 기존에 인사팀이였다면
					}
				}
				// 기존에 인사팀이면 인사권한 삭제
				if(flag) {
					dao.deleteHRAdmin(dto.getId());
				}
			}
			// 내가 바꾸고 싶은 팀 이름에 회계가 포함된다면
			if(taskName.contains("회계")||taskName.contains("총무")) {
				boolean flag = false;
				for(AdminDTO info:authority) {
					if(info.getAuthority_category_id()==AdminDTO.AuthorityCategories.회계) {
						flag=true; // 기존에 회계 관련 팀이라면
					}
				}
				// 회계팀이 아니였다면
				if(!flag) {
					dao.insertACAdmin(dto.getId());
				}
			}else {// 만약 내가 바꾸고 싶은 팀이 회계 관련 팀이 아닌데
				boolean flag = false;
				for(AdminDTO info:authority) {
					if(info.getAuthority_category_id()==AdminDTO.AuthorityCategories.회계) {
						flag=true; // 기존에 회계팀이였다면
					}
				}
				// 기존에 회계팀 권한 삭제
				if(flag) {
					dao.deleteACAdmin(dto.getId());
				}
			}
		}
	}

	// 사용자 이름, id 검색하기
	public List<Map<String, String>> searchUser(String keyword) {
		return dao.searchUser("%"+keyword+"%");
	}

	// 부서별 부서명, 인원 수 불러오기
	public List<Map<String, Object>> selectDeptInfo() {
		return dao.selectDeptInfo();
	}

	// 부서별 팀별 인원 수 불러오기
	public List<Map<String, Object>> selectTaskInfo() {
		return dao.selectTaskInfo();
	}

	// 부서별 인원 정보 불러오기
	public List<Map<String, Object>> selectDepartmentEmpInfo(String dept_id) {
		return dao.selectDepartmentEmpInfo(dept_id);
	}

	// 모든 부서별 정보 불러오기 - 이름, 부서명, id
	public List<Map<String, Object>> selectAllEmpInfo() {
		return dao.selectAllEmpInfo();
	}
	
	// 팀별 인원 정보 불러오기 - 이름, 부서명, id
	public List<Map<String, Object>> selectDetpTaskEmpInfo(String task_id){
		return dao.selectDetpTaskEmpInfo(task_id);
	}
	
	// 팀별(생산1팀,2팀..)인원수, 부서명
	public List<Map<String, Object>> selectAllTaskNameEmpo(){
		return dao.selectAllTaskNameEmpo();
	}
	
	// 임직원 정보에서 부서 클릭하면 정보
	public List<Map<String, Object>> selectDeptEmpo(){
		return dao.selectDeptEmpo();
	}
	
	// 부서 클릭하면 정보 
	public List<Map<String, Object>> selectByDeptName(String dept_name){
		return dao.selectByDeptName(dept_name);
	}
	
	// 팀 클릭하면 정보
	public List<Map<String, Object>> selectByTaskName(String task_name){
		return dao.selectByTaskName(task_name);
	}
	
	// 임직원 검색
	public List<Map<String, Object>> searchByName(String name){
		return dao.searchByName(name);
	}
	
	// 회사이름 불러오기
	public String selectOfficeName() {
		return dao.selectOfficeName();
	}
}
