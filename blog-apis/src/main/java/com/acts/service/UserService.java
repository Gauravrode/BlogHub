package com.acts.service;

import java.util.List;

import com.acts.payloads.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user,Integer id);
	
	UserDto getUser(Integer id);
	
	void deleteUser(Integer id);
	
	List<UserDto> getAllUsers();

}
