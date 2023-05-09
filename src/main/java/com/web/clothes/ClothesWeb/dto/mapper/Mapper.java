package com.web.clothes.ClothesWeb.dto.mapper;

import org.springframework.stereotype.Component;

import com.web.clothes.ClothesWeb.dto.requestDto.AttributeValueRequestDto;
import com.web.clothes.ClothesWeb.dto.requestDto.UserRequestDto;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.User;
@Component
public class Mapper {
	public User userRquestDtoMapToUser(UserRequestDto userRequestDto) {
		User user = new User();
		user.setUserName(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setFullName(userRequestDto.getFullname());
        user.setAddress(userRequestDto.getAddress());
        user.setPhone(userRequestDto.getPhone());
        user.setEmail(userRequestDto.getEmail());
		return user;

	}
	public AttributeValue attributeValueRequestDtoToAttributeValue(AttributeValueRequestDto attributeValueRequestDto) {
		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setAttributeValueName(attributeValueRequestDto.getAttributeValueName());
		attributeValue.setAttribute(attributeValueRequestDto.getAttribute());

		return attributeValue;

	}
}
