package com.flag.flag_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
@EnableCaching
public class FlagServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlagServiceApplication.class, args);
	}

}
