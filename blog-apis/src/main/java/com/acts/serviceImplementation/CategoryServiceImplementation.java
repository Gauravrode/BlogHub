package com.acts.serviceImplementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acts.entity.Category;
import com.acts.exception.ResourceNotFoundException;
import com.acts.payloads.CategoryDto;
import com.acts.repository.CategoryRepo;
import com.acts.service.CategoryService;

@Service
public class CategoryServiceImplementation implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
		
	@Override
	public CategoryDto getCategory(Integer id) {
		Category category=this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
		CategoryDto categoryDto=CategoryToDto(category);
		return categoryDto;
	}

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
	  Category category=dtoToCategory(categoryDto);
	  Category createdCategory=this.categoryRepo.save(category);
	  return CategoryToDto(createdCategory);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer id) {
		Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Category","id",id));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		
		Category savedCategory=this.categoryRepo.save(category);
		return CategoryToDto(savedCategory);
	}

	@Override
	public void deleteCategory(Integer id) {
		this.categoryRepo.deleteById(id);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> categories=this.categoryRepo.findAll();
		List<CategoryDto> categoryDto=categories.stream().map((category)->this.CategoryToDto(category)).collect(Collectors.toList());
		return categoryDto;
	}
	
	
	public Category dtoToCategory(CategoryDto categoryDto)
	{
       Category category=this.modelMapper.map(categoryDto, Category.class);
       return category;		
	}
	
	public CategoryDto CategoryToDto(Category category)
	{
		CategoryDto categoryDto =this.modelMapper.map(category,CategoryDto.class);	
		return categoryDto;
	}

}

