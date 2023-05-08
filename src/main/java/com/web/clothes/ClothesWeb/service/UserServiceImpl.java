package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.dto.UserRequestDto;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.exception.UserNotFoundException;
import com.web.clothes.ClothesWeb.jwt.CustomUserDetails;
//import com.web.clothes.ClothesWeb.jwt.CustomUserDetails;
import com.web.clothes.ClothesWeb.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	@Autowired
	private UserRepository userRepository;
//	private RoleService roleService;
//	private static final String USER = "USER";

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
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findUserByEmail(email);
		return new CustomUserDetails(user.get());
	}

	@Override
	public Optional<User> findUserByEmail(String email) {
		Optional<User> user = userRepository.findUserByEmail(email);
		return user;
	}

	@Override
	public Optional<User> findUserByPhone(String phone) {
		Optional<User> user = userRepository.findUserByEmail(phone);
		return user;
	}

	


}
