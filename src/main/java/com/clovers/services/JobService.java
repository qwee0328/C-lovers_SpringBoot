package com.clovers.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clovers.dao.JobDAO;
import com.clovers.dto.JobDTO;

@Service
public class JobService {
	
	@Autowired
	private JobDAO jdao;
	
	public int insert(JobDTO dto) {
		return jdao.insert(dto);
	}
	
	@Transactional
	public int insertNewJob(String job_name) {
		String newestIdExtract = this.selectNewestIdExtract();
		int newestIdNum = Integer.parseInt(newestIdExtract.substring(1)) + 1;
		String newestIdPrefix = newestIdExtract.substring(0, 1);
		
		String newId = newestIdPrefix + newestIdNum;
		int newSecLevel = this.selectNewestSecLevelExtract()+1;
		
		return this.insert(new JobDTO(newId,job_name,newSecLevel));
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
	
	public Map<String,Object> selectNewestSecLevelWithOutJobId(){
		return jdao.selectNewestSecLevelWithOutJobId();
	}
	
	public List<JobDTO> selectAll(){
		return jdao.selectAll();
	}
	
	public int updateById(JobDTO dto) {
		return jdao.updateById(dto);
	}
	
	
	public int deleteById(String id) {
		return jdao.deleteById(id);
	}

}
