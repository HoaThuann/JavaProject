package com.web.clothes.ClothesWeb.controller;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.web.clothes.ClothesWeb.dto.mapper.Mapper;
import com.web.clothes.ClothesWeb.dto.requestDto.AttributeValueRequestDto;
import com.web.clothes.ClothesWeb.dto.responseDto.AttributeValueResponseDto;
import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.service.AttributeService;
import com.web.clothes.ClothesWeb.service.AttributeValueService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/attributeValue")
public class AttributeValueController {

	private final AttributeValueService attributeValueService;
	private final AttributeService attributeService;
	private final Mapper mapper;
	
	//add attribute value
	@PostMapping(value = "/add")
	public String addAttributeValue(
			@ModelAttribute("attributeValueRequestDto") @Valid AttributeValueRequestDto attributeValueRequestDto,
			BindingResult bindingResult,Model model) {
		//validate input data
		if (bindingResult.hasErrors()) {
			return "admin/attribute";
		}
		
		//check if attribute is exist
		Optional<Attribute> attribute = attributeService.getAttribute(attributeValueRequestDto.getAttribute().getId());
		if(attribute.isPresent()) {
			model.addAttribute("error", "The system is having problems! Please try again later");
			return "admin/attribute";
		}
		
		//check if Attribute value name is exist
		Optional<AttributeValue> attributeValueByName = attributeValueService.findAttributeValueByName(attributeValueRequestDto.getAttributeValueName());
		if(attributeValueByName.isPresent()) {
			model.addAttribute("error", "Attribute value is not exist! Update failse!");
			return "admin/attribute";
		}
		
		//map attributeValueRequestDto to attributeValue
		AttributeValue attributeValue = mapper.attributeValueRequestDtoToAttributeValue(attributeValueRequestDto);
		attributeValueService.save(attributeValue);
		
		model.addAttribute("success", "A new attribute added successfully.");
		return "admin/attribute";
	}
	
	@PutMapping(value = "/update")
	public String updateAttributeValue(@PathVariable Integer attributeValueId,
			@ModelAttribute("attributeValueRequestDto") @Valid AttributeValueRequestDto attributeValueRequestDto,
			BindingResult bindingResult,Model model) {
		
		//validate input data
		if (bindingResult.hasErrors()) {
			return "admin/attribute";
		}
		
		//check if attribute is exist
		Optional<Attribute> attribute = attributeService.getAttribute(attributeValueRequestDto.getAttribute().getId());
		if(attribute.isPresent()) {
			model.addAttribute("error", "The system is having problems! Please try again later");
			return "admin/attribute";
		}
		
		//check if Attribute value is exist
		Optional<AttributeValue> attributeValueById = attributeValueService.getAttributeValue(attributeValueId);
		if(attributeValueById.isPresent()) {
			model.addAttribute("error", "Attribute value is not exist! Update failse!");
			return "admin/attribute";
		}
		
		//check if Attribute value name is exist
		Optional<AttributeValue> attributeValueByName = attributeValueService.findAttributeValueByName(attributeValueRequestDto.getAttributeValueName());
		if(attributeValueByName.isPresent()) {
			model.addAttribute("error", "Attribute value is not exist! Update failse!");
			return "admin/attribute";
		}
		
		//map attributeValueRequestDto to attributeValue
		AttributeValue attributeValue = mapper.attributeValueRequestDtoToAttributeValue(attributeValueRequestDto);
		
		attributeValueService.save(attributeValue);
		
		model.addAttribute("success", "A new attribute updated successfully.");
		return "admin/attribute";
	}
	
	@DeleteMapping(value = "/delete")
	public String deleteAttributeValue(@PathVariable Integer attributeValueId,Model model) {
		
		//check if Attribute value is exist
		Optional<AttributeValue> attributeValueById = attributeValueService.getAttributeValue(attributeValueId);
		if(attributeValueById.isPresent()) {
			model.addAttribute("error", "Attribute value is not exist! Update failse!");
			return "admin/attribute";
		}
		
		attributeValueService.deleteAttributeValue(attributeValueId);
		
		model.addAttribute("success", "A new attribute deleted successfully.");
		return "admin/attribute";
	}
	
	@GetMapping(value = "/list")
	public String getAllAttributes(@RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page,Model model) {
		
	    Page<AttributeValue> attributeValuePage = attributeValueService.getAllAttributeValue(page, size);
	    List<AttributeValueResponseDto> taskListResponseDtoList=attributeValuePage.stream().map(attributeValue -> new AttributeValueResponseDto(attributeValue.getId(),
	    		attributeValue.getAttributeValueName())).collect(Collectors.toList());
	    model.addAttribute("taskListResponseDtoList", taskListResponseDtoList);
	    model.addAttribute("page", attributeValuePage);
	    return "admin/attribute";
	}

}
