package com.web.clothes.ClothesWeb.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.clothes.ClothesWeb.dto.AuthenticationResponseDto;
import com.web.clothes.ClothesWeb.dto.LoginRequestDto;
import com.web.clothes.ClothesWeb.dto.UserRegisterDto;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.jwt.CustomUserDetails;
import com.web.clothes.ClothesWeb.jwt.JwtTokenProvider;
import com.web.clothes.ClothesWeb.repository.UserRepository;
import com.web.clothes.ClothesWeb.service.MailerSericeImpl;
import com.web.clothes.ClothesWeb.service.RoleService;
import com.web.clothes.ClothesWeb.service.SessionService;
import com.web.clothes.ClothesWeb.service.UserService;

import java.util.Map;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value="/user")
public class UserController {
	
	private final UserService userService;
	
	private final SessionService sessionService;
	
	private final MailerSericeImpl mailerSericeImpl;
	
	private PasswordEncoder passwordEncoder;
	
	private final UserRepository userRepository;
	
	private final AuthenticationManager authenticationManager;

	private final JwtTokenProvider tokenProvider;

	private static final String USER = "USER";
	
	private RoleService roleService;

	private String token;

	@PostMapping("/register/{check}")
	public String save(@Validated UserRegisterDto entity, @PathVariable("check") String check,
			BindingResult bindingResult) {

		if (check.equals("mailSender")) {
			int code = (int) Math.floor(((Math.random() * 899999) + 100000));
			sessionService.set("code", code);
			mailerSericeImpl.queue(entity.getEmail(), "Xac nhan Email!!", "Ma xac nhan cua ban la: " + code);
		}

		if (bindingResult.hasErrors()) {

		} else {
			if (entity.getCode().equals(sessionService.get("code").toString()) == false) {
				bindingResult.rejectValue("code", "Sai ma xac nhan!!");
			} else {
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

	@RequestMapping(value = "/login")
	public String authenticateUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("loginRequestDto", new LoginRequestDto());
			return "users/login";
		}
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
		if (roles.contains("ADMIN")) {
			// Nếu đây là một quản trị viên, điều hướng về trang đăng nhập.
			return "redirect:/user/homeAdmin";
		} else {
			// Nếu đây là một người dùng bình thường, điều hướng về trang chủ.
			return "redirect:/user/home";
		}

	}


	@PostMapping(value = "/checkLogin")
	@ResponseBody
	public ResponseEntity<?> authenticateUser1(@Valid @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult,
			Model model) {
		
		AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();
		
		Optional<User> user = userRepository.findUserByEmail(loginRequestDto.getEmail());
		System.out.println(1);
		if (!user.isPresent()) {
			System.out.println(1);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationResponseDto);
		}
		// Authenticate from username and password.
		Authentication authentication = null;
		
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
		} catch (BadCredentialsException ex) {
			
			return ResponseEntity.badRequest().body(authenticationResponseDto);
//            throw new UserNotValidException(HttpStatus.BAD_REQUEST.value(),"UserName or Password is not valid");
		}

		// If no exception occurs, the information is valid
		// Set authentication information to Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
		//generate token
		token = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

		// get role
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		System.out.println(token);
		// check if user is user or admin
		String role = null;
		for (GrantedAuthority authority : authorities) {
			if (authority.getAuthority().equals("ADMIN")) {
				role = "ADMIN";
				break;
			} else if (authority.getAuthority().equals("USER")) {
				role = "USER";
				break;
			}
		}
		authenticationResponseDto.setToken(token);
		authenticationResponseDto.setRole(role);

		return ResponseEntity.ok(authenticationResponseDto);
	}
	@GetMapping("/home")
	@ResponseBody
	public String test() {
		return "thanh công đăng nhập";
	}

	@GetMapping("/homeAdmin")
	@ResponseBody
	public String hello() {
		return "đây là trang admin";
	}

	@GetMapping("/sucess")
	public String sucess() {
		System.out.println(SecurityContextHolder.getContext().getAuthentication());
		return "b1";
	}
}
