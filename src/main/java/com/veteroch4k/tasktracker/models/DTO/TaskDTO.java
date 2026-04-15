package com.veteroch4k.tasktracker.models.DTO;

import com.veteroch4k.tasktracker.models.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Данные для создания или обновления задачи")
public record TaskDTO(

     @Schema(description = "Название задачи", example = "Создание отчета за прошедший квартал...")
     String name,

     @Schema(description = "Подробное описание задачи (сроки, важность задачи тд.)", example = "Необходимо собрать информацию о заказах из Новосибирска...")
     String description,

     @Schema(description = "Статус задачи", allowableValues = {"NEW", "IN_PROGRESS", "DONE"})
     TaskStatus status
) {}
