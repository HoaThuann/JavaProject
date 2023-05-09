package com.web.clothes.ClothesWeb.dto.requestDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class LoginRequestDto {
	@NotEmpty(message="Email cannot be empty")
	@Email(message="Email should be valid")
	private String email;

	@NotEmpty(message="Password cannot be empty")
	@Size(min = 6, max = 30,message="Password must be between 6 and 30 characters long")
	private String password;
	
	 
}
