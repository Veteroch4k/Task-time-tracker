package com.veteroch4k.tasktracker.controllers;

import com.veteroch4k.tasktracker.models.DTO.TimeRecordDTO;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
public class TimeRecordController {

  @PostMapping
  public ResponseEntity<Void> createLogTime(@RequestBody TimeRecordDTO timeRecordDTO) {

    return ResponseEntity.ok().build();
  }

  @GetMapping("/{emp_id}")
  public ResponseEntity<Void> getEmployeeTimeRecords(@PathVariable Long emp_id,
      @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME)LocalDateTime startDate,
      @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endDate) {

    return ResponseEntity.ok().build();

  }

}
