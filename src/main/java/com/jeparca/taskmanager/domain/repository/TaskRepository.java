package com.jeparca.taskmanager.domain.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jeparca.taskmanager.domain.model.Task;

@Repository
public interface TaskRepository {

	Task save(Task task);
	List<Task> findAll();
	void deleteAll();
	
}
