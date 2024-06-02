package com.jeparca.taskmanager.infrastructure.adapter.in;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeparca.taskmanager.application.port.in.CreateTaskUseCase;
import com.jeparca.taskmanager.application.port.in.ListTasksUseCase;
import com.jeparca.taskmanager.domain.model.Task;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/tasks")
@Validated
@AllArgsConstructor
public class TaskController {

	private final ListTasksUseCase listTasksUseCase;
	private final CreateTaskUseCase createTaskUseCase;
	
	@PostMapping
	public Task createTask(@RequestBody @Valid Task task) {
		return createTaskUseCase.createTask(task);
	}
	
	@GetMapping
	public List<Task> listTasks() {
		return listTasksUseCase.listTasks();
	}
}
