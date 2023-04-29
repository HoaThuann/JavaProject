package com.web.clothes.ClothesWeb.service;

import com.web.clothes.ClothesWeb.dto.MailInfoDto;

public interface MailerService {
	void send(MailInfoDto mail);
	void send(String to, String subject, String body);
	void queue(MailInfoDto mail);
	void queue(String to, String subject, String body);
}
