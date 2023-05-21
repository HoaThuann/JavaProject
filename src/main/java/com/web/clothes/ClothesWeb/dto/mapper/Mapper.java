package com.web.clothes.ClothesWeb.dto.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.web.clothes.ClothesWeb.dto.requestDto.AttributeValueRequestDto;
import com.web.clothes.ClothesWeb.dto.requestDto.ProductRequestDto;
import com.web.clothes.ClothesWeb.dto.requestDto.UserRequestDto;
import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.User;
import com.web.clothes.ClothesWeb.entity.Product;
import com.web.clothes.ClothesWeb.service.AttributeService;
import com.web.clothes.ClothesWeb.service.AttributeValueService;
import com.web.clothes.ClothesWeb.service.CategoryService;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class Mapper {
	private final AttributeService attributeService;
	private final AttributeValueService attributeValueService;
	private final CategoryService categoryService;
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
	
	public Product productRequestDtoToProduct(ProductRequestDto productRequestDto) {
		Product product = new Product();
		if(productRequestDto.getId()!=null) {
			product.setId(productRequestDto.getId());
		}
		
		
		//set title
		product.setTitle(productRequestDto.getTitle());
		
		//set category
		Optional<Category> category = categoryService.getCategory(productRequestDto.getCategory());
		product.setCategory(category.get());
		
		//set price
		product.setPrice(productRequestDto.getPrice());
		
		//set current price
		if( productRequestDto.getCurrentPrice()!=0) {
		product.setCurrentPrice(productRequestDto.getCurrentPrice());
		}
		
		
		//set gender
		product.setGender(Boolean.parseBoolean(productRequestDto.getGender()));
		
//		//set size
//		Optional<AttributeValue> size = attributeValueService.getAttributeValue(productRequestDto.getSizeAttributeValue());
//		product.setSizeAttributeValue(size.get());
//		
//		//set color
//		Optional<AttributeValue> color = attributeValueService.getAttributeValue(productRequestDto.getColorAttributeValue());
//		product.setColorAttributeValue(color.get());
//		
		//set discription
		product.setDiscription(productRequestDto.getDescription());

		return product;

	}
}
