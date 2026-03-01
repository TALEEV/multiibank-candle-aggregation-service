package com.candel.aggregation.service.multiibank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MultiibankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultiibankApplication.class, args);
	}

}
