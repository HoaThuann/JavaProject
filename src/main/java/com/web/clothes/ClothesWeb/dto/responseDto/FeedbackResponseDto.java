package com.web.clothes.ClothesWeb.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FeedbackResponseDto {
	private String email;
	private String fullName;
	private String phone;
	private String SubjectName;
}
