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
import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Image;
import com.web.clothes.ClothesWeb.entity.Product;
import com.web.clothes.ClothesWeb.service.ImageService;
import com.web.clothes.ClothesWeb.service.ProductService;

import lombok.RequiredArgsConstructor;
@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/product")
public class ProductController {
	
	private final ProductService productService;
	private final ImageService imageService;
	private final Mapper mapper;
	
	//return view product
	@GetMapping(value = "/list")
	public String getCategoryView() {
		return "admin/product";
	}	
	@GetMapping(value = "/order")
	public String get() {
		return "users/order";
	}
	private static String UPLOADED_FOLDER = System.getProperty("user.dir") + "//src//main//resources//static//upload//";
	
	@PostMapping(value="/add", consumes = "multipart/form-data" ,
            produces = { "application/json", "application/xml" })
	@Transactional
	@ResponseBody
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile[] files,@ModelAttribute  ProductRequestDto productRequestDto, 
	                     HttpSession session) {
		System.out.println("title"+productRequestDto.getTitle());
		System.out.println("CategoryId"+productRequestDto.getCategoryId());
		System.out.println("price"+productRequestDto.getPrice());
		System.out.println("Current price"+productRequestDto.getCurrentPrice());
		System.out.println("Descrip"+productRequestDto.getDescription());
		System.out.println("Descrip"+productRequestDto.getSizeAttributeValue());
		System.out.println("Descrip"+productRequestDto.getColorAttributeValue());
		System.out.println("Descrip"+productRequestDto.getDescription());
		
		//product
		Product product = mapper.productRequestDtoToProduct(productRequestDto);
		productService.save(product);
		
		//image
	    for (int i = 0; i < files.length; i++) {
	        MultipartFile file = files[i];
	        String nameForShow = file.getOriginalFilename();  
//	        String nameForShow = namesForShow[i];
	        if (file.isEmpty()) {
	        	System.out.println(1);
	            continue;
	        }
	        try {
	            // Lưu ảnh vào thư mục tạm thời
	            byte[] bytes = file.getBytes();
	            System.out.println(2);
	            String randomFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(file.getOriginalFilename());
	            Path dir = Paths.get(UPLOADED_FOLDER);
	            Path path = Paths.get(UPLOADED_FOLDER + randomFileName);
	            if(!Files.exists(dir)) {
	            	System.out.println(3);
	                Files.createDirectories(dir);
	            }
	            Files.write(path, bytes);
	            System.out.println(4);
	            // Tạo đối tượng Image và lưu xuống cơ sở dữ liệu
//	            Optional<Product> p = productService.getCategory(1);
//	            if(p.isEmpty()) {
//	            	System.out.println(5-1);
//	            	return ResponseEntity.badRequest().body("lưu ảnh thất bại 2");
//	            }
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
	            return ResponseEntity.badRequest().body("lưu ảnh thất bại 2");
	        }
	    }
	    return ResponseEntity.ok().body("lưu ảnh thành công");
	}




	
}
