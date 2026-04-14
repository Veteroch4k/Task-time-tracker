package com.veteroch4k.tasktracker.models;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TimeRecord {

  private Long id;

  private int employeeId;

  private int taskId;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String description;

}
