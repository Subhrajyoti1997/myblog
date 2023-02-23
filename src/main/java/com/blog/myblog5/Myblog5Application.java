package com.blog.myblog5;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Myblog5Application {
	@Bean
	public ModelMapper modelMapper(){

		return new ModelMapper();
	}

	public static void main(String[] args) {

		SpringApplication.run(Myblog5Application.class, args);
	}

}
