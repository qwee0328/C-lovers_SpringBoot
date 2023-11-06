package com.clovers.dto;

public class EmailFileDTO {
	private int id;
	private int email_id;
	private String ori_name;
	private String sys_name;
	
	public EmailFileDTO() {}
	
	public EmailFileDTO(int id, int email_id, String ori_name, String sys_name) {
		super();
		this.id = id;
		this.email_id = email_id;
		this.ori_name = ori_name;
		this.sys_name = sys_name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEmail_id() {
		return email_id;
	}

	public void setEmail_id(int email_id) {
		this.email_id = email_id;
	}

	public String getOri_name() {
		return ori_name;
	}

	public void setOri_name(String ori_name) {
		this.ori_name = ori_name;
	}

	public String getSys_name() {
		return sys_name;
	}

	public void setSys_name(String sys_name) {
		this.sys_name = sys_name;
	}
}
