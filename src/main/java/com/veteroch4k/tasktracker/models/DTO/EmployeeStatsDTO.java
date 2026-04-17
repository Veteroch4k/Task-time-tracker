package com.veteroch4k.tasktracker.models.DTO;

import com.veteroch4k.tasktracker.models.TimeRecord;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

@Schema(description = "Статистика сотрудника (отработанные часы и записи о проделанной работе) за определенный промежуток времени")
public record EmployeeStatsDTO (
    @Schema(description = "Количество затраченных дней на задачи")
    @PositiveOrZero(message = "Количество дней не может принимать отрицательное значение")
    int totalDays,

    @Schema(description = "Количество затраченных часов на задачи")
    @PositiveOrZero(message = "Количество часов не может принимать отрицательное значение")
    @Max(value = 23, message = "Количество часов не может превышать 23 (в ином случае часы переводятся в дни)")
    int totalHours,

    @Schema(description = "Количество затраченных минут на задачи")
    @PositiveOrZero(message = "Количество минут не может принимать отрицательное значение")
    @Max(value = 59, message = "Количество минут не может превышать 60 (в ином случае часы переводятся в дни)")
    int totalMinutes,

    @Schema(description = "Количество записей (сессий работы)")
    @PositiveOrZero(message = "Количество записей не может принимать отрицательное значение")
    int recordsCount,

    @Schema(description = "Записи о проделанной работе")
    List<TimeRecord> records
)
{}
