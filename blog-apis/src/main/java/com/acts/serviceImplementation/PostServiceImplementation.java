package com.acts.serviceImplementation;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.acts.entity.Category;
import com.acts.entity.Post;
import com.acts.entity.User;
import com.acts.exception.ResourceNotFoundException;
import com.acts.payloads.PostDto;
import com.acts.payloads.PostResponse;
import com.acts.repository.CategoryRepo;
import com.acts.repository.PostRepo;
import com.acts.repository.UserRepo;
import com.acts.service.PostService;

@Service
public class PostServiceImplementation implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo; 

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","id",userId));
		
		Post post=this.modelMapper.map(postDto, Post.class);
		post.setImageURL("default.png");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post updatedPost=this.postRepo.save(post);
		
		return this.modelMapper.map(updatedPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageURL(postDto.getImageURL());
		Post updatePost=this.postRepo.save(post);
		PostDto updatedPostDto=this.modelMapper.map(updatePost,PostDto.class);
		
		return updatedPostDto;
	}


	@Override
	public PostDto getPost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		PostDto postDto=this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		
		Sort sort=null;
		if(sortDir.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else {
			sort=Sort.by(sortBy).descending();
		}
		
		Pageable p=PageRequest.of(pageNumber,pageSize,sort);
		
		Page<Post> pagePost =this.postRepo.findAll(p);
		List<Post> posts=pagePost.getContent();
		
		List<PostDto> postsDto=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse=new PostResponse();
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setContent(postsDto);
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","category id",categoryId));
		List<Post> posts=this.postRepo.findByCategory(category);
		List<PostDto> postsDto=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
		List<Post> posts=this.postRepo.findByUser(user);
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts=this.postRepo.searchPosts(keyword);
		List<PostDto> postsDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDto;
	}

	

}
