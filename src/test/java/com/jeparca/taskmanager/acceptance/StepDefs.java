package com.jeparca.taskmanager.acceptance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jeparca.taskmanager.domain.model.Task;
import com.jeparca.taskmanager.domain.repository.TaskRepository;
import com.jeparca.taskmanager.infrastructure.config.AbstractMongoDbTestContainer;

import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class StepDefs extends AbstractMongoDbTestContainer {

	private final static String TASKS_ENDPOINT = "/tasks";
	
	@LocalServerPort
	private Integer port;
	
	@Autowired
	private TaskRepository taskRepository;
	
	private String title;
	private String description;
	private LocalDate dueDate;
	private List<String> tags;
	
	@Before
	public void setup() {
		RestAssured.baseURI = "http://localhost:" + port;
	}
	
	@After
	public void reset() {
		taskRepository.deleteAll();
	}
	
	@AfterAll
	public static void afterAll() {
	}
	
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
		Task task = new Task(title, description, dueDate, tags);
		given().contentType(ContentType.JSON).body(task).when().post(TASKS_ENDPOINT).then()
				.statusCode(HttpStatus.OK.value());
	}
	
	@Then("the task should be created with title {string}")
	public void task_should_be_created(String title) throws Exception {
		given().contentType(ContentType.JSON).when().get(TASKS_ENDPOINT).then().statusCode(HttpStatus.OK.value())
				.body("[0].title", equalTo(title));
	}
	
	@Given("there are tasks in system")
	public void tasks_in_system() {
		Task task1 = new Task("Test title 1", "Test description 1", LocalDate.now().plusDays(20), null);
		Task task2 = new Task("Test title 2", "Test description 2", LocalDate.now().plusDays(15),
				List.of("test1, test2, tag2"));
		Task task3 = new Task("Test title 3", "Test description 3", LocalDate.now().plusDays(10),
				List.of("test3, test2, tag1"));
		Task task4 = new Task("Test title 4", "Test description 4", LocalDate.now().plusDays(30), null);
		Task task5 = new Task("Test title 5", "Test description 5", LocalDate.now().plusDays(1),
				List.of("test1110, testing, tag"));
		taskRepository.save(task1);
		taskRepository.save(task2);
		taskRepository.save(task3);
		taskRepository.save(task4);
		taskRepository.save(task5);
	}
	
	@When("Customer lists all tasks")
	public void customer_lists_all_tasks() {}
	
	@Then("Customer should see a list of tasks")
	public void should_see_list_of_tasks() throws Exception {
		given().contentType(ContentType.JSON).when().get(TASKS_ENDPOINT).then().statusCode(HttpStatus.OK.value())
				.body(".", hasSize(5));
	}
	
}
