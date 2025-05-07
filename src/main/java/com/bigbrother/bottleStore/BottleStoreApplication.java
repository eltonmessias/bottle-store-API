package com.bigbrother.bottleStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class BottleStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(BottleStoreApplication.class, args);
	}

}
