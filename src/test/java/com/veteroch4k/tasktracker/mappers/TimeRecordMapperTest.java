package com.veteroch4k.tasktracker.mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import com.veteroch4k.tasktracker.models.TimeRecord;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TimeRecordMapperTest extends BaseMapperTest{

  @Autowired
  TimeRecordMapper recordMapper;

  @Autowired
  TaskMapper taskMapper;

  @Test
  void shouldInsert() {

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    long taskId = task.getId();

    TimeRecord record = new TimeRecord();
    record.setEmployeeId(1L);
    record.setTaskId(taskId);
    record.setStartTime(OffsetDateTime.now());
    record.setEndTime(OffsetDateTime.now().plusDays(1));
    record.setDescription("test");

    recordMapper.insert(record);

    assertNotNull(record.getId(), "ID записи не должен быть null после сохранения");

  }

  @Test
  void shouldGetAllByStartDateBetween() {

    /**
     * Тут мы создаем несколько типов записей:
     * 0 - входящие в заданные временные рамки
     * 1 - входящие в заданные временные рамки, но другого сотрудника
     * 2 - начавшиеся раньше startTime и закончившиеся до endTime
     * 3 - начавшиеся позже startTime и закончившиеся после endTime
     * 4 - начавшиеся раньше startTime и закончившиеся после endTime
     * 5 - не входящие в диапазон
      */

    long employeeId = 7L;
    OffsetDateTime startTime = OffsetDateTime.now();
    OffsetDateTime endTime = startTime.plusDays(5);
    int resultCountOfRecords = 5;

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    long taskId = task.getId();

    TimeRecord record0 = new TimeRecord();
    record0.setTaskId(task.getId());
    record0.setEmployeeId(employeeId);
    record0.setStartTime(startTime);
    record0.setEndTime(endTime.minusDays(1));
    record0.setDescription("test");

    TimeRecord record1 = new TimeRecord();
    record1.setTaskId(taskId);
    record1.setEmployeeId(employeeId);
    record1.setStartTime(startTime.plusDays(1));
    record1.setEndTime(endTime.minusDays(1));
    record1.setDescription("test");

    TimeRecord record2 = new TimeRecord();
    record2.setTaskId(taskId);
    record2.setEmployeeId(employeeId);
    record2.setStartTime(startTime.minusDays(1));
    record2.setEndTime(endTime.minusDays(1));
    record2.setDescription("test");

    TimeRecord record3 = new TimeRecord();
    record3.setTaskId(taskId);
    record3.setEmployeeId(employeeId);
    record3.setStartTime(startTime.plusDays(1));
    record3.setEndTime(endTime.plusDays(1));
    record3.setDescription("test");

    TimeRecord record4 = new TimeRecord();
    record4.setTaskId(taskId);
    record4.setEmployeeId(employeeId);
    record4.setStartTime(startTime.minusDays(1));
    record4.setEndTime(endTime.plusDays(1));
    record4.setDescription("test");

    /* Не входящие во временной диапазон/ другого сотрудника*/

    TimeRecord record5 = new TimeRecord();
    record5.setTaskId(taskId);
    record5.setEmployeeId(employeeId);
    record5.setStartTime(startTime.minusDays(10));
    record5.setEndTime(endTime.minusDays(10));
    record5.setDescription("test");

    TimeRecord record6 = new TimeRecord();
    record6.setTaskId(taskId);
    record6.setEmployeeId(employeeId);
    record6.setStartTime(startTime.plusDays(10));
    record6.setEndTime(endTime.plusDays(10));
    record6.setDescription("test");

    TimeRecord recordAnotherEmpl = new TimeRecord();
    recordAnotherEmpl.setTaskId(taskId);
    recordAnotherEmpl.setEmployeeId(1L);
    recordAnotherEmpl.setStartTime(startTime.plusDays(2));
    recordAnotherEmpl.setEndTime(endTime.minusDays(2));
    recordAnotherEmpl.setDescription("test");

    List<TimeRecord> records = List.of(
        record0, record1, record2, record3, record4,record5, record6, recordAnotherEmpl
    );

    for(TimeRecord record : records) recordMapper.insert(record);

    List<TimeRecord> result = recordMapper.getAllByStartTimeBetween(employeeId, startTime, endTime);

    assertEquals(resultCountOfRecords, result.size());

  }

  @Test
  void shouldCheckIfExistsById() {

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    long taskId = task.getId();

    TimeRecord record = new TimeRecord();
    record.setEmployeeId(1L);
    record.setTaskId(taskId);
    record.setStartTime(OffsetDateTime.now());
    record.setEndTime(OffsetDateTime.now().plusDays(1));
    record.setDescription("test");

    recordMapper.insert(record);

    boolean exists = recordMapper.existsByEmployeeId(record.getEmployeeId());
    boolean nonExists = recordMapper.existsByEmployeeId(-1L);

    assertTrue(exists);
    assertFalse(nonExists);

  }

  @Test
  void shouldGetRecordById() {

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    long taskId = task.getId();

    TimeRecord record = new TimeRecord();
    record.setEmployeeId(1L);
    record.setTaskId(taskId);
    record.setStartTime(OffsetDateTime.now(ZoneOffset.UTC).truncatedTo(ChronoUnit.MILLIS));
    record.setEndTime(OffsetDateTime.now(ZoneOffset.UTC).plusDays(1).truncatedTo(ChronoUnit.MILLIS));
    record.setDescription("test");

    recordMapper.insert(record);

    Optional<TimeRecord> timeRecord = recordMapper.getRecordById(record.getId());

    assertTrue(timeRecord.isPresent());

    TimeRecord res = timeRecord.get();

    assertEquals(res.getEmployeeId(), record.getEmployeeId());
    assertEquals(res.getTaskId(), record.getTaskId());
    assertEquals(res.getStartTime(), record.getStartTime());
    assertEquals(res.getEndTime(), record.getEndTime());
    assertEquals(res.getDescription(), record.getDescription());

  }

  @Test
  void shouldDeleteAll() {

    Task task = new Task();
    task.setName("test");
    task.setStatus(TaskStatus.NEW);
    taskMapper.insert(task);

    long taskId = task.getId();

    TimeRecord record = new TimeRecord();
    record.setEmployeeId(1L);
    record.setTaskId(taskId);
    record.setStartTime(OffsetDateTime.now());
    record.setEndTime(OffsetDateTime.now().plusDays(1));
    record.setDescription("test");

    recordMapper.insert(record);

    recordMapper.deleteAll();

    Optional<TimeRecord> timeRecord = recordMapper.getRecordById(record.getId());

    assertFalse(timeRecord.isPresent());


  }


}
