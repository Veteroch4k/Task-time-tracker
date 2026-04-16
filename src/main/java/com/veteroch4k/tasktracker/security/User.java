package com.veteroch4k.tasktracker.security;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class User {

  private String login;
  private String password;
  private String firstName;
  private String lastName;
  private Set<Role> roles;


}



