package com.jeparca.taskmanager.application.port.in;

import java.time.LocalDate;
import java.util.List;

import com.jeparca.taskmanager.domain.model.Task;

public interface ListTasksUseCase {

	List<Task> listTasks();
	List<Task> getTasksFilterByDueDateLessThan(LocalDate dueDate);
	
}
