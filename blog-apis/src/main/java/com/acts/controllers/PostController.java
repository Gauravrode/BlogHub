package com.acts.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acts.config.AppConstants;
import com.acts.payloads.ApiResponse;
import com.acts.payloads.PostDto;
import com.acts.payloads.PostResponse;
import com.acts.service.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
    
	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId
			)
	{
		PostDto createdPost=this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(createdPost,HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser( @PathVariable Integer userId)
	{
		List<PostDto> postsDto=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsDto,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory( @PathVariable Integer categoryId)
	{
		List<PostDto> postsDto=this.postService.getPostByUser(categoryId);
		return new ResponseEntity<List<PostDto>>(postsDto,HttpStatus.OK);
	}
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto postDto=this.postService.getPost(postId);
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required=false) Integer pageNumber,
			@RequestParam(value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required=false) Integer pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required=false) String sortDir
			)
	{
		PostResponse postResponse= this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}
	
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ApiResponse("Post is successfully deleted",true);
	}
	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
			@PathVariable Integer postId)
	{
		PostDto updatedPost=this.postService.updatePost(postDto,postId);
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
	}
	
	//search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(
			@PathVariable("keywords") String keywords
			)
	{
      List<PostDto> postDto=this.postService.searchPosts(keywords);	
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	
	
	
	
	
}
