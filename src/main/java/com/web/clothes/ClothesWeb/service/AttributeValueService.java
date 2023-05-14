package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;

public interface AttributeValueService {
	public Optional<AttributeValue> getAttributeValue(Integer attributeValueId);
	void save(AttributeValue attributeValue);
	void updateAttributeValue(AttributeValue attributeValue);
	void deleteAttributeValue(Integer attributeValueId);
	public Optional<AttributeValue> findAttributeValueByName(String attributeValueName);
	public Page<AttributeValue> getAttributeValueByAttribute(int pageNumber, int szie,String attributeName);
//	public Optional<AttributeValue> findAttributeValueByKey(String name );
}
