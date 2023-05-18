package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Product;

public interface ProductService {
	public Optional<Product> getProduct(Integer prodcutId);
	public Optional<Product> getProductByTitle(String title);
	public Optional<Product> save(Product product);
}
