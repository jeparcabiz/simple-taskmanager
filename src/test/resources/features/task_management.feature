Feature: Task management
	
	Scenario: Customer creates a new task
		Given the task title is "Test task"
		and the task description is "Test description"
		and the task due date is "2025-10-26"
		and the task tags are "test, tag1"
		When Customer creates a task
		Then the task should be created with title "Test task"
		and description "Test description"
		and due date "2025-10-26"
		and tags "test, tag1"
		
	Scenario: List all tasks
		Given there are tasks in system
		When Customer lists all tasks
		Then Customer should see a list of tasks	 