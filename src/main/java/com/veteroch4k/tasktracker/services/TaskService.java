package com.veteroch4k.tasktracker.services;

import com.veteroch4k.tasktracker.exceptions.ResourceNotFoundException;
import com.veteroch4k.tasktracker.mappers.TaskMapper;
import com.veteroch4k.tasktracker.models.DTO.TaskDTO;
import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskMapper taskMapper;

  public Task getTask(Long id) {

    return taskMapper.getTaskById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Задача с ID " + id + " не найдена"));


  }

  public Task createTask(TaskDTO taskDTO) {
    Task task = new Task();
    task.setName(taskDTO.name());
    task.setDescription(taskDTO.description());
    task.setStatus(TaskStatus.NEW);

    taskMapper.insert(task);

    return task;

  }

  public void updateStatus(Long id, TaskStatus status) {

    boolean res = taskMapper.updateStatus(id, status);

    if(!res) throw new ResourceNotFoundException("Задача с ID " + id + " не найдена");

  }

}
