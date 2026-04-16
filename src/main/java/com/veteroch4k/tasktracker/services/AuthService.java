package com.veteroch4k.tasktracker.services;

import com.veteroch4k.tasktracker.security.JwtProvider;
import com.veteroch4k.tasktracker.security.JwtRequest;
import com.veteroch4k.tasktracker.security.JwtResponse;
import com.veteroch4k.tasktracker.security.User;
import jakarta.security.auth.message.AuthException;
import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserService userService;
  private final Map<String, String> refreshStorage = new HashMap<>();
  private final JwtProvider jwtProvider;

  public JwtResponse login(@NonNull JwtRequest authRequest) {

    final User user = userService.getByLogin(authRequest.login())
        .orElseThrow(() -> new AuthException("Пользователь не найден"));

  }

}
