package com.web.clothes.ClothesWeb.service;


import java.util.Optional;

import com.web.clothes.ClothesWeb.dto.UserRequestDto;
import com.web.clothes.ClothesWeb.entity.User;

public interface UserService {
	public User getUser(Integer userId);
	void save(User user);
	public Optional<User> findUserByEmail(String email );
	public Optional<User> findUserByPhone(String phone );
	public Optional<User> findUserByUsername(String username );
	

	
}
