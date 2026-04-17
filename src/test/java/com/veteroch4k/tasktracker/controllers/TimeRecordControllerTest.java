package com.veteroch4k.tasktracker.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import com.veteroch4k.tasktracker.mappers.TaskMapper;
import com.veteroch4k.tasktracker.mappers.TimeRecordMapper;
import com.veteroch4k.tasktracker.models.DTO.TimeRecordRequestDTO;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import com.veteroch4k.tasktracker.services.TimeRecordService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

public class TimeRecordControllerTest extends BaseIntegrationTest {

  @LocalServerPort
  private Integer port;

  @Autowired
  TimeRecordMapper TimeRecordmapper;

  @Autowired
  TaskMapper taskMapper;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = "http://localhost:" + port;
    taskMapper.deleteAll();
  }


  @Test
  void shouldCreateTimeRecord() {

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    Long taskId = task.getId();
    Long employeeId = 1L;

    // Jackson по умолчанию конвертирует даты в UTC и обрезает наносекунды
    OffsetDateTime startTime = OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS);

    OffsetDateTime endTime = startTime.plusDays(1);
    String description = "test";


    TimeRecordRequestDTO requestDTO = new TimeRecordRequestDTO(
        employeeId, taskId, startTime, endTime, description
    );

    given()
        .contentType(ContentType.JSON)
        .body(requestDTO)
    .when()
        .post("/api/time-record")
    .then()
        .statusCode(201)
        .body("id", notNullValue())
        .body("employeeId", equalTo(employeeId.intValue()))
        .body("taskId", equalTo(taskId.intValue()))
        .body("startTime", equalTo(startTime.toString()))
        .body("endTime", equalTo(endTime.toString()))
        .body("description", equalTo(description));

  }

  @Test
  void shouldReturn400WhenValidationErrorCreatingRecord() {

    String BlankDescription = "";

    TimeRecordRequestDTO requestDTO = new TimeRecordRequestDTO(
        1L, 1L, OffsetDateTime.now(), OffsetDateTime.now().plusDays(1), BlankDescription
    );

    given()
        .contentType(ContentType.JSON)
        .body(requestDTO)
    .when()
        .post("/api/time-record")
    .then()
        .statusCode(400);
  }

}
