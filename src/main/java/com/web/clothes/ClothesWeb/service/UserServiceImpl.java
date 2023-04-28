package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.dto.UserRegisterDto;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.exception.UserNotFoundException;
//import com.web.clothes.ClothesWeb.jwt.CustomUserDetails;
import com.web.clothes.ClothesWeb.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	private RoleService roleService;
	private static final String USER = "USER";

	@Override
	public User getUser(Integer userId) {
		// check role is exist
		Optional<User> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			throw new UserNotFoundException(HttpStatus.NOT_FOUND.value(), "Can not find user with id " + userId);
		}
		return user.get();
	}

	@Override
	public void signUp(UserRegisterDto userRegisterDto) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(userRegisterDto.getPassword());

		User user = new User();

		user.setFullName(userRegisterDto.getFullname());
		user.setEmail(userRegisterDto.getEmail());
		user.setPhone(userRegisterDto.getPhone());
		user.setAddress(userRegisterDto.getAddress());
		user.setRole(roleService.getRoleByName(USER));
		user.setPassword(encodedPassword);
		
		userRepository.save(user);
	}
}
