package com.flab.quicktogether;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class QuickTogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuickTogetherApplication.class, args);
	}

}
