package com.veteroch4k.tasktracker.models;

import lombok.Data;

@Data
public class Task {

  private Long id;

  private String name;


  private String description;

  private TaskStatus status;

}


