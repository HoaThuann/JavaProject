package com.web.clothes.ClothesWeb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.Category;

@Repository
public interface AttributeValueRepository extends JpaRepository<AttributeValue, Integer> {
	public Optional<AttributeValue> getAttributeValueById(Integer AttributeValueId);

	public Optional<AttributeValue> getAttributeValueByAttributeValueNameIgnoreCase(String attibuteName);

	//public void deleteAttributeValueById(Integer attributeValueId);

//public Page<AttributeValue> findAttributeValueByAttribute(Pageable Page,Attribute attribute);
	@Query("SELECT a FROM AttributeValue a WHERE a.deleted = 0 AND a.attribute = :attribute")
	Page<AttributeValue> findAll(Pageable pageable, @Param("attribute") Attribute attribute);
}
