package com.example.Pokedex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ResTfulPokedexApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResTfulPokedexApplication.class, args);
		System.out.println("Server running on port 8080");
	}

}
