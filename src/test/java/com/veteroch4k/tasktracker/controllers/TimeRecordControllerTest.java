package com.veteroch4k.tasktracker.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import com.veteroch4k.tasktracker.mappers.TaskMapper;
import com.veteroch4k.tasktracker.mappers.TimeRecordMapper;
import com.veteroch4k.tasktracker.models.DTO.TimeRecordRequestDTO;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import com.veteroch4k.tasktracker.models.TimeRecord;
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

  @Test
  void shouldGetEmployeeTimeRecordsBetweenTime() {

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    Long taskId = task.getId();
    Long employeeId = 1L;
    OffsetDateTime startTime = OffsetDateTime.now();
    OffsetDateTime endTime = startTime.plusDays(10);
    String description = "test";

    TimeRecord record1 = new TimeRecord();
    record1.setEmployeeId(employeeId);
    record1.setTaskId(taskId);
    record1.setStartTime(startTime.plusDays(3));
    record1.setEndTime(endTime.plusDays(3));
    record1.setDescription(description);

    TimeRecord record2 = new TimeRecord();
    record2.setEmployeeId(employeeId);
    record2.setTaskId(taskId);
    record2.setStartTime(startTime.minusDays(3));
    record2.setEndTime(endTime.minusDays(3));
    record2.setDescription(description);

    TimeRecordmapper.insert(record1);
    TimeRecordmapper.insert(record2);

    int resultSize = 2;

    given()
        .log().all()
        .contentType(ContentType.JSON)
    .when()
        .get("/api/time-record/" + employeeId + "?startTime=" + startTime + "&endTime=" + endTime)
    .then()
        .statusCode(200)
        .body("records", hasSize(resultSize))
        .body("recordsCount", equalTo(resultSize));

  }

  @Test
  void shouldReturn400WhenValidationErrorGettingStatsOfEmployee() {

    long employeeId = 1;
    OffsetDateTime endTime = OffsetDateTime.now();
    OffsetDateTime incorrectStartTime = endTime.plusDays(1);

    given()
        .log().all()
        .contentType(ContentType.JSON)
        .when()
        .get("/api/time-record/" + employeeId + "?startTime=" + incorrectStartTime + "&endTime=" + endTime)
        .then()
        .statusCode(400);

  }

  @Test
  void shouldReturn404WhenNoSuchEmployeeGettingStatsOfEmployee() {

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    Long taskId = task.getId();
    Long employeeId = 1L;
    OffsetDateTime startTime = OffsetDateTime.now();
    OffsetDateTime endTime = startTime.plusDays(10);
    String description = "test";

    given()
        .log().all()
        .contentType(ContentType.JSON)
        .when()
        .get("/api/time-record/" + (employeeId + 1) + "?startTime=" + startTime + "&endTime=" + endTime)
        .then()
        .statusCode(404);


  }


}
