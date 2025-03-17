package com.acts.security;

import com.acts.payloads.UserDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class JwtResponse {

	private String jwtToken;
	private UserDto user;
}
