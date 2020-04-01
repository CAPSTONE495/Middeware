package com.example.restservice;

import com.example.restservice.Constants.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@SpringBootApplication
public class RestServiceApplication {

	public static void main(String[] args) {



		SpringApplication.run(RestServiceApplication.class, args);
	}

}
