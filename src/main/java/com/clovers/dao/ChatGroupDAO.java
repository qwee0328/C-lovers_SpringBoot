package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatGroupDTO;

@Repository
public class ChatGroupDAO {
	// 채팅그룹 관리 DAO

	@Autowired
	private SqlSession db;

	// 채팅 그룹에 사람을 초대
	public int inviteChatGroup(Map<String, Object> param) {
		return db.insert("ChatGroup.inviteChatGroup", param);
	}

	// 사용자에게 채팅방과 그에 관련된 그룹을 보여줌.
	public List<Map<String, Object>> selectByEmpId(String emp_id) {
		return db.selectList("ChatGroup.selectByEmpId", emp_id);
	}
	
	public List<String> selectEmpIDByChatRoomId(String chat_room_id){
		return db.selectList("ChatGroup.selectEmpIDByChatRoomId",chat_room_id);
	}

	// 채팅방에 사용자만을 위한 정보를 보여줌.
	public Map<String, Object> selectByEmpIdNChatRoomID(Map<String, String> param) {
		return db.selectOne("ChatGroup.selectByEmpIdNChatRoomID", param);
	}

	// 모든 사용자 정보 가져오기
	public List<Map<String, Object>> getAllOfficerInfo() {
		return db.selectList("ChatGroup.getAllOfficerInfo");
	}
	

	// 사용자 아이디로 사용자 정보 가져오기
	public Map<String, Object> getOfficerInfoByEmployeeId(String employee_id) {
		return db.selectOne("ChatGroup.getOfficerInfoByEmployeeId", employee_id);
	}

	// 이미 이전에 개설한 1:1채팅방이 존재 하는경우 그 채팅방 아이디를 반환
	public String getExist1v1ChatRoom(Map<String, String> param) {
		return db.selectOne("ChatGroup.getExist1v1ChatRoom", param);
	}

	// 사용자가 속한 채팅그룹 리스트를 반환.
	public List<ChatGroupDTO> selectChatGroupListByEmpId(String emp_id) {
		return db.selectList("ChatGroup.selectChatGroupListByEmpId", emp_id);
	}

	// 채팅방에 입장할 시 마지막으로 본 메시지의 아이디 번호를 가져와 마지막에 본 채팅메시지의 아이디로 업데이트
	// 혹은 채팅이 올라오면서 지속적으로 업데이트.
	public int updateMsgEndId(int msg_end_id) {
		return db.update("ChatGroup.updateMsgEndId", msg_end_id);
	}

	// 채팅 그룹 이름 변경(각자 자신이 속한 채팅 그룹의 이름을 변경)
	public int updateChatGroupName(Map<String, Object> param) {
		return db.update("ChatGroup.updateChatGroupName", param);
	}

	// 채팅 그룹에서 나가기
	public int deleteByEmpIdNChatId(Map<String, Object> param) {
		return db.delete("ChatGroup.deleteByEmpIdNChatId", param);
	}

	// 채팅 그룹 해체(1명만 남아있는 채팅그룹은 자동으로 해제되도록 할 예정)
	public int closeChatGroup(int chat_id) {
		return db.delete("ChatGroup.closeChatGroup", chat_id);
	}

}
