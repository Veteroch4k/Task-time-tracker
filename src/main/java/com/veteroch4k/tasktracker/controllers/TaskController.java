package com.veteroch4k.tasktracker.controllers;

import com.veteroch4k.tasktracker.models.DTO.TaskDTO;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

  private final TaskService taskService;

  @GetMapping("/{id}")
  public ResponseEntity<Task> getTask(@PathVariable Long id){

    Task task = taskService.getTask(id);

    return ResponseEntity.ok(task);

  }

  @PostMapping
  public ResponseEntity<Void> createTask(@RequestBody TaskDTO taskDTO) {
    return ResponseEntity.ok().build();
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestBody String newStatus) {
    return ResponseEntity.ok().build();
  }



}
