package com.fullstack.taskboard.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
    @NotBlank(message = "Name est obligatoire")
    String name,

    @Email(message = "l'Email doit être valide")
    @NotBlank(message = "Email est obligatoire")
    String email
) {
}
