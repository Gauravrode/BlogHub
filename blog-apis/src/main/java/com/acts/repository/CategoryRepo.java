package com.acts.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.acts.entity.Category;


public interface CategoryRepo extends JpaRepository<Category,Integer>{

}
