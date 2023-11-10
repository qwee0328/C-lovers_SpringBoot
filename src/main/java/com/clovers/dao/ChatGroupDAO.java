package com.clovers.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatGroupDTO;

@Repository
public class ChatGroupDAO {
	//채팅그룹 관리 DAO
	
	@Autowired
	private SqlSession db;
	
	// 채팅 그룹에 사람을 초대
	public int insert(ChatGroupDTO dto) {
		return db.insert("ChatGroup.insert", dto);
	}
	
	// 채팅방에 입장할 시 마지막으로 본 메시지의 아이디 번호를 가져와 마지막에 본 채팅메시지의 아이디로 업데이트
	// 혹은 채팅이 올라오면서 지속적으로 업데이트.
	public int updateMsgEndId(int msg_end_id) {
		return db.update("ChatGroup.updateMsgEndId",msg_end_id);
	}
	
	
	// 채팅 그룹 이름 변경(각자 자신이 속한 채팅 그룹의 이름을 변경)
	public int updateChatGroupName(Map<String,Object> param) {		
		return db.update("ChatGroup.updateChatGroupName", param);
	}
	
	// 채팅 그룹에서 나가기
	public int deleteByEmpIdNChatId(Map<String,Integer> param ) {
		return db.delete("ChatGroup.deleteByEmpIdNChatId", param);
	}
	
	
	// 채팅 그룹 해체
	public int closeChatGroup(int chat_id) {
		return db.delete("ChatGroup.closeChatGroup", chat_id);
	}
	
	// 사용자에게 채팅방과 그에 관련된 그룹을 보여줌.
	public List<Map<String, Object>> selectByEmpId(String emp_id) {
		return db.selectList("ChatGroup.selectByEmpId",emp_id);
	}
	
	


}
