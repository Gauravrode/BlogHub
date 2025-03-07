package com.acts.service;

import java.util.List;

import com.acts.payloads.CategoryDto;


public interface CategoryService {

	//getCategoryById
	CategoryDto getCategory(Integer id);
	
	//createCategory
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
	
	//delete
	void deleteCategory(Integer id);
	
	//getAllCategory
	List<CategoryDto> getAllCategory();
	
}
