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
		System.out.println("vao send cua MailerSericeImpl");
		javaMailSender.send(msg);
	}



}
