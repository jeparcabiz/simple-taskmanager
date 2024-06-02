package com.jeparca.taskmanager.domain.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import com.jeparca.taskmanager.domain.model.Task;

@ActiveProfiles("test")
@DataMongoTest
public class TaskRepositoryTest {

	private static final String MONGODB_VERSION = "mongo:7.0.11";

	@Container
	@ServiceConnection
	private static MongoDBContainer database;

	static {
		database = new MongoDBContainer(MONGODB_VERSION);
		database.start();
	}

	@Autowired
	private TaskRepository taskRepository;

	@AfterEach
	public void tearDown() {
		taskRepository.deleteAll();
	}

	@Test
	public void testSave() {
		Task task = new Task("Test Task", "Test Description", LocalDate.now(), List.of("tag1", "tag2"));
		Task savedTask = taskRepository.save(task);

		assertNotNull(savedTask);
		assertEquals(task.getTitle(), savedTask.getTitle());
		assertEquals(task.getDescription(), savedTask.getDescription());
		assertEquals(task.getDueDate(), savedTask.getDueDate());
		assertEquals(task.getTags(), savedTask.getTags());
	}

	@Test
	public void testFindAll() {
		Task task1 = new Task("Test Task 1", "Test Description 1", LocalDate.now(), List.of("tag1", "tag2"));
		Task task2 = new Task("Test Task 2", "Test Description 2", LocalDate.now().plusDays(1),
				List.of("tag3", "tag4"));

		taskRepository.save(task1);
		taskRepository.save(task2);

		List<Task> tasks = taskRepository.findAll();

		assertEquals(2, tasks.size());
	}

	@Test
	public void testDeleteAll() {
		Task task1 = new Task("Test Task 1", "Test Description 1", LocalDate.now(), List.of("tag1", "tag2"));
		Task task2 = new Task("Test Task 2", "Test Description 2", LocalDate.now().plusDays(1), null);

		taskRepository.save(task1);
		taskRepository.save(task2);

		taskRepository.deleteAll();

		List<Task> tasks = taskRepository.findAll();

		assertTrue(tasks.isEmpty());
	}

	@Test
	public void testFindByDueDateLessThan() {
		Task task1 = new Task("Test Task 1", "Test Description 1", LocalDate.now(), null);
		Task task2 = new Task("Test Task 2", "Test Description 2", LocalDate.now().plusDays(5),
				List.of("tag1", "tag2"));

		taskRepository.save(task1);
		taskRepository.save(task2);

		List<Task> tasks = taskRepository.findByDueDateLessThan(LocalDate.now().plusDays(2));

		assertEquals(1, tasks.size());
		assertEquals(task1.getTitle(), tasks.get(0).getTitle());
	}

	@Test
	public void testDeleteAllById() {
		Task task1 = new Task();
		task1.setTitle("Test Task 1");
		task1.setDescription("Test Description 1");
		task1.setDueDate(LocalDate.now());

		Task task2 = new Task();
		task2.setTitle("Test Task 2");
		task2.setDescription("Test Description 2");
		task2.setDueDate(LocalDate.now().plusDays(5));
		task2.setTags(List.of("tag1", "tag2"));

		Task savedTask1 = taskRepository.save(task1);
		Task savedTask2 = taskRepository.save(task2);

		List.of(savedTask1, savedTask2).forEach(task -> taskRepository.delete(task));

		List<Task> tasks = taskRepository.findAll();

		assertTrue(tasks.isEmpty());
	}

}
