# CLI-Agenda

CLI-Agenda is a console application written in Java that allows you to manage tasks, notes, and events directly from the terminal.

---

## Features

### Tasks
- Create, list, search, update, and delete tasks
- Filter tasks by status: all, incomplete, completed
- Each task can be marked as completed/incomplete
- Tasks can be associated with an event

### Notes
- Create, list, search, update, and delete notes
- Notes must be associated with an existing task
- When creating a note, the list of available tasks is displayed

### Events
- Create, list, search, update, and delete events
- Date format validation with retry on error
- Events can be recurring (monthly or annually)
- When searching for an event, associated tasks are displayed

---

## Prerequisites

- Java 17 or higher
- Docker and Docker Compose
- Maven 3.x

---

## Usage Instructions

### 1. Build the project

```bash
mvn clean package
```

This will generate the file `target/CLI-agenda-1.0-jar-with-dependencies.jar`.

### 2. Start the MySQL database

```bash
docker compose up -d
```

The MySQL container will start on port 3306 with data persistence.

### 3. Run the application

```bash
java -jar target/CLI-agenda-1.0-jar-with-dependencies.jar
```

### 4. Stop the database (optional)

```bash
docker compose down
```

---

## Technologies Used

- Java 17 — main language
- JDBC — database connection
- Docker — database deployment
- Maven — dependency management
- JUnit 5 — unit testing
- MySQL 8.0 — database

---

## Architecture

```
CLI (input/output)
    ↓
DTO (request validation in domain package)
    ↓
Service (business logic + formatting)
    ↓
Repository (CRUD + SqlConnection)
    ↓
SqlConnection (singleton connection)
    ↓
Database (MySQL)
```

### Design Patterns
- **Singleton**: SqlConnection, Repositories
- **Repository**: Data access abstraction

### SOLID Principles
- **S**ingle Responsibility: Each layer has one purpose
- **O**pen/Closed: Extend services, not modify
- **L**iskov Substitution: Interfaces for Repositories
- **I**nterface Segregation: Small, focused interfaces
- **D**ependency Inversion: Services depend on Repository interfaces

---

## Project Structure

```
src/
├── main/java/com/itacademy/cliagenda/
│   ├── application/          # Entry point and menu
│   ├── event/                # Event management
│   │   ├── model/           # Event entity
│   │   ├── dto/             # Request DTOs with validation
│   │   ├── repository/      # EventRepository + IEventRepository
│   │   ├── service/         # EventService (validation + formatting)
│   │   └── cli/              # EventCli (input/output)
│   ├── task/                 # Task management
│   │   ├── model/           # Task entity
│   │   ├── dto/             # Request DTOs with validation
│   │   ├── repository/      # TaskRepository + ITaskRepository
│   │   ├── service/         # TaskService
│   │   └── cli/              # TaskCli
│   ├── note/                 # Note management
│   │   ├── model/           # Note entity
│   │   ├── dto/             # Request DTOs with validation
│   │   ├── repository/      # NotesRepository + INotesRepository
│   │   ├── service/         # NotesService
│   │   └── cli/              # NoteCli
│   ├── infrastructure/       # Data access
│   │   └── sql/             # SqlConnection (singleton)
│   └── common/              # Shared utilities
│       └── exception/       # Custom exceptions
├── test/                     # Tests
│   ├── java/                # Test sources
│   └── resources/           # Test resources (schema.sql)
└── doc/                      # Documentation
```

---

## Running Tests

### Unit Tests Only

```bash
mvn test
```

### Integration Tests (requires Docker)

```bash
# Start test database first
docker compose -f docker-compose.test.yml up -d

# Run all tests including integration (use -Dintegration-tests to activate)
mvn test -Dintegration-tests

# Stop test database
docker compose -f docker-compose.test.yml down
```

---

## Working Branches

- `main` — stable branch, ready for production
- `dev` — development branch where features are integrated
- `feature/*` / `docs/*` / `chore/*` — individual working branches

Each feature is developed on its own branch and integrated into `dev` through Pull Requests reviewed by the team.

---

## COPYRIGHT and LICENSE

Copyright 2026 Ulises Lafuente, Daniel Vila

CLI-Agenda is free software; you can redistribute it and/or modify it under the terms of the Apache License 2.0.

CLI-Agenda is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the Apache License 2.0 for more details.