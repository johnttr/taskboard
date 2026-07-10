package com.fullstack.taskboard.dto;

public record UserResponse(
    Long id,
    String name,
    String email
) {
}
