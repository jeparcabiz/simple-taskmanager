package com.jeparca.taskmanager.infrastructure.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class AbstractMongoDbTestContainer {
	
	private static final String MONGODB_VERSION = "mongo:7.0.11";
	
	@Container
	@ServiceConnection
	private static MongoDBContainer database;
	
	static {
		database = new MongoDBContainer(MONGODB_VERSION);
		database.start();
	}

}
