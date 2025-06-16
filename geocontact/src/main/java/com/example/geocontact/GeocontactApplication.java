package com.example.geocontact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.geocontact.config.DotenvLoader;

@SpringBootApplication
public class GeocontactApplication {

	public static void main(String[] args) {
		DotenvLoader.load();
		SpringApplication.run(GeocontactApplication.class, args);
	}

}
