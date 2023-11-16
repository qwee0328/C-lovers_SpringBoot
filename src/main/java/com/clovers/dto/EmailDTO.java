package com.clovers.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString 
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
	private String reference_id;
	private Timestamp send_date;
	private boolean confirmation;
}
