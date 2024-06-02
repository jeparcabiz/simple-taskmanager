package com.jeparca.taskmanager.application.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jeparca.taskmanager.application.port.in.CreateTaskUseCase;
import com.jeparca.taskmanager.application.port.in.DeleteTaskUseCase;
import com.jeparca.taskmanager.application.port.in.ListTasksUseCase;
import com.jeparca.taskmanager.application.port.out.TaskRepositoryPort;
import com.jeparca.taskmanager.domain.model.Task;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TaskService implements CreateTaskUseCase, ListTasksUseCase, DeleteTaskUseCase {

	private final TaskRepositoryPort taskRepository;
	
	@Override
	public List<Task> listTasks() {
		return taskRepository.findAll();
	}

	@Override
	public Task createTask(Task task) {
		return taskRepository.save(task);
	}
	
	@Override
	public List<Task> getTasksFilterByDueDateLessThan(LocalDate dueDate) {
		return taskRepository.findByDueDateLessThan(dueDate);
	}
	
	@Override
	public void deleteAllTasks(List<Task> tasks) {
		tasks.forEach(task -> taskRepository.delete(task));
	}

}
