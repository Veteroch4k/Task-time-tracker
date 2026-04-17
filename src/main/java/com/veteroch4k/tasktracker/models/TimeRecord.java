package com.veteroch4k.tasktracker.models;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import lombok.Data;

@Data
@Schema(description = "Модель записи о затраченном времени сотрудника на задачу")
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
  example = "2025-09-03T10:15:30Z", maximum = "end_Time")
  private OffsetDateTime startTime;

  @Schema(description = "Время окончания работы над задачей",
      example = "2025-10-03T10:15:30Z", minimum = "start_Time")
  private OffsetDateTime endTime;

  @Schema(description = "Описание проделанной работы",
      example = "Было проделано...")
  private String description;

}
