package com.example.kottabi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class KottabiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KottabiApplication.class, args);
	}

}
