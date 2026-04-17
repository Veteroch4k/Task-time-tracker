package com.veteroch4k.tasktracker.controllers;

import com.veteroch4k.tasktracker.mappers.TimeRecordMapper;
import com.veteroch4k.tasktracker.models.DTO.EmployeeStatsDTO;
import com.veteroch4k.tasktracker.models.DTO.TimeRecordRequestDTO;
import com.veteroch4k.tasktracker.models.TimeRecord;
import com.veteroch4k.tasktracker.services.TimeRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time-record")
@RequiredArgsConstructor
@Tag(name = "TimeRecord API", description = "API для управления записями времени работы сотрудников")
public class TimeRecordController {

  private final TimeRecordService service;

  @Operation(summary = "Создание записи о затраченном времени сотрудника на задачу")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Запись успешно создана"),
      @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
  })
  @PostMapping
  public ResponseEntity<TimeRecord> createLogTime(@Parameter(description = "Данные о времени работы")
      @Valid @RequestBody TimeRecordRequestDTO timeRecordRequestDTO) {

    TimeRecord record = service.createTimeRecord(timeRecordRequestDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(record);
  }

  @Operation(summary = "Получение инфы о затраченном времени сотрудника на задачу за определенный промежуток времени",
  description = "Возвращает список с записями времени работы сотрудника")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Записи успешно получены"),
      @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных"),
      @ApiResponse(responseCode = "404", description = "Отсутствуют записи о данном сотруднике")
  })
  @GetMapping("/{empId}")
  public ResponseEntity<EmployeeStatsDTO> getEmployeeTimeRecordsBetweenTime(
      @Parameter(description = "ID рассматриваемого сотрудника") @PathVariable("empId") Long empId,
      @Parameter(description = "Начало временного интервала") @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime startDate,
      @Parameter(description = "Конец временного интервала") @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) OffsetDateTime endDate) {

    EmployeeStatsDTO stats = service.getStatisticsOfEmployee(empId, startDate, endDate);

    return ResponseEntity.ok(stats);

  }

}
