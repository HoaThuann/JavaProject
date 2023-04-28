package com.web.clothes.ClothesWeb.service;


import com.web.clothes.ClothesWeb.dto.UserRegisterDto;
import com.web.clothes.ClothesWeb.entity.User;

public interface UserService {
	public User getUser(Integer userId);
	 public void signUp(UserRegisterDto userRequestDto);
}
