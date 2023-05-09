package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.dto.mapper.Mapper;
import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.jwt.JwtTokenProvider;
import com.web.clothes.ClothesWeb.repository.AttributeRepository;
import com.web.clothes.ClothesWeb.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttributeServiceImpl implements AttributeService{

	private final AttributeRepository attributeRepository;

	@Override
	public Optional<Attribute> getAttribute(Integer attributeId) {
		Optional<Attribute> attribute = attributeRepository.getAttributeById(attributeId);
		return attribute;
	}
}
