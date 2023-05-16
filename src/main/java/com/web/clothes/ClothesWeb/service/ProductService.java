package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Product;

public interface ProductService {
	public Optional<Product> getCategory(Integer prodcutId);
	public void save(Product product);
}
