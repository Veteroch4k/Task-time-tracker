package com.veteroch4k.tasktracker.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Данные для создания или обновления задачи")
public record TaskRequestDTO(

     @Schema(description = "Название задачи", example = "Создание отчета за прошедший квартал...")
     @NotBlank(message = "Название задачи не может быть пустым")
     @Size(max = 100, message = "Название задачи не должно превышать 100 символов")
     String name,

     @Schema(description = "Подробное описание задачи (сроки, важность задачи тд.)", example = "Необходимо собрать информацию о заказах из Новосибирска...")
     @Size(max = 1000, message = "Описание слишком длинное")
     String description

) {}
