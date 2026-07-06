# Todo Management System

## Overview

This is a simple Todo Management System built with Spring Boot following the MVC architecture. The application allows users to manage daily tasks with basic CRUD operations and additional features such as search and filter.

## Technologies

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- Thymeleaf
- Bootstrap 5
- MySQL
- Maven

## Features

- Create a new todo
- View all todos
- Update a todo
- Delete a todo
- Toggle todo status (Pending / Completed)
- Search todos by title
- Filter todos by status
- Form validation

## Project Structure

```
todoApp/
├── src/
│   ├── main/
│   │   ├── java/com/duongnvl/todoApp/
│   │   │   ├── config/                    # Configuration classes
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   └── WebConfig.java
│   │   │   ├── constant/                  # Application constants
│   │   │   │   └── AppConstant.java
│   │   │   ├── controller/                # REST Controllers
│   │   │   │   └── TodoController.java
│   │   │   ├── dto/                       # Data Transfer Objects
│   │   │   │   └── TodoRequest.java
│   │   │   ├── entity/                    # JPA Entities
│   │   │   │   ├── Todo.java
│   │   │   │   └── TodoStatus.java
│   │   │   ├── exception/                 # Custom exceptions
│   │   │   │   └── TodoNotFoundException.java
│   │   │   ├── repository/                # Data Access Layer
│   │   │   │   └── TodoRepository.java
│   │   │   ├── response/                  # API Response wrapper
│   │   │   │   └── ApiResponse.java
│   │   │   ├── service/                   # Business Logic Layer
│   │   │   │   ├── TodoService.java
│   │   │   │   └── impl/
│   │   │   │       └── TodoServiceImpl.java
│   │   │   ├── util/                      # Utility classes
│   │   │   │   └── DateTimeUtil.java
│   │   │   └── TodoAppApplication.java
│   │   ├── resources/
│   │   │   ├── application.yml            # Spring Boot configuration
│   │   │   ├── application.properties     # Legacy properties (can be removed)
│   │   │   ├── static/
│   │   │   │   └── style.css
│   │   │   └── templates/
│   │   │       └── index.html
│   └── test/
│       └── java/com/duongnvl/todoApp/
│           └── TodoAppApplicationTests.java
├── logs/                                   # Application logs
├── pom.xml                                 # Maven configuration
├── README.md                               # Project documentation
└── .gitignore                              # Git ignore file
```

## Architecture Layers

### 1. **Controller Layer** (`controller/`)

- Handles HTTP requests and responses
- Maps URLs to service methods
- Validates input data

### 2. **Service Layer** (`service/`)

- Contains business logic
- Handles data validation and transformation
- Manages transactions

### 3. **Repository Layer** (`repository/`)

- Data access abstraction
- JPA/Hibernate integration
- Database queries

### 4. **Entity Layer** (`entity/`)

- JPA entities mapping to database tables
- Defines data models and relationships

### 5. **DTO Layer** (`dto/`)

- Data Transfer Objects for API communication
- Decouples entity from presentation layer

### 6. **Exception Handling** (`exception/`, `config/GlobalExceptionHandler.java`)

- Custom business exceptions
- Centralized exception handling

## Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE todo_db;
```

Configure your database connection in `application.properties`.

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## Running the Project

Clone the repository:

```bash
git clone <repository-url>
```

Move to the project directory:

```bash
cd todoApp
```

Run the application:

```bash
mvn spring-boot:run
```

Open your browser:

```
http://localhost:8080
```
