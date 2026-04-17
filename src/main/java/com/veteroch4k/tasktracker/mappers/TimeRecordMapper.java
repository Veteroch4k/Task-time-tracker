package com.veteroch4k.tasktracker.mappers;

import com.veteroch4k.tasktracker.models.TimeRecord;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TimeRecordMapper {

  @Insert("INSERT INTO time_records(employee_id, task_id, start_time, end_time, description) "
      + "VALUES(#{employeeId}, #{taskId}, #{startTime}, #{endTime}, #{description}) ")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(TimeRecord record);

  @Select("SELECT * FROM time_records WHERE employee_id = #{employeeId}"
      + " AND start_time < #{end} AND end_time > #{start} ")
  List<TimeRecord> getAllByStartTimeBetween(@Param("employeeId") Long employeeId,
      @Param("start") OffsetDateTime start, @Param("end") OffsetDateTime end);

  @Select("SELECT EXISTS(SELECT 1 FROM time_records WHERE employee_id = #{employeeId})")
  boolean existsByEmployeeId(@Param("employeeId") Long employeeId);

  @Select("SELECT * FROM time_records WHERE id = #{id}")
  Optional<TimeRecord> getRecordById(@Param("id") Long id);

  @Delete("DELETE FROM time_records")
  void deleteAll();


}
