package com.example.FactoryApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class FactoryAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FactoryAppApplication.class, args);
	}

}
