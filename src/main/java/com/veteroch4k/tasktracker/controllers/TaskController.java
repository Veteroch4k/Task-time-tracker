package com.veteroch4k.tasktracker.controllers;

import com.veteroch4k.tasktracker.models.DTO.TaskDTO;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import com.veteroch4k.tasktracker.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Task API", description = "API для управления задачами")
public class TaskController {

  private final TaskService taskService;

  @Operation(summary = "Получение задачи по ID",
  description = "Возвращает объект задачи, если он найден в системе")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Задача найдена"),
      @ApiResponse(responseCode = "404", description = "Задача отсутствует")
  })
  @GetMapping("/{id}")
  public ResponseEntity<Task> getTask(@Parameter(description = "ID задачи") @PathVariable Long id){

    Task task = taskService.getTask(id);

    return ResponseEntity.ok(task);

  }

  @Operation(summary = "Создать новую задачу")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Задача успешно создана"),
      @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
  })
  @PostMapping
  public ResponseEntity<Task> createTask(@Parameter(description = "Данные новой задачи")
  @Valid @RequestBody TaskDTO taskDTO) {

    Task task = taskService.createTask(taskDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(task);
  }

  @Operation(summary = "Обновить статус задачи ")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Статус успешно обновлён"),
      @ApiResponse(responseCode = "400", description = "Некорректный статус или формат данных"),
      @ApiResponse(responseCode = "404", description = "Задача не найдена")
  })
  @PatchMapping("/{id}/status")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void changeStatus(@Parameter(description = "ID обновляемой задачи") @PathVariable Long id,
      @Parameter(description = "Новый статус задачи") @RequestBody TaskStatus newStatus) {

    taskService.updateStatus(id, newStatus);

  }



}
