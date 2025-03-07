package com.acts.serviceImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acts.entity.Comment;
import com.acts.entity.Post;
import com.acts.exception.ResourceNotFoundException;
import com.acts.payloads.CommentDto;
import com.acts.repository.CommentRepo;
import com.acts.repository.PostRepo;
import com.acts.service.CommentService;

@Service
public class CommentServiceImplementation implements CommentService{
  
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto,Integer postId) {
		
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","post id",postId));
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		this.commentRepo.save(comment);
		
		return this.modelMapper.map(comment,CommentDto.class);
	}
	@Override
	public void deleteComment(Integer commentId) {
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Id",commentId));
		this.commentRepo.delete(comment);
	}
	
}
