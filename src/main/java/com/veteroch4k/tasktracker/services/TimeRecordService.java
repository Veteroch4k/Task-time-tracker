package com.veteroch4k.tasktracker.services;

import com.veteroch4k.tasktracker.mappers.TimeRecordMapper;
import com.veteroch4k.tasktracker.models.DTO.TimeRecordRequestDTO;
import com.veteroch4k.tasktracker.models.TimeRecord;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeRecordService {

  private final TimeRecordMapper mapper;

  public TimeRecord createTimeRecord(TimeRecordRequestDTO timeRecordDTO) {

    TimeRecord record = new TimeRecord();
    record.setEmployeeId(timeRecordDTO.employeeId());
    record.setTaskId(timeRecordDTO.taskId());
    record.setStartTime(timeRecordDTO.startTime());
    record.setEndTime(timeRecordDTO.endTime());
    record.setDescription(timeRecordDTO.description());

    mapper.insert(record);

    return record;

  }

  public List<TimeRecord> getStatisticsOfEmployee(Long employeeId, LocalDateTime start, LocalDateTime end) {

    List<TimeRecord> records = mapper.getAllByStartTimeBetween(employeeId, start, end);

    return records;


  }

}
