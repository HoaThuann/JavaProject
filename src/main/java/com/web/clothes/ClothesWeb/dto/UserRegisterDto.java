package com.web.clothes.ClothesWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserRegisterDto {
	private String username;
	private String password;
	private String fullname;
	private String address;
	private String phone;
	private String email;
}
