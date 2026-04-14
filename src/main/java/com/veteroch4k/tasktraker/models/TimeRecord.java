package com.veteroch4k.tasktraker.models;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TimeRecord {

  private Long id;

  private int employeeId;

  private int taskId;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private String description;

}
