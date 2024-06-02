package com.jeparca.taskmanager.infrastructure.adapter.in;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jeparca.taskmanager.application.port.in.CreateTaskUseCase;
import com.jeparca.taskmanager.application.port.in.ListTasksUseCase;
import com.jeparca.taskmanager.domain.model.Task;

@WebMvcTest(TaskController.class)
@ActiveProfiles("test")
public class TaskControllerTest {

	private final static String TASKS_ENDPOINT = "/tasks";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CreateTaskUseCase createTaskUseCase;
	@MockBean
	private ListTasksUseCase listTasksUseCase;

	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Test
	public void testCreateTask() throws Exception {
		Task task = new Task("Test Task", "Test Description", LocalDate.now().plusMonths(1), List.of("Test"));

		given(createTaskUseCase.createTask(task)).willReturn(task);

		mockMvc.perform(post(TASKS_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(task))).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Test Task")))
				.andExpect(jsonPath("$.description", is("Test Description")))
				.andExpect(jsonPath("$.dueDate", is(LocalDate.now().plusMonths(1).toString())))
				.andExpect(jsonPath("$.tags.length()", is(1)));
	}

	@Test
	public void testListTasks() throws Exception {
		Task task = new Task("Test Task", "Test Description", LocalDate.now().plusDays(5), List.of("Test"));

		List<Task> tasks = List.of(task);

		given(listTasksUseCase.listTasks()).willReturn(tasks);

		mockMvc.perform(get(TASKS_ENDPOINT).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().json(
						"[{\"id\":null,\"title\":\"Test Task\",\"description\":\"Test Description\",\"dueDate\":\""
								+ LocalDate.now().plusDays(5).toString() + "\",\"tags\":[\"Test\"]}]"));
	}
}