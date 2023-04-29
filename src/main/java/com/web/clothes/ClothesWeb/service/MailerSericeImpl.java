package com.web.clothes.ClothesWeb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.dto.MailInfoDto;

@Service
public class MailerSericeImpl implements MailerService{
	@Autowired
	JavaMailSender javaMailSender;
	
	List<MailInfoDto> list = new ArrayList<>();

	@Override
	public void send(MailInfoDto mail) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mail.getTo());
		msg.setSubject(mail.getSubject());
		msg.setText(mail.getBody());
		
		javaMailSender.send(msg);
	}

	@Override
	public void send(String to, String subject, String body) {
		this.send(new MailInfoDto(to, subject, body));
		
	}

	@Override
	public void queue(MailInfoDto mail) {
		list.add(mail);
	}

	@Override
	public void queue(String to, String subject, String body) {
		queue(new MailInfoDto(to, subject, body));
		
	}
	
	@Scheduled(fixedDelay = 1000)
	public void run() {
		while(!list.isEmpty()) {
			MailInfoDto mail = list.remove(0);
			try {
				this.send(mail);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
