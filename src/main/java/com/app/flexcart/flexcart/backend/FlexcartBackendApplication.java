package com.app.flexcart.flexcart.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class FlexcartBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlexcartBackendApplication.class, args);
	}

}
