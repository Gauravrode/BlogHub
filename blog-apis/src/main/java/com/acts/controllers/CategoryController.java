package com.acts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acts.payloads.CategoryDto;
import com.acts.service.CategoryService;

@CrossOrigin
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	//create
	@PostMapping("/")
	ResponseEntity<CategoryDto> createCategory (@RequestBody CategoryDto categoryDto)
	{
		 CategoryDto newCategory= categoryService.createCategory(categoryDto);
	     return new ResponseEntity<>(newCategory,HttpStatus.CREATED);
	}
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {

		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(updatedCategory);
	}

	@GetMapping("/get")
	public List<CategoryDto> getAll() {
		return categoryService.getAllCategory();
	}
	
	@GetMapping("/{categoryId}")
	public CategoryDto getCategory(@PathVariable Integer categoryId){
		return categoryService.getCategory(categoryId);
	}

	@DeleteMapping("/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer categoryId) {
		this.categoryService.deleteCategory(categoryId);
		return ResponseEntity.ok("deleted");

	}

	
}
