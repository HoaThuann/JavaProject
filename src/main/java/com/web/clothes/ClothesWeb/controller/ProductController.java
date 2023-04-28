package com.web.clothes.ClothesWeb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.web.clothes.ClothesWeb.service.ProductService;
@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
}
