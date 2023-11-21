package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import com.clovers.dto.AccountingDTO;

@Repository
public class AccountingDAO {
	// 회계지원 DAO
	
	@Autowired
	private SqlSession db;
	
	// 계좌 리스트 전부 불러오기
	public List<AccountingDTO> selectAccountAll(){
		return db.selectList("Accounting.selectAll");
	}
	//이름, 사번으로 검색
	public List<AccountingDTO> searchBy(String keyword){
		String keyword2 = "%"+keyword+"%";
		return db.selectList("Accounting.searchBy",keyword2);
	}
	// 계좌 추가 전에 유효한 사번인지 아닌지 확인
	public int isEmployee(String emp_id) {
		return db.selectOne("Accounting.isEmployee",emp_id);
	}
	
	// 계좌 추가 전에 이미 계좌가 등록된 사번인지 아닌지 확인
	public int isEmpId(String emp_id) {
		return db.selectOne("Accounting.isEmpId",emp_id);
	}
	// 계좌 추가
	public int insert(AccountingDTO dto) {
		return db.insert("Accounting.insert",dto);
	}
	// id로 삭제
	public int deleteAccount(String id){
		return db.delete("Accounting.deleteById",id);
	}
	// 계좌 수정
	public int update(AccountingDTO dto){
		return db.update("Accounting.update",dto);
	}
	
	//////법인 카드 ////
	// 카드 리스트 전부 부르기
	public List<AccountingDTO> selectCardAll(){
		return db.selectList("Accounting.selectCardAll");
	}
	// 검색
	public List<AccountingDTO> searchCard(String keyword){
		String keyword2 = "%"+keyword+"%";
		return db.selectList("Accounting.searchCard",keyword2);
	}
	// 카드번호 중복 확인
	public int isAlreadyCardNum(String cardNum) {
		return db.selectOne("Accounting.isAlreadyCardNum",cardNum);
	}
	// 계좌 추가 전에 이미 카드가 등록된 사번인지 아닌지 확인
	public int isEmpCardId(String emp_id) {
		return db.selectOne("Accounting.isEmpCardId",emp_id);
	}
	// 카드 추가
	public int insertCard( AccountingDTO dto){
		return db.insert("Accounting.insertCard",dto);
	}
	// 카드 삭제
	public int deleteCard(String id){
		return db.delete("Accounting.deleteCard",id);
	}
	// 카드 수정
	public int updateCard(AccountingDTO dto){
		return db.update("Accounting.updateCard",dto);
	}
}
