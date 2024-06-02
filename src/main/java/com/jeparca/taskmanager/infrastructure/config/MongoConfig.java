package com.jeparca.taskmanager.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import lombok.AllArgsConstructor;

@ConfigurationProperties
@AllArgsConstructor
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Value("${spring.data.mongodb.database}")
	private String dbName;
	
	@Value("${spring.data.mongodb.uri}")
	private String dbUri;
	
	@Override
	protected String getDatabaseName() {
		return dbName;
	}
	
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(dbUri);
	}

}
