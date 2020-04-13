package com.example.restservice;

import com.example.restservice.Constants.Constants;
import com.example.restservice.database.Database;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootApplication
public class RestServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	//test shit here
	@Override
	public void run(String... args) throws Exception {

	}

	/*private void convert(String string){
		String newString = string.substring(0,string.length()-1).substring(1).replaceAll("\\s","");
		String answer = "";
		ArrayList<String> rs = new ArrayList<>();
		ArrayList<ArrayList<String>> frs = new ArrayList<>();
		boolean inSet = false;
		for(int i=0;i<newString.length();i++){
			char c = newString.charAt(i);
			if(c=='['){
				rs = new ArrayList<>();
				inSet=true;
			}else if(c==']'){
				rs.add(answer);
				answer="";
				inSet=false;
				frs.add(rs);
			}else if(c==','&&inSet){
				rs.add(answer);
				answer="";
			}else if(c!=','){
				answer=answer+c;
			}
		}

		System.out.print(rs.toString());
	}*/
}
