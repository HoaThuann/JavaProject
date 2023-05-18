package com.web.clothes.ClothesWeb.service;

import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.ProductAttributeValue;
import com.web.clothes.ClothesWeb.repository.ProductAttributeValueRepository;


import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService{
	private final ProductAttributeValueRepository productAttributeValueRepository;
	@Override
	public void save(ProductAttributeValue productAttributeValue) {
		productAttributeValueRepository.save(productAttributeValue);
	}

}
