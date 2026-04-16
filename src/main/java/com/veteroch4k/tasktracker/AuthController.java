package com.veteroch4k.tasktracker;

import com.veteroch4k.tasktracker.security.JwtRequest;
import com.veteroch4k.tasktracker.security.JwtResponse;
import com.veteroch4k.tasktracker.security.RefreshJwtRequest;
import com.veteroch4k.tasktracker.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("login")
  public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest) {
    final JwtResponse token = authService.login(jwtRequest);
    return ResponseEntity.ok(token);
  }

  @PostMapping("token")
  public ResponseEntity<JwtResponse> getnewAccessToken(@RequestBody RefreshJwtRequest request) {
    final JwtResponse token = authService.getAccessToken(request.refreshToken());
    return ResponseEntity.ok(token);
  }

  @PostMapping("refresh")
  public ResponseEntity<JwtResponse> refresh(@RequestBody RefreshJwtRequest request) {
    final JwtResponse token = authService.refresh(request.refreshToken());
    return ResponseEntity.ok(token);
  }

}
