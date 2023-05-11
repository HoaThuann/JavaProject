package com.web.clothes.ClothesWeb.dto.responseDto;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.web.clothes.ClothesWeb.entity.AttributeValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AttributeValuePageResponseDto {
	private int totalPages;
	private int currentPage;
	private int size;
	private List<AttributeValueResponseDto> attributeValueResponseDto;
}
