package com.jeparca.taskmanager.infrastructure.adapter.out;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jeparca.taskmanager.application.port.out.TaskRepositoryPort;
import com.jeparca.taskmanager.domain.model.Task;

@Repository
public interface MongoTaskRepository extends MongoRepository<Task, String>, TaskRepositoryPort {
}
