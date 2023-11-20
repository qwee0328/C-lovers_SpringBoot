package com.clovers.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clovers.dto.AdminDTO;

@Repository
public class AdminDAO {
	
	@Autowired
	private SqlSession db;
	
	public int insert(AdminDTO adto) {
		return db.insert("Admin.insert", adto);
	}
	
	public List<Map<String,Object>> selectAll(){
		return db.selectList("Admin.selectAll");
	}
	
	public List<Map<String,Object>> selectAllCount(){
		return db.selectList("Admin.selectAllCount");
	}
	
	public List<String> selectAuthorityCategories(){
		return db.selectList("Admin.selectAuthorityCategories");
				
	}
	
	public int updateAdminInfo(Map<String,Object> param) {
		return db.update("Admin.updateAdminInfo",param);
	}
	
	public int deleteById(int id) {
		return db.delete("Admin.deleteById",id);
	}
	
	
	
	

}
