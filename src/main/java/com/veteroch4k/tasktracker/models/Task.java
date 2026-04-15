package com.veteroch4k.tasktracker.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Модель задачи")
public class Task {

  @Schema(description = "Уникальный идентификатор задачи",
  example = "123")
  private Long id;

  @Schema(description = "Название задачи",
      example = "Создание отчета за прошедший квартал...")
  private String name;

  @Schema(description = "Подробное описание задачи (сроки, важность задачи тд.)",
      example = "Необходимо собрать информацию о заказах из Новосибирска...")
  private String description;

  @Schema(description = "Статус задачи",
      allowableValues = {"NEW", "IN_PROGRESS", "DONE"})
  private TaskStatus status;

}


