package com.web.clothes.ClothesWeb.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	public void save(Product product) {
		productRepository.save(product);
		 
	}


	@Override
	public Optional<Product> getProductByTitle(String title) {
		Optional<Product> product = productRepository.getProductByTitle(title);
		return product;
	}

	@Override
	public Page<Product> getProductPage(int pageNumber, int szie) {
		PageRequest productPageable = PageRequest.of(pageNumber, szie, Sort.by(Sort.Direction.ASC, "title"));
		
		 Page<Product> productPage = productRepository.findProductPage(productPageable);
		 
		return productPage;
	}

	@Override
	public void delete(Product product) {
		product.setDeleteAt(LocalDate.now());
		productRepository.save(product);
		
	}
}
