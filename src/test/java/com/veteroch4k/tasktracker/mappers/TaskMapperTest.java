package com.veteroch4k.tasktracker.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.veteroch4k.tasktracker.BaseIntegrationTest;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskMapperTest extends BaseIntegrationTest {

  @Autowired
  TaskMapper mapper;

  @BeforeEach
  void setUp() {
    mapper.deleteAll();
  }

  @Test
  void shouldGetTaskById() {

    String expectedName = "Test";
    TaskStatus expectedStatus = TaskStatus.NEW;

    Task expectedtask = new Task();
    expectedtask.setName(expectedName);
    expectedtask.setStatus(expectedStatus);
    mapper.insert(expectedtask);

    Long id = expectedtask.getId();

    Task resultTask = mapper.getTaskById(id).get();

    assertEquals(expectedName, resultTask.getName(), "Названия задач должны совпадать");
    assertEquals(expectedStatus, resultTask.getStatus(), "Статусы задач должны совпадать");

  }

  @Test
  void shouldInsertTask() {

    String expectedName = "Test";
    TaskStatus expectedStatus = TaskStatus.NEW;

    Task expectedtask = new Task();
    expectedtask.setName(expectedName);
    expectedtask.setStatus(expectedStatus);

    mapper.insert(expectedtask);

    assertNotNull(expectedtask.getId(), "ID задачи не должен быть null после сохранения");

  }

  @Test
  void shouldUpdateStatus() {

    String expectedName = "Test";
    TaskStatus expectedStatus = TaskStatus.NEW;

    Task expectedtask = new Task();
    expectedtask.setName(expectedName);
    expectedtask.setStatus(expectedStatus);

    mapper.insert(expectedtask);

    Long id = expectedtask.getId();

    boolean isUpdated = mapper.updateStatus(id, TaskStatus.DONE);

    assertTrue(isUpdated, "Метод должен вернуть true при успешном обновлении");

    Task resultTask = mapper.getTaskById(id).get();
    assertEquals(TaskStatus.DONE, resultTask.getStatus(), "Статус в БД должен измениться на DONE");
  }

  @Test
  void shouldDeleteAll() {

    String expectedName = "Test";
    TaskStatus expectedStatus = TaskStatus.NEW;

    Task expectedtask = new Task();
    expectedtask.setName(expectedName);
    expectedtask.setStatus(expectedStatus);

    mapper.insert(expectedtask);

    mapper.deleteAll();

    Optional<Task> result = mapper.getTaskById(expectedtask.getId());
    assertFalse(result.isPresent(), "База должна быть пустой, Optional должен быть пустым");
  }


}
