package com.web.clothes.ClothesWeb.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.web.clothes.ClothesWeb.entity.Category;
import com.web.clothes.ClothesWeb.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Optional<Category> getCategory(Integer categoryId) {
		Optional<Category> category = categoryRepository.getCategoryById(categoryId);
		return category;
	}

	@Override
	public void save(Category category) {
		categoryRepository.save(category);
		
	}

	@Override
	public void updateCategory(Category category) {
		categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Category category) {
		//set deleted =1
		category.setDeleted(1);
		//set date delete
		category.setDeleteAt(LocalDate.now());
		categoryRepository.save(category);
		
	}

	@Override
	public Optional<Category> findCategoryByName(String categoryName) {
		Optional<Category> category = categoryRepository.getCategoryByCategoryNameIgnoreCase(categoryName);
		return category;
	}

	@Override
	public Page<Category> getAllCategory(int pageNumber, int szie) {
		
		PageRequest categoryPageable = PageRequest.of(pageNumber, szie, Sort.by(Sort.Direction.ASC, "categoryName"));
		
		 Page<Category> categoryPage = categoryRepository.findAll(categoryPageable);
		 
		return categoryPage;
	}
}
