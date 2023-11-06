package com.clovers.dto;

import java.sql.Timestamp;

public class EmailDTO {
	private int id;
	private String send_id;
	private String receive_id;
	private String title;
	private String content;
	private boolean temporary;
	private boolean reservation;
	private Timestamp reservation_date;
	private boolean trash;
	
	public EmailDTO() {}
	
	public EmailDTO(int id, String send_id, String receive_id, String title, String content, boolean temporary,
			boolean reservation, Timestamp reservation_date, boolean trash) {
		super();
		this.id = id;
		this.send_id = send_id;
		this.receive_id = receive_id;
		this.title = title;
		this.content = content;
		this.temporary = temporary;
		this.reservation = reservation;
		this.reservation_date = reservation_date;
		this.trash = trash;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getSend_id() {
		return send_id;
	}
	
	public void setSend_id(String send_id) {
		this.send_id = send_id;
	}
	
	public String getReceive_id() {
		return receive_id;
	}
	
	public void setReceive_id(String receive_id) {
		this.receive_id = receive_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public boolean isTemporary() {
		return temporary;
	}
	
	public void setTemporary(boolean temporary) {
		this.temporary = temporary;
	}
	
	public boolean isReservation() {
		return reservation;
	}
	
	public void setReservation(boolean reservation) {
		this.reservation = reservation;
	}
	
	public Timestamp getReservation_date() {
		return reservation_date;
	}
	
	public void setReservation_date(Timestamp reservation_date) {
		this.reservation_date = reservation_date;
	}
	
	public boolean isTrash() {
		return trash;
	}
	
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	
	
	
}
