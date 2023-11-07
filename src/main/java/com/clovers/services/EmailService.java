package com.clovers.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

//	비밀번호 변경 이메일 관련 코드
	
	@Autowired
	private JavaMailSender javaMailSender;
	private static final String senderEmail = "c-lovers@gmail.com";
	private static int number;
	
	public static void createNumber() {
		number = (int)(Math.random()*(90000)) + 10000;
	}
	
	public MimeMessage CreateMail(String email) {
		
		createNumber();
		
		MimeMessage message = javaMailSender.createMimeMessage();
		
		try {
			message.setFrom(senderEmail);
			message.setRecipients(MimeMessage.RecipientType.TO, email);
			message.setSubject("C-lovers 이메일 인증");
			String body = "";
			body += "<h3>"+"요청하신 인증 번호입니다."+"<h3>";
			body += "<h1>"+number+"<h1>";
			body += "<h3>"+"감사합니다."+"<h3>";
			message.setText(body,"UTF-8","html");
		}catch(MessagingException e) {
			e.printStackTrace();
		}
		return message;
	}
	
	
	public int sendMail(String email) {
		MimeMessage message = CreateMail(email);
		javaMailSender.send(message);
		
		return number;
	}
	
	
	
}
