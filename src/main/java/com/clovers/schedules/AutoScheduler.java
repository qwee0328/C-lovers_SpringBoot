package com.clovers.schedules;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.clovers.dto.AnnaulRestDTO;
import com.clovers.dto.MemberDTO;
import com.clovers.services.MemberService;

@Component
public class AutoScheduler {
	@Autowired
	private MemberService mservice;
	
	@Scheduled(cron="0 30 8 1 1 ?")
	//@Scheduled(cron="0/30 0/1 * * * ?")
	public void AnnaulScheduler() {
		List<MemberDTO> list = mservice.selectUserList();

		// 현재 시간 가져오기
        LocalDateTime currentDateTime = LocalDateTime.now();
		for(int i=0;i<list.size();i++) {
			// 회원들의 연차 기록을 불러오기 위함
			// AnnaulRestDTO user = mservice.selectAnnaulRestById(list.get(i).getId());
			 AnnaulRestDTO user = new AnnaulRestDTO();
			 user.setEmp_id(list.get(i).getId());
			 
			Timestamp hireDate = list.get(i).getHire_date();
			// Timestamp값을 LocalDateTime으로 변환
			LocalDateTime hireDateDateTime = hireDate.toLocalDateTime();
			
			// 두 날짜 사이의 Duration 계산
			Duration duration = Duration.between(hireDateDateTime, currentDateTime);
			
			// 차이를 년 단위로 변환
			long years = duration.toDays()/365;
			
			// 년차별 이번 년도 연차
			int thisYearAnnual = 0;
			if(years<2) {
				thisYearAnnual = 15;
			}else if(years<4) {
				thisYearAnnual = 16;
			}else if(years<6) {
				thisYearAnnual = 17;
			}else if(years<8) {
				thisYearAnnual =18;
			}else if(years<10) {
				thisYearAnnual =19;
			}else if(years<12) {
				thisYearAnnual =20;
			}else if(years<14) {
				thisYearAnnual =21;
			}else if(years<16) {
				thisYearAnnual =22;
			}else if(years<18) {
				thisYearAnnual =23;
			}else if(years<20) {
				thisYearAnnual =24;
			}else {
				thisYearAnnual =25;
			}
			
			// 기록이 없다면 새로운 기록 삽입
//			if(user.getEmp_id()==null) {
//				user.setEmp_id(list.get(i).getId());
				user.setRest_type_id("연차");
				user.setRest_cnt(thisYearAnnual);
				mservice.insertAutomaticAnnaulRest(user);
//			}else { // 기록이 있다면 기존 연차기록에서 업데이트
//				// 연차 합산
				//int existingAnnual = user.getRest_cnt();
				//user.setRest_cnt(existingAnnual+thisYearAnnual);
				
				//System.out.println("연차 업데이트 해야함");
//				mservice.insertAutomaticAnnaulRest(user);
			//}
		}
	}
}
