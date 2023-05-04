package com.web.clothes.ClothesWeb.dto;

import javax.validation.constraints.NotEmpty;


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
	@NotEmpty(message="k trống email")
	private String email;

	@NotEmpty(message="k trống pass")
	private String password;
	
	 
}
