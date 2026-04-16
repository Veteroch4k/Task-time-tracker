package com.veteroch4k.tasktracker.services;

import com.veteroch4k.tasktracker.security.Role;
import com.veteroch4k.tasktracker.security.User;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final List<User> users;

  public UserService(List<User> users) {
    this.users = List.of(
        new User("user1", "12345", "user1", "user1", Collections.singleton(Role.USER)),
        new User("victor", "12345", "Виктор", "Попов", Collections.singleton(Role.ADMIN))
    );
  }

  public Optional<User> getByLogin(@NonNull String login) {
    return users.stream()
        .filter(user -> login.equals(user.getLogin()))
        .findFirst();
  }


}
