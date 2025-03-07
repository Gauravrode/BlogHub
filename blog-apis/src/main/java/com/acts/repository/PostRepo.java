package com.acts.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.acts.entity.Category;
import com.acts.entity.Post;
import com.acts.entity.User;

public interface PostRepo extends JpaRepository<Post,Integer> {

	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	 @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
	 List<Post> searchPosts(String keyword);
	
}