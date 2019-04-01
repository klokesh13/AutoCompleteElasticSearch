package com.org.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BootElasticSearchApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(BootElasticSearchApplication.class, args);
		
	}

}
