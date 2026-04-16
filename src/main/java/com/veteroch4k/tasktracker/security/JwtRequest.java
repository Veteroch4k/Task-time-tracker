package com.veteroch4k.tasktracker.security;

public record JwtRequest(
    String login,
    String password
) {

}
