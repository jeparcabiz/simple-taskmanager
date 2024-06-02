package com.jeparca.taskmanager.application.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jeparca.taskmanager.application.port.in.DeleteExpiredTasksUseCase;
import com.jeparca.taskmanager.domain.model.Task;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExpiredTasksScheduledService implements DeleteExpiredTasksUseCase {

	private static final Logger log = LoggerFactory.getLogger(ExpiredTasksScheduledService.class);
	private final TaskService taskService;

	@Scheduled(cron = "0 0 0 * * *", zone = "Europe/Madrid")
	public void deleteExpiredTasks() {
		LocalDate now = LocalDate.now();
		log.info("Removing expired tasks for date less than {}", now);
		deleteExpiredTasks(now);
	}

	@Override
	public void deleteExpiredTasks(LocalDate dueDate) {
		List<Task> expiredTasks = taskService.getTasksFilterByDueDateLessThan(dueDate);
		taskService.deleteAllTasks(expiredTasks);
	}
}