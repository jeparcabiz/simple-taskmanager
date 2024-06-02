package com.jeparca.taskmanager.domain.repository;

import java.util.List;

import com.jeparca.taskmanager.domain.model.Task;

public interface TaskRepository {

	Task save(Task task);
	List<Task> findAll();
	
}
