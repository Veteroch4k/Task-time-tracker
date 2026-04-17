package com.veteroch4k.tasktracker.controllers;

import com.veteroch4k.tasktracker.security.JwtProvider;
import com.veteroch4k.tasktracker.security.User;
import com.veteroch4k.tasktracker.services.AuthService;
import com.veteroch4k.tasktracker.services.UserService;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BaseIntegrationTest {

  @LocalServerPort
  private Integer port;

  @ServiceConnection
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
      .withUrlParam("stringtype", "unspecified");

  @Autowired
  private JwtProvider provider;

  @Autowired
  private UserService userService;

  @BeforeEach
  void setUpSecurity() {
    RestAssured.baseURI = "http://localhost:" + port;

    String token = provider.generateAccessToken(userService.getByLogin("user1").get());

    RestAssured.requestSpecification = new RequestSpecBuilder()
        .addHeader("Authorization", "Bearer " + token)
        .build();
  }

  static {
    postgres.start();
  }
}
