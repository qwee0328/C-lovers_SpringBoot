package com.clovers.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.JobDAO;
import com.clovers.dto.JobDTO;

@Service
public class JobService {
	
	@Autowired
	private JobDAO jdao;
	
	public int insert(JobDTO dto) {
		return jdao.insert(dto);
	}
	
	public String selectNewestIdExtract() {
		return jdao.selectNewestIdExtract();
	}
	
	public int selectNewestSecLevelExtract() {
		return jdao.selectNewestSecLevelExtract();
	}
	
	public List<JobDTO> selectAllACSSecLevel(){
		return jdao.selectAllACSSecLevel();
	}
	
	public List<Map<String,Object>> selectAllAcsSecLevelWithOutJobId(){
		return jdao.selectAllAcsSecLevelWithOutJobId();
	}
	
	public List<JobDTO> selectAll(){
		return jdao.selectAll();
	}
	
	public int updateJobNameById(JobDTO dto) {
		return jdao.updateJobNameById(dto);
	}
	
	public int updateSecLevelById(JobDTO dto) {
		return jdao.updateSecLevelById(dto);
	}
	
	public int deleteById(int id) {
		return jdao.deleteById(id);
	}

}
