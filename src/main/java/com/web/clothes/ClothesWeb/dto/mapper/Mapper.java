package com.web.clothes.ClothesWeb.dto.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.web.clothes.ClothesWeb.dto.requestDto.AttributeValueRequestDto;
import com.web.clothes.ClothesWeb.dto.requestDto.UserRequestDto;
import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.service.AttributeService;
import com.web.clothes.ClothesWeb.service.AttributeValueService;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class Mapper {
	private final AttributeService attributeService;
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
		Optional<Attribute> attribute = attributeService.getAttribute(attributeValueRequestDto.getAttributeName());
		attributeValue.setAttribute(attribute.get());

		return attributeValue;

	}
}
