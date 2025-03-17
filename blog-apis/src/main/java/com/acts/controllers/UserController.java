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
import org.springframework.web.bind.annotation.RestController;

import com.acts.payloads.UserDto;
import com.acts.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	// POST create user
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		System.out.println(userDto.getEmail());
		System.out.println(userDto.getPassword());
		
		UserDto createUserdto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserdto, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Integer userId) {

		UserDto updatedUser = this.userService.updateUser(userDto, userId);

		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/getAll")
	public List<UserDto> getAll() {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{userId}")
	public UserDto getUser(@PathVariable Integer userId){
		return userService.getUser(userId);
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
		this.userService.deleteUser(userId);
		return ResponseEntity.ok("deleted");

	}

}
