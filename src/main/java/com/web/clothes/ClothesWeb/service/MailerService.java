package com.web.clothes.ClothesWeb.service;

import com.web.clothes.ClothesWeb.dto.MailInfoDto;

public interface MailerService {
	void send(MailInfoDto mail);

}
