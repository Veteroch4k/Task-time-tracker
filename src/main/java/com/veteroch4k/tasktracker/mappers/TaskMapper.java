package com.veteroch4k.tasktracker.mappers;

import com.veteroch4k.tasktracker.models.Task;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TaskMapper {

  @Select("SELECT * FROM tasks WHERE id = #{id}")
  Optional<Task> getTaskById(@Param("id") Long id);

}
