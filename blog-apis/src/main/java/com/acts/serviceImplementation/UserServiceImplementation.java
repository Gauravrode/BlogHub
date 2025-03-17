package com.acts.serviceImplementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acts.entity.User;
import com.acts.exception.ApiException;
import com.acts.exception.ResourceNotFoundException;
import com.acts.payloads.UserDto;
import com.acts.repository.UserRepo;
import com.acts.service.UserService;

@Service
public class UserServiceImplementation implements UserService {

	   @Autowired
	    private UserRepo userRepo;

	    @Autowired
	    private ModelMapper modelMapper;
	    
	    @Autowired
	    private PasswordEncoder passwordEncoder; 

	    public UserDto createUser(UserDto userDto) {
	        Optional<User> existingUser = userRepo.findByEmail(userDto.getEmail());
	        
	        if (existingUser.isPresent()) {
	        	throw new ApiException("Email already in use: " + userDto.getEmail());
	        }

	        User user = this.dtoToUser(userDto);
	        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
	        User savedUser = this.userRepo.save(user);

	        return this.userToDto(savedUser);
	    }


	    public UserDto updateUser(UserDto userDto, Integer id) {
	        User user = this.userRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

	        user.setName(userDto.getName());
	        user.setEmail(userDto.getEmail());
	        user.setAbout(userDto.getAbout());
	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	        User updatedUser = this.userRepo.save(user);
	        return this.userToDto(updatedUser);
	    }

	    public UserDto getUser(Integer id) {
	        User user = this.userRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	        return this.userToDto(user);
	    }

	    public void deleteUser(Integer id) {
	        User user = this.userRepo.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	        this.userRepo.delete(user);
	    }

	    public List<UserDto> getAllUsers() {
	        List<User> users = this.userRepo.findAll();
	        return users.stream().map(this::userToDto).collect(Collectors.toList());
	    }

	    public User dtoToUser(UserDto userDto) {
	        return this.modelMapper.map(userDto, User.class);
	    }

	    public UserDto userToDto(User user) {
	        return this.modelMapper.map(user, UserDto.class);
	    }


}
