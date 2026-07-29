package com.fullstack.taskboard.dto;

import jakarta.validation.constraints.Size;

import com.fullstack.taskboard.model.Statut;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskCreateRequest(
    @NotBlank(message = "Title est obligatoire")
    @Size(max = 100, message = "Le titre ne doit pas dépasser 100 caractères")
    String title,

    @NotBlank(message = "Description est obligatoire")
    @Size(min = 6, max = 500, message = "La description doit avoir entre 6 et 500 caractères")
    String description,

    Statut statut,
    
    @NotNull(message = "boardId est obligatoire")
    Long boardId
) {
}