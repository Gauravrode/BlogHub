package com.acts.service;
import com.acts.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto comment,Integer postId);
	
	void deleteComment(Integer commentId);

}
