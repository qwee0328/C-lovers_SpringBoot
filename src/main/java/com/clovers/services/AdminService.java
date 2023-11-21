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

	public int insert(AdminDTO dto) {
		return adao.insert(dto);
	}

	public List<Map<String, Object>> selectAll() {
		return adao.selectAll();
	}
	
	public Map<String,Object> selectByAdminId(int id){
		return adao.selectByAdminId(id);
	}
	
	public int newestId() {
		return adao.newestId();
	}

	public List<Map<String, Object>> selectAllCount() {
		return adao.selectAllCount();
	}

	public List<String> selectAuthorityCategories() {
		return adao.selectAuthorityCategories();
	}

//	public int updateAdminInfo(AdminDTO.AuthorityCategories authority_category_id, int id) {
//		Map<String,Object> param = new HashMap<>();
//		param.put("authority_category_id", authority_category_id);
//		param.put("id",id);
//		return adao.updateAdminInfo(param);
//	}

	public void updateAdminInfo(List<Map<String, Object>> params) {
		for (Map<String, Object> param : params) {
			Map<String, Object> parameter = new HashMap<>();
			parameter.put("authority_category_id", param.get("authority_category_id"));
			parameter.put("id", param.get("id"));
			adao.updateAdminInfo(parameter);
		}
	}

//	public int deleteById(int id) {
//		return adao.deleteById(id);
//	}

	public void deleteById(List<Integer> params) {
		for (int param : params) {
			adao.deleteById(param);
		}
	}
}
