package com.veteroch4k.tasktracker.models.DTO;

import java.time.LocalDateTime;

public record TimeRecordRequestDTO(
     Long employeeId,

     Long taskId,

     LocalDateTime startTime,

     LocalDateTime endTime,

     String description
) {

}
