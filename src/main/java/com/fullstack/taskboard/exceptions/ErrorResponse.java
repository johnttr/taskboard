package com.fullstack.taskboard.exceptions;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String message,
    Map<String, String> fieldErrors
) {
    public ErrorResponse(LocalDateTime timestamp, int status, String message) {
        this(timestamp, status, message, null);
    }
    public ErrorResponse(int status, String message, Map<String, String> fieldErrors) {
        this(LocalDateTime.now(), status, message, fieldErrors);
    }
}
