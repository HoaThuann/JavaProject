package com.web.clothes.ClothesWeb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.repository.AttributeValueRepository;
import org.springframework.data.domain.Sort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;
@Service
@RequiredArgsConstructor
public class AttributeValueServiceImpl implements AttributeValueService{
	
	private final AttributeValueRepository attributeValueRepository;
	
	private final AttributeService attributeService;

	@Override
	public Optional<AttributeValue> getAttributeValue(Integer attributeValueId) {
		Optional<AttributeValue> attributeValue = attributeValueRepository.getAttributeValueById(attributeValueId);
		return attributeValue;
	}

	@Override
	public void save(AttributeValue attributeValue) {
		attributeValueRepository.save(attributeValue);
	}

	@Override
	public void updateAttributeValue(AttributeValue attributeValue) {
		attributeValueRepository.save(attributeValue);
	}

	@Override
	public void deleteAttributeValue(AttributeValue attributeValue) {
		attributeValue.setDeleted(1);
		attributeValue.setDeleteAt(LocalDate.now());
		attributeValueRepository.save(attributeValue);
	}

	@Override
	public Optional<AttributeValue> findAttributeValueByName(String attributeValueName) {
		Optional<AttributeValue> attributeValue = attributeValueRepository.getAttributeValueByAttributeValueNameIgnoreCase(attributeValueName);
		return attributeValue;
	}

	@Override
	public Page<AttributeValue> getAttributeValueByAttribute(int pageNumber, int szie,String attributeName) {
		Optional<Attribute> attribute =attributeService.getAttribute(attributeName);
		PageRequest attributePageable = PageRequest.of(pageNumber, szie, Sort.by(Sort.Direction.ASC, "attributeValueName"));
		
		 Page<AttributeValue> attributeValuePage = attributeValueRepository.findAll(attributePageable,attribute.get());
		 
		return attributeValuePage;
	}


}
