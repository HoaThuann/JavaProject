package com.web.clothes.ClothesWeb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.entity.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
	public Optional<Product> getProductById(Integer productId);
}
