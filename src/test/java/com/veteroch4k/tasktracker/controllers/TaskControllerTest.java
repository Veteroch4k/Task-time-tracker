package com.veteroch4k.tasktracker.controllers;

import com.veteroch4k.tasktracker.BaseIntegrationTest;
import com.veteroch4k.tasktracker.mappers.TaskMapper;
import com.veteroch4k.tasktracker.models.DTO.TaskDTO;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TaskControllerTest extends BaseIntegrationTest {

  @LocalServerPort
  private Integer port;


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

    TaskDTO newTask = new TaskDTO(expectedName, expectedDescription);

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

  @Test
  void shouldUpdateStatus() {

    TaskStatus expectedStatus = TaskStatus.DONE;

    Task task = new Task();
    task.setName("Stub");
    task.setStatus(TaskStatus.NEW);

    taskMapper.insert(task);

    Long id = task.getId();

    given()
        .contentType(ContentType.JSON)
        .body("\"" + expectedStatus.name() + "\"")
    .when()
        .patch("/api/tasks/" + id.intValue() + "/status")
    .then()
        .statusCode(204);

  }


}
