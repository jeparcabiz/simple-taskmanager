package com.jeparca.taskmanager.application.port.in;

import java.util.List;

import com.jeparca.taskmanager.domain.model.Task;

public interface DeleteTaskUseCase {

	void deleteAllTasks(List<Task> tasks);
	
}
