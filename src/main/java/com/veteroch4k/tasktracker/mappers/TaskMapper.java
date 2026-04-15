package com.veteroch4k.tasktracker.mappers;

import com.veteroch4k.tasktracker.models.Task;
import com.veteroch4k.tasktracker.models.TaskStatus;
import java.util.Optional;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TaskMapper {

  @Select("SELECT * FROM tasks WHERE id = #{id}")
  Optional<Task> getTaskById(@Param("id") Long id);

  @Insert("INSERT INTO tasks(name, description, status) VALUES(#{name}, #{description}, #{status})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(Task task);

  @Update("UPDATE tasks SET status = #{status} WHERE id = #{id}")
  boolean updateStatus(@Param("id") Long id, @Param("status")TaskStatus status);


  @Delete("DELETE FROM tasks")
  void deleteAll();

}
