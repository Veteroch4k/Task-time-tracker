package com.veteroch4k.tasktracker.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;

@Schema(description = "Данные для создания или обновления записи о затраченном времени сотрудника на задачу")
public record TimeRecordRequestDTO(

    @Schema(description = "ID сотрудника, выполнявшего задачу",
        example = "12")
    @NotNull(message = "ID сотрудника не может быть Null")
    @PositiveOrZero(message = "ID сотрудника не может быть отрицательным")
    Long employeeId,

    @Schema(description = "ID выполняемой задачи",
        example = "12")
    @NotNull(message = "ID задачи не может быть Null")
    @PositiveOrZero(message = "ID задачи не может быть отрицательным")
    Long taskId,

    @Schema(description = "Время начала работы над задачи",
        example = "2007-12-03T10:15:30+01:00")
    @NotNull(message = "Время начала работы должно быть обязательно указано")
    OffsetDateTime startTime,

    @Schema(description = "Время окончания работы над задачей",
        example = "2007-12-03T10:15:30+01:00")
    @NotNull(message = "Время окончания работы должно быть обязательно указано")
    OffsetDateTime endTime,

    @Schema(description = "Описание проделанной работы",
        example = "Было проделано...")
    @NotBlank(message = "Описание проделанной работы не может быть пустым")
    String description

) {}

