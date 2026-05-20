[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/Qr3lBpHw)
# University Management System - Lab 8

This project is a Spring Boot based university management system developed for WM2 Lab 8.

It consists of two microservices:
- student-service
- course-service

The system allows managing students, managing courses, enrolling students into courses, checking prerequisites, storing enrollment dates, and retrieving courses by student name.

## Technologies Used

- Java 21
- Spring Boot
- Gradle
- Spring Data JPA
- PostgreSQL
- Docker Compose
- OpenFeign
- RestTemplate
- Swagger / OpenAPI

## How to Run

### 1. Start database

```bash
docker compose up -d
```

### 2. Build project

```bash
./gradlew clean build -x test
```

### 3. Run student-service

```bash
./gradlew :student-service:bootRun
```

### 4. Run course-service in another terminal

```bash
./gradlew :course-service:bootRun
```

## Swagger URLs

Student Service:

```text
http://localhost:8081/swagger-ui/index.html
```

Course Service:

```text
http://localhost:8082/swagger-ui/index.html
```

## Example Requests

### Create Student

POST `/api/v1/students`

```json
{
  "firstName": "Nicat",
  "lastName": "Aliyev",
  "email": "nicat.aliyev@example.com",
  "age": 20
}
```

### Create Course Without Prerequisite

POST `/api/v1/courses`

```json
{
  "title": "Programming 1",
  "code": "CS101",
  "credits": 6,
  "prerequisiteCourseId": null
}
```

### Create Course With Prerequisite

POST `/api/v1/courses`

```json
{
  "title": "Programming 2",
  "code": "CS102",
  "credits": 6,
  "prerequisiteCourseId": 1
}
```

### Enroll Student

POST `/api/v1/courses/1/students/1`

### Get Courses by Student Name

GET `/api/v1/courses/students/name/Nicat`

## Notes

- Enrollment date is stored in the enrollment table because enrollment represents the relationship between a student and a course.
- Prerequisite validation happens before enrollment.
- If a course has a prerequisite, the student must already be enrolled in that prerequisite course.
- Swagger documentation is written in Azerbaijani as required by the assignment.
