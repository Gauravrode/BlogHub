package com.acts.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.acts.entity.Category;
import com.acts.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {

	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageURL;
	
	private Date addedDate;
	
	private Category category;
	
	private User user;
	
	private Set<CommentDto> comments=new HashSet<>();
}
