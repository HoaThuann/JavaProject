package com.web.clothes.ClothesWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.web.clothes.ClothesWeb.dto.UserRegisterDto;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.service.MailerSericeImpl;
import com.web.clothes.ClothesWeb.service.RoleService;
import com.web.clothes.ClothesWeb.service.SessionService;
import com.web.clothes.ClothesWeb.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private MailerSericeImpl mailerSericeImpl;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private static final String USER = "USER";
	private RoleService roleService;
	
	@PostMapping("/register/{check}")
	public String save(@Validated UserRegisterDto entity, @PathVariable("check") String check, 
			BindingResult bindingResult) {
		
		if (check.equals("mailSender")) {
			int code = (int) Math.floor(((Math.random() * 899999) + 100000));
			sessionService.set("code", code);
			mailerSericeImpl.queue(entity.getEmail(), "Xac nhan Email!!", "Ma xac nhan cua ban la: "+code);
		}
		
		if (bindingResult.hasErrors()) {
			
		}else {
			if (entity.getCode().equals(sessionService.get("code").toString())==false) {
				bindingResult.rejectValue("code", "Sai ma xac nhan!!");
			}else {
				User user = new User();
				String password = passwordEncoder.encode(entity.getPassword());
				
				user.setFullName(entity.getFullname());
				user.setEmail(entity.getEmail());
				user.setPhone(entity.getPhone());
				user.setAddress(entity.getAddress());
				user.setRole(roleService.getRoleByName(USER));
				user.setPassword(password);
				
				userService.save(user);
			}
		}
		
		return "/register";
	}
}
