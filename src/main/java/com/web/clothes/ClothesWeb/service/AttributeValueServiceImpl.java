package com.web.clothes.ClothesWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.repository.AttributeValueRepository;

@Service
public class AttributeValueServiceImpl implements AttributeValueService{
	@Autowired
	private AttributeValueRepository attributeValueRepository;
}
