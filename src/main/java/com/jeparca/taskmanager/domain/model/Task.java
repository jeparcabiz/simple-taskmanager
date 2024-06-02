package com.jeparca.taskmanager.domain.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Task {

	@NotBlank(message = "It's mandatory a title for task")
	private String title;
	@NotBlank(message = "It's mandatory a description for task")
	private String description;
	@NotNull(message = "It's mandatory a due date for task")
	@Future(message = "Due date must be a future moment")
	private LocalDate dueDate;
	private List<String> tags;

	public Task(String title, String description, LocalDate dueDate, List<String> tags) {
		super();
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.tags = tags;
	}

}
