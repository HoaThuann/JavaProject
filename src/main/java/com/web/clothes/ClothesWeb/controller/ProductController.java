package com.web.clothes.ClothesWeb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.web.clothes.ClothesWeb.dto.mapper.Mapper;
import com.web.clothes.ClothesWeb.dto.requestDto.CategoryRequestDto;
import com.web.clothes.ClothesWeb.dto.requestDto.ProductRequestDto;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.entity.Product;
import com.web.clothes.ClothesWeb.entity.ProductAttributeValue;
import com.web.clothes.ClothesWeb.service.AttributeValueService;
import com.web.clothes.ClothesWeb.service.CategoryService;
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
	private final CategoryService categoryService;
	private final ProductAttributeValueService productAttributeValueService;
	private final ImageService imageService;
	private final Mapper mapper;

	// return view product
	@GetMapping(value = "/list")
	public String getCategoryView() {
		return "admin/product";
	}

	@GetMapping(value = "/order")
	public String get() {
		return "users/order";
	}

	private static String UPLOADED_FOLDER = System.getProperty("user.dir") + "//src//main//resources//static//upload//";

	@PostMapping(value = "/add", consumes = "multipart/form-data", produces = { "application/json", "application/xml" })
	@Transactional
	@ResponseBody
	public ResponseEntity<?> upload(@RequestParam(value="file",required = false) MultipartFile[] files,@Valid
			@ModelAttribute ProductRequestDto productRequestDto,BindingResult bindingResult, HttpSession session) {
		Map<String, Object> errors = new HashMap<>();
		
		Optional<AttributeValue> color = attributeValueService.getAttributeValue(productRequestDto.getColorAttributeValue());
		Optional<AttributeValue> size = attributeValueService.getAttributeValue(productRequestDto.getSizeAttributeValue());
//		Optional<Category> category = categoryService.getCategory(productRequestDto.getCategory());
		
		System.out.println("title" + productRequestDto.getTitle());
		System.out.println("CategoryId" + productRequestDto.getCategory());
		System.out.println("price" + productRequestDto.getPrice());
		System.out.println("Current price" + productRequestDto.getCurrentPrice());
		System.out.println("Descrip" + productRequestDto.getDescription());
		System.out.println("Descrip" + productRequestDto.getSizeAttributeValue());
		System.out.println("Descrip" + productRequestDto.getColorAttributeValue());
		System.out.println("Descrip" + productRequestDto.getDescription());
		System.out.println("Gender" + productRequestDto.getGender());
		// validate input data
		if (bindingResult.hasErrors()) {
			errors.put("bindingErrors", bindingResult.getAllErrors());
		}
		 if (files == null) {
			 errors.put("fileErrors", "Please upload at least a file.");
	     }
		 Optional<Product> productByTitle = productService.getProductByTitle(productRequestDto.getTitle());
		 
		 if(productByTitle.isPresent()) {
			 errors.put("titleDuplicate", "Title already exists! please enter a new title. ");
		 }
//		 if(!color.isPresent()) {
//			 errors.put("colorNotFound", "Color doesn't exist.");
//		 }
//		 if(!size.isPresent()) {
//			 errors.put("sizeNotFound", "Size doesn't exist.");
//		 }
//		 if(!category.isPresent()) {
//			 errors.put("categoryNotFound", "Category doesn't exist.");
//		 }
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}
		// product
		Product product = mapper.productRequestDtoToProduct(productRequestDto);
		Optional<Product> savedProduct = productService.save(product);
		//product_attributValue
		
		if(savedProduct.isPresent()) {
			ProductAttributeValue productAttributeValue = new ProductAttributeValue(savedProduct.get(),size.get(),color.get());
			// image
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				String nameForShow = file.getOriginalFilename();
				if (file.isEmpty()) {
					System.out.println(1);
					continue;
				}
				try {
					// Lưu ảnh vào thư mục tạm thời
					byte[] bytes = file.getBytes();
					System.out.println(2);
					String randomFileName = UUID.randomUUID().toString() + "."
							+ FilenameUtils.getExtension(file.getOriginalFilename());
					Path dir = Paths.get(UPLOADED_FOLDER);
					Path path = Paths.get(UPLOADED_FOLDER + randomFileName);
					if (!Files.exists(dir)) {
						System.out.println(3);
						Files.createDirectories(dir);
					}
					Files.write(path, bytes);
					System.out.println(4);
					// Tạo đối tượng Image và lưu xuống cơ sở dữ liệu
					System.out.println(5);
					Image image = new Image();
					image.setInmageForShow(nameForShow);
					image.setInmageForSave(randomFileName);
					image.setIsDefault(false);
					image.setProduct(product);
					
					System.out.println(6);
					imageService.save(image);
					System.out.println(7);
				} catch (IOException e) {
					e.printStackTrace();
					return ResponseEntity.badRequest().body("{'message':'lưu ảnh thất bại 2'}");
				}
			}
					
		}
		
		System.out.println(8);
		return ResponseEntity.ok().body("{\"message\": \"Lưu ảnh thành công\"}");
	}

}
