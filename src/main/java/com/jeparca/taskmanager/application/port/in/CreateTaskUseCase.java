package com.jeparca.taskmanager.application.port.in;

import com.jeparca.taskmanager.domain.model.Task;

public interface CreateTaskUseCase {

	Task createTask(Task task);
	
}
