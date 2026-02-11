# Employee API – Spring Boot Version

A RESTful API built with **Spring Boot** as the evolution of the pure Java (Servlet + JDBC) version of this project.

This version demonstrates how enterprise backend systems can be simplified and standardized using Spring Boot while preserving clean architecture principles.

> 🚀 This is the **Spring Boot** version.
> Check out the **Core Java (Servlet + JDBC)** version here:
> [EmployeeAPI-Core](https://github.com/MatheusLeiteCarneiro/EmployeeAPI)

---

## 🎯 Purpose

After building the entire backend infrastructure manually in the Core version, this project was created to:

* Understand what Spring abstracts under the hood
* Compare manual architecture vs framework-driven architecture
* Apply best practices using Spring Boot ecosystem
* Implement production-ready configuration using profiles

This version focuses on **productivity, maintainability, and standardization** without sacrificing architectural clarity.

---

## 📌 Project Overview

* **Architecture:** Controller + Service + Repository (Spring Data JPA)
* **Validation:** Bean Validation (Jakarta Validation)
* **Profiles:** `dev` and `prod`
* **Database (Dev):** H2 In-Memory
* **Database (Prod):** PostgreSQL
* **Documentation:** Swagger / OpenAPI
* **Testing:** Integration Testing with MockMvc
* **Build Tool:** Maven

---

## ✨ Features

### ✅ CRUD Operations

Full employee lifecycle:

* Create
* Read
* Update
* Delete

Built using Spring MVC + Spring Data JPA.

---
### ✅ Global Exception Handling

Exception handling is centralized using `@ControllerAdvice`, ensuring:

* Standardized error responses
* Consistent HTTP status codes
* Clean controller logic

This replaces the manual `Filter` approach implemented in the Core version.

---

### ✅ Pagination

Endpoint:

```
GET /employee?page=0&size=10
```

Handled natively using:
* `Pageable`
* `Page<EmployeeDTO>`
* Automatic SQL generation via JPA

No manual `LIMIT` / `OFFSET` required.

---

### ✅ Bean Validation

Input validation is handled declaratively using annotations:

* `@NotBlank`
* `@NotNull`
* `@Positive`


Validation occurs automatically at the **Controller layer**, eliminating manual service validation logic previously implemented in the Core version.

Example validation error response:

```json
{
  "timestamp": "2026-02-11T22:23:26.794859474Z",
  "status": 422,
  "error": "Invalid Data",
  "path": "/app/employee",
  "errors": [
    {
      "fieldName": "name",
      "message": "Name can't be blank"
    }
  ]
}
```

---

### ✅ Profiles (Dev & Prod)

The application supports environment-based configuration:

#### 🔹 `dev`

* H2 in-memory database
* Fast startup
* Ideal for development and testing

#### 🔹 `prod`

* PostgreSQL
* Production-ready configuration

Profile configuration is handled via:

```
application-dev.properties
application-prod.properties
```

This ensures proper separation between development and production environments.

---

### ✅ Swagger / OpenAPI Documentation

API documentation is automatically generated using SpringDoc OpenAPI.

Available at:

```
http://localhost:8080/swagger-ui.html
```

Features:

* Interactive endpoint testing
* Request/response schema visualization
* Automatic documentation generation

---

### ✅ Lombok

Lombok is used to reduce boilerplate:

* `@Getter`
* `@Setter`
* `@Builder`
* `@AllArgsConstructor`
* `@NoArgsConstructor`

This keeps the code clean and focused on business logic.

---

## 🧪 Testing Strategy

Unlike the Core version (which used unit tests + mocked layers), this version focuses on:

### Integration Testing with MockMvc

* Tests the full HTTP request lifecycle
* Loads Spring context
* Validates controller behavior
* Ensures correct status codes and JSON responses

Why?

Because the Service layer is intentionally **streamlined**, with most validation delegated to:

* Bean Validation
* Spring MVC
* JPA

Therefore, integration testing provides higher confidence with less redundant mocking.

---

## 🛠️ Technologies Used

* Java 25
* Spring Boot
* Spring Web
* Spring Data JPA
* Bean Validation (Jakarta Validation)
* PostgreSQL
* H2 Database
* SpringDoc OpenAPI (Swagger)
* Lombok
* Maven
* JUnit 5
* MockMvc
* Git & GitHub

---

## 🧠 What This Project Demonstrates

This project shows understanding of:

* Spring Boot auto-configuration
* Dependency injection
* REST controllers
* JPA abstraction over JDBC
* Profile-based configuration
* Declarative validation
* Integration testing with MockMvc
* Framework-driven architecture


It also demonstrates the ability to compare:

| Core Version        | Spring Version            |
| ------------------- | ------------------------- |
| Manual Servlets     | Spring MVC                |
| Manual JDBC         | Spring Data JPA           |
| Manual Validation   | Bean Validation           |
| Global Filter       | Spring Exception Handling |
| Manual SQL          | JPA Query Generation      |
| Manual ObjectMapper | Automatic JSON conversion |

This evolution reflects architectural maturity and framework-level understanding.

---

## 🛠️ Configuration & Setup

### Prerequisites
* Java 25
* Maven
* PostgreSQL (only for prod profile)




### 1️⃣ Clone

```bash
git clone https://github.com/MatheusLeiteCarneiro/EmployeeAPI-Spring.git
```

---

### 2️⃣ Build

```bash
mvn clean install
```

---
### 3️⃣ Run

### (Dev Profile)

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Or configure in IDE:

```
spring.profiles.active=dev
```

---

### (Prod Profile)

Ensure PostgreSQL is running and configured correctly.

Before running with the prod profile, update your database credentials in: `application-prod.properties`

Then activate the profile:
```
spring.profiles.active=prod
```

---

## 📡 API Endpoints

| Method | Endpoint             | Description                           |
| ------ |----------------------| ------------------------------------- |
| GET    | `/app/employee`      | List employees (pagination supported) |
| GET    | `/app/employee/{id}` | Get employee by ID                    |
| POST   | `/app/employee`      | Create employee                       |
| PUT    | `/app/employee/{id}` | Update employee                       |
| DELETE | `/app/employee/{id}` | Delete employee                       |


Swagger for testing: http://localhost:8080/swagger-ui.html

---

### 📝 Sample JSON (POST / PUT)

```json
{
  "name": "Developer Name",
  "salary": 10000.00,
  "role": "JUNIOR",
  "hiringDate": "2024-02-01"
}
```

Available roles:

```
INTERN | JUNIOR | MID_LEVEL | SENIOR
```


---

## 👨‍💻 Author

**Matheus Leite Carneiro**

Backend Developer | Java | Spring Boot

