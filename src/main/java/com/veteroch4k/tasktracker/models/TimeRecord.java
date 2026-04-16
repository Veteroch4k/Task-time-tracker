package com.veteroch4k.tasktracker.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Schema(description = "Модель записи времени работы над задачей")
public class TimeRecord {

  @Schema(description = "Уникальный идентификатор записи",
      example = "123")
  private Long id;

  @Schema(description = "ID сотрудника, выполнявшего задачу",
      example = "12")
  private Long employeeId;

  @Schema(description = "ID выполняемой задачи",
      example = "12")
  private Long taskId;

  @Schema(description = "Время начала работы над задачи",
  example = "2007-12-03T10:15:30", maximum = "end_Time")
  private LocalDateTime startTime;

  @Schema(description = "Время окончания работы над задачей",
      example = "2007-12-03T10:15:30", minimum = "start_Time")
  private LocalDateTime endTime;

  @Schema(description = "Описание проделанной работы",
      example = "Было проделано...")
  private String description;

}
