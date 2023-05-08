package com.web.clothes.ClothesWeb.controller;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import javax.swing.text.html.Option;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.web.clothes.ClothesWeb.dto.AuthenticationResponseDto;
import com.web.clothes.ClothesWeb.dto.LoginRequestDto;
import com.web.clothes.ClothesWeb.dto.MailInfoDto;
import com.web.clothes.ClothesWeb.dto.UserRequestDto;
import com.web.clothes.ClothesWeb.dto.mapper.Mapper;
import com.web.clothes.ClothesWeb.entity.ConfirmationToken;
import com.web.clothes.ClothesWeb.entity.Role;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.jwt.CustomUserDetails;
import com.web.clothes.ClothesWeb.jwt.JwtTokenProvider;
import com.web.clothes.ClothesWeb.repository.ConfirmationTokenRepository;
import com.web.clothes.ClothesWeb.repository.UserRepository;
import com.web.clothes.ClothesWeb.service.ConfirmationTokenService;
import com.web.clothes.ClothesWeb.service.MailerSericeImpl;
import com.web.clothes.ClothesWeb.service.MailerService;
import com.web.clothes.ClothesWeb.service.RoleService;
import com.web.clothes.ClothesWeb.service.SessionService;
import com.web.clothes.ClothesWeb.service.UserService;
import com.web.clothes.ClothesWeb.service.UserServiceImpl;

import java.util.Map;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/user")
public class UserController {

	private final UserService userService;

//	private final SessionService sessionService;

	private final MailerService mailerService;

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;
	private final ConfirmationTokenService confirmationTokenService;

	private final AuthenticationManager authenticationManager;

	private final JwtTokenProvider tokenProvider;

	private static final String USER = "USER";

	private final RoleService roleService;
	private final Mapper mapper;

	@GetMapping(value = "/register")
	public String displayRegistration(Model model) {
		model.addAttribute("userRequestDto", new UserRequestDto());
		return "users/register";
	}

	@PostMapping(value = "/checkRegister")
	public String registerUser(Model model, @ModelAttribute("userRequestDto") @Valid UserRequestDto userRequestDto,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			
			return "users/register";
		}
		Optional<User> UserByEmail = userService.findUserByEmail(userRequestDto.getEmail());
		Optional<User> UserByPhone = userService.findUserByPhone(userRequestDto.getPhone());
		Optional<User> UserByUserName = userService.findUserByUsername(userRequestDto.getUsername());
		Optional<Role> role = roleService.getRoleByName(USER);
		
		if (UserByEmail.isPresent() || UserByPhone.isPresent() || UserByUserName.isPresent() ||  !userRequestDto.getPassword().equals(userRequestDto.getConfirmPassword())) {
			if (UserByEmail.isPresent()) {
				model.addAttribute("emailDuplicate", "Email already exists! Please enter a new one");
			}
			if (UserByPhone.isPresent()) {
				model.addAttribute("phoneDuplicate", "Phone already exists! Please enter a new one");
			}
			if (UserByUserName.isPresent()) {
				model.addAttribute("usernameDuplicate", "Username already exists! Please enter a new one");
			}
			if(!userRequestDto.getPassword().equals(userRequestDto.getConfirmPassword())) {
				model.addAttribute("notMatchPass", "Passwords do not match");
			}
			return "users/register";
		}

		String encodedPassword = passwordEncoder.encode(userRequestDto.getPassword());
		userRequestDto.setPassword(encodedPassword);

		User user = mapper.userRquestDtoMapToUser(userRequestDto);
		user.setRole(role.get());
		userService.save(user);

		ConfirmationToken confirmationToken = new ConfirmationToken(user);

		confirmationTokenService.save(confirmationToken);
		MailInfoDto mailInfoDto = new MailInfoDto(user.getEmail(), "Puu-Verify Your Account",
				" Thank you for signing up for our service. To ensure the security of your account, please verify your email address by clicking on the link below:"
						+ "http://localhost:8080/user/confirm-account?token=" + confirmationToken.getToken());

		mailerService.send(mailInfoDto);
		model.addAttribute("email", mailInfoDto.getTo());
		return "users/successfulRegisteration";
	}

	@PostMapping(value = "/resendMail")
	public String sendMailVerifyAccount(@RequestParam("email") String email, Model model) {

		Optional<User> user = userService.findUserByEmail(email);

		ConfirmationToken confirmationToken = new ConfirmationToken(user.get());

		confirmationTokenService.save(confirmationToken);
		MailInfoDto mailInfoDto = new MailInfoDto(user.get().getEmail(), "Puu-Verify Your Account",
				" Thank you for signing up for our service. To ensure the security of your account, please verify your email address by clicking on the link below:"
						+ "http://localhost:8080/user/confirm-account?token=" + confirmationToken.getToken());

		mailerService.send(mailInfoDto);
		model.addAttribute("email", mailInfoDto.getTo());
		return "users/successfulRegisteration";
	}

	@RequestMapping(value = "/confirm-account", method = { RequestMethod.GET, RequestMethod.POST })
	public String confirmUserAccount(Model model, @RequestParam("token") String confirmationToken) {
		Optional<ConfirmationToken> token = confirmationTokenService.getConfirmationTokenByToken(confirmationToken);

		// if token is not valid
		if (!token.isPresent()) {
			model.addAttribute("message", "The link is invalid or broken!");
			return "users/verifyTokenError";
		}
		// if token is valid but expiryDate

		if (token.isPresent()) {

			if (token.get().getExpiryDate().isBefore(LocalDateTime.now())) {
				model.addAttribute("message", "The link is invalid or broken!");
				return "users/verifyTokenError";
			}
			Optional<User> user = userService.findUserByEmail(token.get().getUser().getEmail());
			user.get().setActive(true);
			userService.save(user.get());
			return "users/accountVerified";
		} else {
			model.addAttribute("message", "The link is invalid or broken!");
			return "users/verifyTokenError";
		}

	}

	@GetMapping(value = "/login")
	public String authenticateUser(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			model.addAttribute("loginRequestDto", new LoginRequestDto());
			return "users/login";
		}
		Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

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
	public ResponseEntity<?> authenticateUser1(@Valid @RequestBody LoginRequestDto loginRequestDto,
			BindingResult bindingResult, Model model) {

		AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto();

		Optional<User> user = userRepository.findUserByEmail(loginRequestDto.getEmail());

		if (!user.isPresent()) {
			authenticationResponseDto.setMessage("The account with email is not exist, please check again");
			return ResponseEntity.badRequest().body(authenticationResponseDto);
		}

		if (user.isPresent() && !user.get().isActive()) {
			authenticationResponseDto.setMessage(
					"The account has not been verified, please check your email to verify your account before logging in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(authenticationResponseDto);
		}
		// Authenticate from username and password.
		Authentication authentication = null;

		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));
		} catch (BadCredentialsException ex) {
			authenticationResponseDto.setMessage("The account or password is incorrect, please check again");
			return ResponseEntity.badRequest().body(authenticationResponseDto);

		}

		// If no exception occurs, the information is valid
		// Set authentication information to Security Context
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// generate token
		String token = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());

		// get role
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

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
