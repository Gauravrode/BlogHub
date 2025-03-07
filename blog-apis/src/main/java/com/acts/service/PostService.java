package com.acts.service;

import java.util.List;
import com.acts.payloads.PostDto;
import com.acts.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto post,Integer postId,Integer categoryId);
	
	PostDto updatePost(PostDto post,Integer postId);
	
	PostDto getPost(Integer postId);
	
	void deletePost(Integer postId);
	
	PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);

	List<PostDto> getPostByCategory(Integer categoryId );
	
	List<PostDto> getPostByUser(Integer userId );
	
	List<PostDto> searchPosts(String keyword); 
}
