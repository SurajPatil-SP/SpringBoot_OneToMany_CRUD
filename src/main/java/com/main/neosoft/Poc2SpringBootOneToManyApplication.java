package com.main.neosoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@EntityScan
@SpringBootApplication
public class Poc2SpringBootOneToManyApplication {

	public static void main(String[] args) {
		SpringApplication.run(Poc2SpringBootOneToManyApplication.class, args);
	}

}
