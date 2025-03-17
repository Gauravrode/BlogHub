package com.acts.blog;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.acts.entity")
@ComponentScan(basePackages = {"com.acts.service","com.acts.serviceImplementation", "com.acts.controllers", "com.acts.repositories","com.acts.config","com.acts.security"})
@EnableJpaRepositories("com.acts.repository")  
public class BlogApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
