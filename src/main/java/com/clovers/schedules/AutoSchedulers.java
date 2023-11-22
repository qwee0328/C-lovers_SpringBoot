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
import com.clovers.services.AddressBookService;
import com.clovers.services.MailService;
import com.clovers.services.MemberService;
import com.clovers.services.ScheduleService;

@Component
public class AutoSchedulers {
	@Autowired
	private MemberService mservice;

	@Autowired
	private ScheduleService sservice;

	@Autowired
	private AddressBookService abservice;

	@Autowired
	private MailService mailService;

	@Scheduled(cron = "0 30 8 1 1 ?")
	public void AnnaulScheduler() {
		List<MemberDTO> list = mservice.selectUserList();

		// 현재 시간 가져오기
		LocalDateTime currentDateTime = LocalDateTime.now();
		for (int i = 0; i < list.size(); i++) {
			AnnaulRestDTO user = new AnnaulRestDTO();
			user.setEmp_id(list.get(i).getId());

			Timestamp hireDate = list.get(i).getHire_date();
			// Timestamp값을 LocalDateTime으로 변환
			LocalDateTime hireDateDateTime = hireDate.toLocalDateTime();

			// 두 날짜 사이의 Duration 계산
			Duration duration = Duration.between(hireDateDateTime, currentDateTime);

			// 차이를 년 단위로 변환
			long years = duration.toDays() / 365;

			// 년차별 이번 년도 연차
			int thisYearAnnual = 0;
			if (years < 2) {
				thisYearAnnual = 15;
			} else if (years < 4) {
				thisYearAnnual = 16;
			} else if (years < 6) {
				thisYearAnnual = 17;
			} else if (years < 8) {
				thisYearAnnual = 18;
			} else if (years < 10) {
				thisYearAnnual = 19;
			} else if (years < 12) {
				thisYearAnnual = 20;
			} else if (years < 14) {
				thisYearAnnual = 21;
			} else if (years < 16) {
				thisYearAnnual = 22;
			} else if (years < 18) {
				thisYearAnnual = 23;
			} else if (years < 20) {
				thisYearAnnual = 24;
			} else {
				thisYearAnnual = 25;
			}

			user.setRest_type_id("연차");
			user.setRest_cnt(thisYearAnnual);
			mservice.insertAutomaticAnnaulRest(user);
		}
	}

	// 매일 12시에 휴지통에서 30일 경과한 주소록 및 일정 삭제
	// 메일 추가
	@Scheduled(cron = "0 0 0 * * *")
	public void TrashScheduler() throws Exception {
		sservice.autoDeleteInTrash();
		abservice.autoDeleteInTrash();
		mailService.autoDeleteInTrash();
	}
}
