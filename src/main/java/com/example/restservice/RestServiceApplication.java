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

		//if(!loadData()){return;}

		SpringApplication.run(RestServiceApplication.class, args);
	}

	private static boolean loadData(){


		try {
			Constants.DatabaseConstants.loadDatabaseCredials();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (SAXException e) {
			e.printStackTrace();
			return false;
		}




		return true;
	}

}
