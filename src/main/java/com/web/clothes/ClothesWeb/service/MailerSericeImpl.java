package com.web.clothes.ClothesWeb.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.dto.MailInfoDto;
import com.web.clothes.ClothesWeb.dto.mapper.Mapper;
import com.web.clothes.ClothesWeb.entity.ConfirmationToken;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.jwt.JwtTokenProvider;
import com.web.clothes.ClothesWeb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailerSericeImpl implements MailerService {
	
	private final JavaMailSender javaMailSender;
	private final ConfirmationTokenService confirmationTokenService;

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

	@Override
	public void sendEmailToConfirmAccount(User user) {
		ConfirmationToken confirmationToken = new ConfirmationToken(user);

		confirmationTokenService.save(confirmationToken);
		MailInfoDto mailInfoDto = new MailInfoDto(user.getEmail(), "Puu-Verify Your Account",
				" Thank you for signing up for our service. To ensure the security of your account, please verify your email address by clicking on the link below:"
						+ "http://localhost:8080/user/confirm-account?token=" + confirmationToken.getToken());
		
		send(mailInfoDto);
		
	}

}
