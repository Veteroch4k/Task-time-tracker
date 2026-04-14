package com.veteroch4k.tasktracker.controllers;

import com.veteroch4k.tasktracker.models.DTO.TaskDTO;
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
public class TaskController {

  @GetMapping("/{id}")
  public ResponseEntity<TaskDTO> getTask(@PathVariable Long id){

    return ResponseEntity.ok().build();

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
