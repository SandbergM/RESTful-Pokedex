package com.example.Pokedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ResTfulPokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResTfulPokedexApplication.class, args);
		System.out.println("Server running on port 8080");
	}
}
