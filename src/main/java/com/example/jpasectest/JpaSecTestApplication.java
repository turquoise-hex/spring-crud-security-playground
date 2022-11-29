package com.example.jpasectest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JpaSecTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaSecTestApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

}
