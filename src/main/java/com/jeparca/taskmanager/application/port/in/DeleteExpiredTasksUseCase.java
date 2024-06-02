package com.jeparca.taskmanager.application.port.in;

import java.time.LocalDate;

public interface DeleteExpiredTasksUseCase {

	void deleteExpiredTasks(LocalDate dueDate);
	
}
