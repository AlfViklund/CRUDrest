package com.arslek.CRUD3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class Crud3Application {

	public static void main(String[] args) {
		SpringApplication.run(Crud3Application.class, args);

	}

}
