package com.web.clothes.ClothesWeb.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private SessionService sessionService;
	
	@Autowired
	private MailerSericeImpl mailerSericeImpl;
	
	@Autowired
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
	@RequestMapping(value ="/loginForm")
    public String authenticateUser(Model model) {
        model.addAttribute("loginRequestDto",new LoginRequestDto() );
        return "a";
    }
	@PostMapping(value ="/login1")
	@ResponseBody
    public ResponseEntity<AuthenticationResponseDto> authenticateUser(@RequestBody LoginRequestDto loginRequestDto) {
        Optional<User> user = userRepository.findUserByEmail(loginRequestDto.getEmail());
        System.out.println(loginRequestDto.getEmail());
        System.out.println(loginRequestDto.getPassword());
        // Xác thực từ username và password.
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
//            throw new UserNotValidException(HttpStatus.BAD_REQUEST.value(),"UserName or Password is not valid");
        }


        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
         token = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
         
         //lấy role
         Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

         // Kiểm tra xem người dùng thuộc role nào
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
        return ResponseEntity.ok(new AuthenticationResponseDto(token,role));
    }
	@PostMapping(value ="/login2")
	@ResponseBody
    public String authenticateUser2(@RequestBody LoginRequestDto loginRequestDto) {
        Optional<User> user = userRepository.findUserByEmail(loginRequestDto.getEmail());
        System.out.println(loginRequestDto.getEmail());
        System.out.println(loginRequestDto.getPassword());
        // Xác thực từ username và password.
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getEmail(),
                            loginRequestDto.getPassword()
                    )
            );
        } catch (BadCredentialsException ex) {
//            throw new UserNotValidException(HttpStatus.BAD_REQUEST.value(),"UserName or Password is not valid");
        }


        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
         token = tokenProvider.generateToken((CustomUserDetails) authentication.getPrincipal());
        
        return token;
    }
	 

	@GetMapping("/test")
	@ResponseBody
	public String test() {
		return "thanh công đăng nhập";
	}
	@GetMapping("/hello")
	public String hello() {
		return "b";
	}
}
