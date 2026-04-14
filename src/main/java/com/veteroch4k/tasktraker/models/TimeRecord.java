package com.veteroch4k.tasktraker.models;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TimeRecord {

  private int id;

  private int employeeId;

  private int taskId;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  private String description;

}
