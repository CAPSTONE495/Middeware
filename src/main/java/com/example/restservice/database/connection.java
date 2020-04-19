package com.example.restservice.database;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class connection {
	public @Bean MongoTemplate connector(){
		MongoClient conn = MongoClients.create("mongodb+srv://nmolina:OMYTXMcswUTHvdFd@cluster0-v76zg.mongodb.net/test?retryWrites=true&w=majority");
		MongoDbFactory connection =  new SimpleMongoClientDbFactory(conn,"Capstone");
		MongoTemplate mongoTemplate = new MongoTemplate(connection);
		return mongoTemplate;	
	}
}
