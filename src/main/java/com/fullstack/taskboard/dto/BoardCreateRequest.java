package com.fullstack.taskboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BoardCreateRequest(
    @NotBlank(message = "Title est obligatoire")
    String title,

    @NotNull(message = "ownerId est obligatoire")
    Long ownerId
) {
}
