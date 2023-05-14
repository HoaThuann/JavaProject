package com.web.clothes.ClothesWeb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
	public Optional<AttributeValue> getAttributeValueById(Integer AttributeValueId);

	public Optional<AttributeValue> getAttributeValueByAttributeValueNameIgnoreCase(String attibuteName);

	public void deleteAttributeValueById(Integer AttributeValueId);

	public Page<AttributeValue> findAttributeValueByAttribute(Pageable Page,Attribute attribute);
}
