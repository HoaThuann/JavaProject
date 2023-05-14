package com.web.clothes.ClothesWeb.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.web.clothes.ClothesWeb.entity.Attribute;
import com.web.clothes.ClothesWeb.entity.AttributeValue;
import com.web.clothes.ClothesWeb.entity.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{
	public Optional<Category> getCategoryById(Integer categoryId);

	public Optional<Category> getCategoryByCategoryNameIgnoreCase(String categoryName);
	
	@Query("SELECT c FROM Category c WHERE c.deleted = 0")
    Page<Category> findAll(Pageable pageable);
}
