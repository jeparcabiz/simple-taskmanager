# Task Manager

## Description

Task management application, developed using Spring Boot, using Hexagonal Architecture and DDD. This app use MongoDB database and Docker to configure developer environment.

## Requirements

- Docker
- Docker Compose
- Java 17
- Maven

## Configuration

1. Clone the repository

```bash
    git clone https://github.com/jeparcabiz/simple-taskmanager.git
    cd simple-taskmanager
```

2. Build Docker image and run containers

```bash
    docker-compose up --build
```

3. Application is running in `http://localhost:8080`

## Run tests

To execute unit and acceptance tests, use next command:

```bash
    mvn clean test
```

## Endpoints

* `POST /tasks`: Creates a new task. You have to use a JSON body with next mandatory information: title and future dueDate. Complete JSON body is shown in the next line:
```json
{
    "title": "Test title",
    "description": "Test description",
    "dueDate": "2025-06-02",
    "tags": [
        "tag1",
        "tag2"
    ]
}
```

* `GET /tasks`: Lists all tasks.

## Others

A scheduled service has been included to remove expired tasks based on due date.