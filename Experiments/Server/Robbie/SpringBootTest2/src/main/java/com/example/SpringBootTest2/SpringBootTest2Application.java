package com.example.SpringBootTest2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.example.SpringBootTest2")
@EntityScan("com.example.SpringBootTest2")
@SpringBootApplication
public class SpringBootTest2Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootTest2Application.class, args);
	}

}
