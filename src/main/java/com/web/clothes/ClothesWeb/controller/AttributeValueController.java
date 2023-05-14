package com.web.clothes.ClothesWeb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.web.clothes.ClothesWeb.dto.mapper.Mapper;
import com.web.clothes.ClothesWeb.dto.requestDto.AttributeValueRequestDto;
import com.web.clothes.ClothesWeb.dto.responseDto.AttributeValueResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.AttributeValuePageResponseDto;
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

	// add attribute value
	@PostMapping(value = "/add")
	@ResponseBody
	public ResponseEntity<?> addAttributeValue(@Valid @RequestBody AttributeValueRequestDto attributeValueRequestDto,
			BindingResult bindingResult, Model model) {
		Map<String, Object> errors = new HashMap<>();

		// validate input data
		if (bindingResult.hasErrors()) {
			System.out.println("1");
			errors.put("bindingErrors", bindingResult.getAllErrors());
		}

		// check if attribute is exist
		Optional<Attribute> attribute = attributeService.getAttribute(attributeValueRequestDto.getAttributeName());
		// check if Attribute value name is exist
		Optional<AttributeValue> attributeValueByName = attributeValueService
				.findAttributeValueByName(attributeValueRequestDto.getAttributeValueName());
		if (attribute.isEmpty()) {
			
			errors.put("NotFoundAttribute", "The system is having problems! Please try again later");

		}
		if (attributeValueByName.isPresent() ) {
			
			errors.put("NotFoundAttributeExist", "Attribute value already exists! Please enter a new one!");
		}

		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		// map attributeValueRequestDto to attributeValue
		AttributeValue attributeValue = mapper.attributeValueRequestDtoToAttributeValue(attributeValueRequestDto);
		attributeValueService.save(attributeValue);
		System.out.println("thành công");
		String success = "A new attribute added successfully.";
		return ResponseEntity.ok().body(success);
	}

	@PutMapping(value = "/update")
	@ResponseBody
	public ResponseEntity<?> updateAttributeValue(@RequestParam Integer attributeValueId,
			@Valid @RequestBody AttributeValueRequestDto attributeValueRequestDto, BindingResult bindingResult) {

		
		Map<String, Object> errors = new HashMap<>();
		// validate input data
		if (bindingResult.hasErrors()) {
			
			errors.put("bindingErrors", bindingResult.getAllErrors());
			return ResponseEntity.badRequest().body(errors);
		}

		// check if attribute is exist
		Optional<Attribute> attribute = attributeService.getAttribute(attributeValueRequestDto.getAttributeName());
		if (attribute.isEmpty()) {
			
			errors.put("error", "The system is having problems! Please try again later");
			return ResponseEntity.badRequest().body(errors);

		}

		// check if Attribute value is exist
		Optional<AttributeValue> attributeValueById = attributeValueService.getAttributeValue(attributeValueId);
		Optional<AttributeValue> attributeValueByName = attributeValueService
				.findAttributeValueByName(attributeValueRequestDto.getAttributeValueName());
		
		if (attributeValueById.isEmpty()) {
			errors.put("error", "Attribute value is not exist! Update failse!");
			return ResponseEntity.badRequest().body(errors);

		} else if (attributeValueByName.isPresent() && !(attributeValueByName.get().getId()).equals(attributeValueId)) {
			// check if Attribute value name is exist
			
			errors.put("error", "Attribute value name already exists! Please enter a new one!");
			return ResponseEntity.badRequest().body(errors);
		}
		// map attributeValueRequestDto to attributeValue
		AttributeValue attributeValue = mapper.attributeValueRequestDtoToAttributeValue(attributeValueRequestDto);
		attributeValue.setId(attributeValueId);
		attributeValueService.save(attributeValue);

		return ResponseEntity.ok().body("A new attribute updated successfully.");
	}

	@DeleteMapping(value = "/delete")
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteAttributeValue(@RequestParam("attributeValueId") Integer attributeValueId) {
		
		// check if Attribute value is exist
		Optional<AttributeValue> attributeValueById = attributeValueService.getAttributeValue(attributeValueId);
		if (attributeValueById.isEmpty()) {
			
			return ResponseEntity.badRequest().body("Attribute value is not exist! Update failse!");
		}
		
		attributeValueService.deleteAttributeValue(attributeValueId);
		return ResponseEntity.ok().body("A new attribute deleted successfully.");
	}

	@GetMapping(value = "/list")
	public String getAllAttributes() {
		return "admin/attribute";
	}

	@GetMapping(value = "/getAttributePage")
	@ResponseBody
	public ResponseEntity<AttributeValuePageResponseDto> getAllAttributesJson(
			@RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "0") int page,
			@RequestParam String attributeName) {

		Page<AttributeValue> attributeValuePage = attributeValueService.getAttributeValueByAttribute(page, size,
				attributeName);
		List<AttributeValueResponseDto> attributeValueResponseDto = attributeValuePage.stream()
				.map(attributeValue -> new AttributeValueResponseDto(attributeValue.getId(),
						attributeValue.getAttributeValueName()))
				.collect(Collectors.toList());
		
		AttributeValuePageResponseDto attributeValuePageResponseDto = new AttributeValuePageResponseDto(
				attributeValuePage.getTotalPages(), attributeValuePage.getNumber(), attributeValuePage.getSize(),
				attributeValueResponseDto);
		return ResponseEntity.ok(attributeValuePageResponseDto);

	}

}
