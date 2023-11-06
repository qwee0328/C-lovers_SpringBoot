package com.clovers.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.ChatGroupDAO;
import com.clovers.dto.ChatGroupDTO;
import com.clovers.dto.ChatMessageDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.servlet.http.HttpSession;

@Service
public class ChatGroupService {

	@Autowired
	private ChatGroupDAO dao;
	
	@Autowired
	private HttpSession session;
	
	
	public List<Map<String, Object>> selectByEmpId() {
		return dao.selectByEmpId((String)session.getAttribute("loginID"));
	}
	
	public Map<String,Object> selectByChatId(){
		Map<String,Object> group = new HashMap<>();
		group.put("chat_id", 1);
		group.put("name", "채팅방1");
		group.put("emp_cnt", 3);
		
		Map<String,Object> list = new HashMap<>();
		list.put("group", group);
		
		List<ChatMessageDTO> msg = new ArrayList<>();
		for(int i=1; i<5; i++) {
			msg.add(new ChatMessageDTO(i,1,"test","내용이에요ㅋㅋㅋㅋㅋㅋㅋㅋ"+i,new Timestamp(System.currentTimeMillis()),0));
		}
		for(int i=5; i<10; i++) {
			msg.add(new ChatMessageDTO(i,1,"another","내용이에요ㅋㅋㅋㅋㅋㅋㅋㅋ"+i,new Timestamp(System.currentTimeMillis()),0));
		}
		msg.add(new ChatMessageDTO(9,1,"test","file",new Timestamp(System.currentTimeMillis()),0));
		msg.add(new ChatMessageDTO(10,1,"another","file",new Timestamp(System.currentTimeMillis()),0));
		list.put("chat", msg);
		
		list.put("loginID",(String)session.getAttribute("loginID"));
		
		return list;
	}
}
