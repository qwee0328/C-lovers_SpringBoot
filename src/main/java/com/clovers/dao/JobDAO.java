package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.JobDTO;

@Repository
public class JobDAO {
	
	@Autowired
	private SqlSession db;
	
	public int insert(JobDTO dto) {
		return db.insert("Job.insert",dto);
	}
	
	public String selectNewestIdExtract() {
		return db.selectOne("Job.selectNewestIdExtract");
	}
	
	public int selectNewestSecLevelExtract() {
		return db.selectOne("Job.selectNewestSecLevelExtract");
	}
	
	public List<JobDTO> selectAllACSSecLevel(){
		return db.selectList("Job.selectAllACSSecLevel");
	}
	
	public List<Map<String,Object>> selectAllAcsSecLevelWithOutJobId(){
		return db.selectList("Job.selectAllAcsSecLevelWithOutJobId");
	}
	
	public Map<String,Object> selectNewestSecLevelWithOutJobId(){
		return db.selectOne("Job.selectNewestSecLevelWithOutJobId");
	}
	
	public List<JobDTO> selectAll(){
		return db.selectList("Job.selectAll");
	}
	
	public int updateJobNameById(JobDTO dto) {
		return db.update("Job.updateJobNameById",dto);
	}
	
	public int updateSecLevelById(JobDTO dto) {
		return db.update("Job.updateSecLevelById",dto);
	}
	
	public int deleteById(int id) {
		return db.delete("Job.deleteById",id);
	}

}
