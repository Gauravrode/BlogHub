package com.acts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.acts.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer>{

}
