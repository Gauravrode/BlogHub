package com.acts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.acts.security.JwtAuthenticationFilter;
import com.acts.security.JwtAuthenticationEntryPoint;
import com.acts.service.UserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailService userDetailService;
	
	@Autowired
	private JwtAuthenticationFilter filter;
	
	@Autowired
	private JwtAuthenticationEntryPoint point;
	
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/api/users/register","/auth/login").permitAll() // Allow register API without authentication
                    .anyRequest().authenticated() // Secure all other APIs
                )
            .httpBasic(Customizer.withDefaults())     //to enable just api access we can use it for postman
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
             http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider()
    {
    	DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
    	daoAuthenticationProvider.setUserDetailsService(userDetailService);
    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
    	
    	return daoAuthenticationProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder()
    {
    	return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
    
}
