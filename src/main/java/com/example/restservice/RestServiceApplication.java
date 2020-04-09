package com.example.restservice;

import com.example.restservice.Constants.Constants;
import com.example.restservice.database.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class RestServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	//test shit here
	@Override
	public void run(String... args) throws Exception {
		Constants.APIKeyMapper.addAPIKey("fasfasd@klfsfls","985269842dafasdsaf");
		System.out.println(Constants.APIKeyMapper.checkAPIKey("fasfasd@klfsfls","985269842dafasdsaf"));
	}
}
