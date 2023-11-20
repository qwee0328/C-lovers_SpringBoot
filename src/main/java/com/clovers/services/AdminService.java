package com.clovers.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clovers.dao.AdminDAO;
import com.clovers.dto.AdminDTO;

@Service
public class AdminService {

	@Autowired
	private AdminDAO adao;
	
	
	public int insert(AdminDTO adto) {
		return adao.insert(adto);
	}
	
	public List<Map<String,Object>> selectAll(){
		return adao.selectAll();
	}
	
	public List<Map<String,Object>> selectAllCount(){
		return adao.selectAllCount();
	}
	
	public List<String> selectAuthorityCategories(){
		return adao.selectAuthorityCategories();
	}
	
	public int updateAdminInfo(AdminDTO.AuthorityCategories authority_category_id, int id) {
		Map<String,Object> param = new HashMap<>();
		param.put("authority_category_id", authority_category_id);
		param.put("id",id);
		return adao.updateAdminInfo(param);
	}
	
	public int deleteById(int id) {
		return adao.deleteById(id);
	}
}
