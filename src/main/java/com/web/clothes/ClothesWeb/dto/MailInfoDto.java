package com.web.clothes.ClothesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MailInfoDto {
	String from;
	String to;
	String subject;
	String body;
	
	public MailInfoDto(String to, String subject, String body) {
//		this.from = "Shop";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
