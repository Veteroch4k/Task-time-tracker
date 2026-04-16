package com.veteroch4k.tasktracker.services;

import com.veteroch4k.tasktracker.mappers.TimeRecordMapper;
import com.veteroch4k.tasktracker.models.DTO.TimeRecordRequestDTO;
import com.veteroch4k.tasktracker.models.TimeRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeRecordService {

  private final TimeRecordMapper mapper;

  public void insert(TimeRecordRequestDTO timeRecordDTO) {

    TimeRecord record = new TimeRecord();
    record.setEmployeeId(timeRecordDTO.employeeId());
    record.setTaskId(timeRecordDTO.taskId());
    record.setStartTime(timeRecordDTO.startTime());
    record.setEndTime(timeRecordDTO.endTime());

  }

}
