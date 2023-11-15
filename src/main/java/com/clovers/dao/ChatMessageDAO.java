package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.ChatMessageDTO;

@Repository
public class ChatMessageDAO {
	// 채팅 메시지 관련 DAO
	
	@Autowired
	private SqlSession db;
	
	
	// 채팅방에 채팅 남기기
	public int recordChat(ChatMessageDTO cdto) {
		return db.insert("ChatMessage.recordChat", cdto);
	}
	
	// 채팅방의 아이디를 기준으로 채팅 메시지를 가져옴.
	public List<ChatMessageDTO> selectMessagesByChatRoomId(String chat_room_id){
		return db.selectList("ChatMessage.selectMessagesByChatRoomId", chat_room_id);
	};
	
	// 채팅방의 아이디를 기준으로 채팅 메시지를 가져옴 #2 (이름까지 가져오는 것)
	public List<Map<String,Object>> selectMessageListByChatRoomId(String chat_room_id){
		return db.selectList("ChatMessage.selectMessageListByChatRoomId", chat_room_id);
	};
	
	// 채팅방에 처음 초대되거나 마지막으로 읽은 메시지를 업데이트 하는데에 사용되는 함수. 채팅방 아이디를 기준으로 가장 최신의 메시지의 아이디 값을 가져온다.
	public int selectLatestChatMsgIdByChatRoomId(String chat_room_id) {
		return db.selectOne("ChatMessage.selectLatestChatMsgIdByChatRoomIdForUpdate", chat_room_id);
	}
	
	// 사용자의 아이디를 기준으로 사용자가 현재 채팅방에 들어와 있는지 확인
	//	public 
	
	// 채팅방의 메시지를 삭제하기(업데이트하여서 가리기)
	public int deleteExecModifyChatMessageById(int id) {
		return db.update("ChatMessage.deleteExecModifyChatMessageById",id);
	}
	
	

}
