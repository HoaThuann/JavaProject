package com.web.clothes.ClothesWeb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Product;
import com.web.clothes.ClothesWeb.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Optional<Product> getProduct(Integer productId) {
		Optional<Product> product = productRepository.getProductById(productId);
		return product;
	}

	@Override
	public Optional<Product>  save(Product product) {
		return productRepository.saveProduct(product);
		 
	}


	@Override
	public Optional<Product> getProductByTitle(String title) {
		Optional<Product> product = productRepository.getProductByTitle(title);
		return product;
	}
}
