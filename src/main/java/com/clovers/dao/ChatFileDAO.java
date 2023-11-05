package com.clovers.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatFileDTO;

@Repository
public class ChatFileDAO {
	
	@Autowired
	private SqlSession db;
	
	
	// 채팅 그룹에서 파일 업로드
	public int insert(ChatFileDTO cdto) {
		return db.insert("ChatFile.insert", cdto);
	}
	
	// 파일 삭제 => 채팅 그룹이 삭제된다면 파일이 전부 삭제되어야 함 -> 순회를 돌면서 삭제를 하자.
	public int deleteById(int id) {
		return db.delete("ChatFile.delete", id);
	}


}
