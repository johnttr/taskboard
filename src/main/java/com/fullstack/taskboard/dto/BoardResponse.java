package com.fullstack.taskboard.dto;

import java.time.LocalDateTime;

public record BoardResponse(
    Long id,
    String title,
    LocalDateTime createAt,
    Long ownerId,
    String ownerName
) {
}