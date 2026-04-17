package com.veteroch4k.tasktracker.services;

import com.veteroch4k.tasktracker.exceptions.ResourceNotFoundException;
import com.veteroch4k.tasktracker.mappers.TimeRecordMapper;
import com.veteroch4k.tasktracker.models.DTO.EmployeeStatsDTO;
import com.veteroch4k.tasktracker.models.DTO.TimeRecordRequestDTO;
import com.veteroch4k.tasktracker.models.TimeRecord;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

  public EmployeeStatsDTO getStatisticsOfEmployee(Long employeeId, OffsetDateTime start, OffsetDateTime end) {

    if (start.isAfter(end)) {
      throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");
    }

    if (!mapper.existsById(employeeId)) {
      throw new ResourceNotFoundException("Сотрудник с ID " + employeeId + " не найден в системе");
    }

    List<TimeRecord> records = mapper.getAllByStartTimeBetween(employeeId, start, end);

    int totalMinutesSum = (int) records.stream()
        .map(record -> Duration.between(record.getStartTime(), record.getEndTime()))
        .mapToLong(Duration::toMinutes)
        .sum();

    int totalHours = totalMinutesSum / 60;
    int days = totalHours / 24;
    int hours = totalHours % 24;
    int minutes = totalMinutesSum % 60;

    return new EmployeeStatsDTO(
        days, hours, minutes, records.size(), records
    );


  }

}
