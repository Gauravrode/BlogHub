package com.acts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acts.payloads.ApiResponse;
import com.acts.payloads.CommentDto;
import com.acts.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
 
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId)
	{
	 CommentDto createdComment= this.commentService.createComment(commentDto, postId);
	 return new ResponseEntity<>(createdComment,HttpStatus.CREATED);
	 
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> createComment(@PathVariable Integer commentId)
	{
	 this.commentService.deleteComment(commentId);
	 return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Deleted",true),HttpStatus.OK);
	}
	
	
}