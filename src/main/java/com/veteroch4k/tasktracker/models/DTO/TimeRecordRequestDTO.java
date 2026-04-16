package com.veteroch4k.tasktracker.models.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public record TimeRecordRequestDTO(

    @Schema(description = "ID сотрудника, выполнявшего задачу",
        nullable = true, example = "12")
    Long employeeId,

    @Schema(description = "ID выполняемой задачи",
        example = "12")
    Long taskId,

    @Schema(description = "Время начала работы над задачи",
        example = "2007-12-03T10:15:30")
    LocalDateTime startTime,

    @Schema(description = "Время окончания работы над задачей",
        example = "2007-12-03T10:15:30")
    LocalDateTime endTime,

    @Schema(description = "Описание проделанной работы",
        example = "Было проделано...")
    String description

) {}

