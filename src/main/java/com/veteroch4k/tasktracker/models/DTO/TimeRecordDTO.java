package com.veteroch4k.tasktracker.models.DTO;

import java.time.LocalDateTime;

public record TimeRecordDTO (
     int employeeId,

     int taskId,

     LocalDateTime startTime,

     LocalDateTime endTime,

     String description
) {

}
