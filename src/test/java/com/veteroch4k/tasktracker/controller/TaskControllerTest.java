package com.veteroch4k.tasktracker.controller;

import com.veteroch4k.tasktracker.mappers.TaskMapper;
import com.veteroch4k.tasktracker.models.DTO.TaskDTO;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test") // Я так случайно забыл @DynamicPropertySource аннотацию добавить и фулл БДШку очистил под 0
// Так что от греха подальше пусть будет тестовый профиль, если по какой-то случайности перехват DynamicPropertySource не сработает
public class TaskControllerTest {

  @LocalServerPort
  private Integer port;

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
      "postgres:16-alpine"
  );

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url",() -> postgres.getJdbcUrl() + "&stringtype=unspecified");
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired
  TaskMapper taskMapper;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
    taskMapper.deleteAll();
  }

  @Test
  void shouldGetTask() {

    String expectedName = "TestName";
    String expectedDescription = "TestDesc";
    TaskStatus expectedStatus = TaskStatus.NEW;

    Task task = new Task();
    task.setName(expectedName);
    task.setDescription(expectedDescription);
    task.setStatus(expectedStatus);

    taskMapper.insert(task);

    Long id = task.getId();

    given()
        .contentType(ContentType.JSON)
    .when()
        .get("/api/tasks/" + id)
    .then()
        .statusCode(200)
        .body("id", equalTo(id.intValue()))
        .body("name",equalTo(expectedName))
        .body("description", equalTo(expectedDescription))
        .body("status", equalTo(expectedStatus.name()));

  }


  @Test
  void shouldCreateTask() {

    String expectedName = "TestName";
    String expectedDescription = "TestDesc";
    TaskStatus expectedStatus = TaskStatus.NEW;

    TaskDTO newTask = new TaskDTO(expectedName, expectedDescription, expectedStatus);

    given()
        .contentType(ContentType.JSON)
        .body(newTask)
    .when()
        .post("/api/tasks")
    .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("name", equalTo(expectedName))
        .body("description", equalTo(expectedDescription))
        .body("status", equalTo(expectedStatus.name()));
  }

}
