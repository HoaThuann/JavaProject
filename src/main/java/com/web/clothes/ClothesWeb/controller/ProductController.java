package com.web.clothes.ClothesWeb.controller;


import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.web.clothes.ClothesWeb.dto.mapper.Mapper;
import com.web.clothes.ClothesWeb.dto.requestDto.ProductRequestDto;
import com.web.clothes.ClothesWeb.dto.responseDto.ProductAttributeValueResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.ProductResponseDto;
import com.web.clothes.ClothesWeb.dto.responseDto.ProductPageResponseDto;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.Product;
import com.web.clothes.ClothesWeb.entity.ProductAttributeValue;
import com.web.clothes.ClothesWeb.service.AttributeValueService;
import com.web.clothes.ClothesWeb.service.ImageService;
import com.web.clothes.ClothesWeb.service.ProductAttributeValueService;
import com.web.clothes.ClothesWeb.service.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {

	private final ProductService productService;
	private final AttributeValueService attributeValueService;
	private final ProductAttributeValueService productAttributeValueService;
	private final ImageService imageService;
	private final Mapper mapper;
	private final NumberFormat moneyFormat;

	// return view product
	@GetMapping
	public String getCategoryView() {
		return "admin/product";
	}

	@GetMapping(value = "/order")
	public String get() {
		return "users/order";
	}

	

	@PostMapping(value = "/add", consumes = "multipart/form-data", produces = { "application/json", "application/xml" })
	@Transactional
	@ResponseBody
	public ResponseEntity<?> upload(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam int defaultFileIndex, @Valid @ModelAttribute ProductRequestDto productRequestDto,
			BindingResult bindingResult, HttpSession session) {
		Map<String, Object> errors = new HashMap<>();

		Optional<AttributeValue> color = attributeValueService
				.getAttributeValue(productRequestDto.getColorAttributeValue());
		Optional<AttributeValue> size = attributeValueService
				.getAttributeValue(productRequestDto.getSizeAttributeValue());

		// validate input data
		if (bindingResult.hasErrors()) {
			errors.put("bindingErrors", bindingResult.getAllErrors());
		}
		if (files == null) {
			errors.put("fileErrors", "Please upload at least a file.");
		}
		Optional<Product> productByTitle = productService.getProductByTitle(productRequestDto.getTitle());

		if (productByTitle.isPresent()) {
			errors.put("titleDuplicate", "Title already exists! please enter a new title. ");
		}


		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}
		// product
		Product product = mapper.productRequestDtoToProduct(productRequestDto);
		productService.save(product);
		Optional<Product> savedProduct = productService.getProductByTitle(productRequestDto.getTitle());
		// product_attributValue

		if (savedProduct.isPresent()) {
			if(color.isPresent() || size.isPresent()) {
				ProductAttributeValue productAttributeValue = new ProductAttributeValue();
				if (color.isPresent() && size.isPresent()) {
					productAttributeValue.setProduct(savedProduct.get());
					productAttributeValue.setColor(color.get());
					productAttributeValue.setSize(size.get());
				} else if (color.isPresent()) {
					productAttributeValue.setProduct(savedProduct.get());
					productAttributeValue.setColor(color.get());
				} else if (size.isPresent()) {
					productAttributeValue.setProduct(savedProduct.get());
					productAttributeValue.setSize(size.get());
				}

				productAttributeValueService.save(productAttributeValue);
			}
			
			// image
			imageService.uploadFile(files, defaultFileIndex, product);

		}

		return ResponseEntity.ok().body("{\"message\": \"Add product successfully\"}");
	}
	
	@PutMapping(value = "/update", consumes = "multipart/form-data", produces = { "application/json", "application/xml" })
	@Transactional
	@ResponseBody
	public ResponseEntity<?> updateProduct(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam int defaultFileIndex, @Valid @ModelAttribute ProductRequestDto productRequestDto,
			BindingResult bindingResult, HttpSession session) {
		
		Map<String, Object> errors = new HashMap<>();
		
		Optional<AttributeValue> color = attributeValueService
				.getAttributeValue(productRequestDto.getColorAttributeValue());
		Optional<AttributeValue> size = attributeValueService
				.getAttributeValue(productRequestDto.getSizeAttributeValue());

		if (bindingResult.hasErrors()) {
			errors.put("bindingErrors", bindingResult.getAllErrors());
		}
		if (files == null) {
			errors.put("fileErrors", "Please upload at least a file.");
		}
		Optional<Product> productByTitle = productService.getProductByTitle(productRequestDto.getTitle());

		if (productByTitle.isPresent() && !productByTitle.get().getId().equals(productRequestDto.getId())) {
			errors.put("titleDuplicate", "Title already exists! please enter a new title. ");
		}


		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}
		
		if(!productService.getProduct(productRequestDto.getId()).isPresent()) {
			errors.put("NotFoundProduct", "This product does not exist! Update failed.");
		}
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}
		// product
		Product product = mapper.productRequestDtoToProduct(productRequestDto);
		product.setUpdateAt(LocalDate.now());
		productService.save(product);
		Optional<Product> savedProduct = productService.getProductByTitle(productRequestDto.getTitle());
		// product_attributValue

		if (savedProduct.isPresent()) {
			ProductAttributeValueResponseDto productAttributeValueResponseDto = productAttributeValueService.findProductAttributeValueByProduct(product);
			ProductAttributeValue productAttributeValue = new ProductAttributeValue();
			if(productAttributeValueResponseDto.getId()!=null) {
				productAttributeValue.setId(productAttributeValueResponseDto.getId());
			}
			if(color.isPresent() || size.isPresent()) {
				//if there are productAttributeValueResponseDto set id to update
				
				
				if (color.isPresent() && size.isPresent()) {
					
					productAttributeValue.setProduct(savedProduct.get());
					productAttributeValue.setColor(color.get());
					productAttributeValue.setSize(size.get());
				} else if (color.isPresent()) {
					
					productAttributeValue.setProduct(savedProduct.get());
					productAttributeValue.setColor(color.get());
				} else if (size.isPresent()) {
					
					productAttributeValue.setProduct(savedProduct.get());
					productAttributeValue.setSize(size.get());
				}

				productAttributeValueService.save(productAttributeValue);
			} 
			if(size.isEmpty() && color.isEmpty() && productAttributeValueResponseDto.getId()!=null){
				productAttributeValueService.deleteById(productAttributeValueResponseDto.getId());
			}
			
			
			// image
			imageService.deleteByProduct(product);
			imageService.uploadFile(files, defaultFileIndex, product);

		}
		
		return ResponseEntity.ok().body("{\"message\": \"Update product successfully\"}");
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseBody
	@Transactional
	public ResponseEntity<String> deleteProduct(@RequestParam("productId") Integer productId) {
		
		// check if product is exist
		Optional<Product> productById = productService.getProduct(productId);
		if (productById.isEmpty() || productById.get().getDeleteAt()!=null) {
			return ResponseEntity.badRequest().body("Product is not exist! Delete failse!");
		}
		
		productService.delete(productById.get());
		productAttributeValueService.setDeleteAt(productById.get());
		return ResponseEntity.ok().body("A product deleted successfully.");
	}
	
	//get page product
	@GetMapping(value = "/getProductPage")
	@ResponseBody
	public ResponseEntity<ProductPageResponseDto> getProductPage(
			@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {

		Page<Product> productPage = productService.getProductPage(page, size);
		
		
		
		List<ProductResponseDto> productResponseDtos = productPage.stream()
				.map(product -> new ProductResponseDto(product.getId(),
						product.getTitle(),moneyFormat.format(product.getPrice()),product.getCurrentPrice()!=null? moneyFormat.format(product.getCurrentPrice()) : null
							,product.isGender(),product.getDiscription(),
						product.getCreateAt(),product.getUpdateAt(),product.getCategory().getCategoryName()
						,productAttributeValueService.findProductAttributeValueByProduct(product)!=null ? productAttributeValueService.findProductAttributeValueByProduct(product):null,imageService.getImageByProduct(product)))
				.collect(Collectors.toList());

		ProductPageResponseDto productPageResponseDto = new ProductPageResponseDto(
				productPage.getTotalPages(), productPage.getNumber(), productPage.getSize(),
				productResponseDtos);

		return ResponseEntity.ok(productPageResponseDto);

	}
		

}
