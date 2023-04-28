package com.web.clothes.ClothesWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.repository.AttributeRepository;

@Service
public class AttributeServiceImpl implements AttributeService{
	@Autowired
	private AttributeRepository attributeRepository;
}
