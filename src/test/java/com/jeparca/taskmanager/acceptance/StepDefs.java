package com.jeparca.taskmanager.acceptance;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StepDefs extends SpringIntegrationTest {

	private final static String TASKS_ENDPOINT = "/tasks";
	
	private final MockMvc mockMvc;
	
	private final ObjectMapper om = new ObjectMapper();
	
	private String title;
	private String description;
	private LocalDate dueDate;
	private List<String> tags;
	
	@Given("the task title is {string}")
	public void task_title_is(String title) {
		this.title = title;
	}
	
	@Given("the task description is {string}")
	public void task_description_is(String description) {
		this.description = description;
	}
	
	@Given("the task due date is {string}")
	public void task_due_date_is(String dueDate) {
		this.dueDate = LocalDate.parse(dueDate);
	}
	
	@Given("the task tags are {string}")
	public void task_tags_are(String tags) {
		this.tags = Arrays.asList(tags.split(", "));
	}
	
	@When("Customer creates a task")
	public void create_task() throws JsonProcessingException, Exception {
		// TODO: Waiting to create domain model
		Object task = null;
		mockMvc.perform(
				post(TASKS_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(om.writeValueAsString(task)))
			.andExpect(status().isOk());
	}
	
	@Then("the task should be created with title {string} and description {string} and due date {string} and tags {string}")
	public void task_should_be_created(String title, String description, String dueDate, String tags) throws Exception {
		mockMvc.perform(get(TASKS_ENDPOINT)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].title", equalTo(title)))
				.andExpect(jsonPath("$[0].description", equalTo(description)))
				.andExpect(jsonPath("$[0].dueDate", equalTo(LocalDate.parse(dueDate))))
				.andExpect(jsonPath("$[0].tags", equalTo(Arrays.asList(tags.split(", ")))));
	}
	
	@Given("there are tasks in system")
	public void tasks_in_system() {
		// TODO: Waiting to create domain 
	}
	
	@When("Customer lists all tasks")
	public void customer_lists_all_tasks() {}
	
	@Then("Customer should see a list of tasks")
	public void should_see_list_of_tasks() throws Exception {
		mockMvc.perform(get(TASKS_ENDPOINT)).andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(5));
	}
	
}
