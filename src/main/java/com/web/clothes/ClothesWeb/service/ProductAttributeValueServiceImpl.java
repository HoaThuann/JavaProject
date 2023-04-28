package com.web.clothes.ClothesWeb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.web.clothes.ClothesWeb.repository.ProductAttributeValueRepository;
@Service
public class ProductAttributeValueServiceImpl implements ProductAttributeValueService{
	@Autowired
	private ProductAttributeValueRepository productAttributeValueRepository;
}
