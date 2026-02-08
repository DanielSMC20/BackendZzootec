package com.zzootec.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class ZzootecAdminBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZzootecAdminBackendApplication.class, args);
	}

}
