package com.posapp.pos_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PosApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosApiApplication.class, args);
		System.out.println("Berhasil terhubung ke server di port 8081!");
	}

}
