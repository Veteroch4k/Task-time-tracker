package com.veteroch4k.tasktraker.models.DTO;

import java.time.LocalDateTime;

public record TimeRecordDTO (
     int employeeId,

     int taskId,

     LocalDateTime startDate,

     LocalDateTime endDate,

     String description
) {

}
