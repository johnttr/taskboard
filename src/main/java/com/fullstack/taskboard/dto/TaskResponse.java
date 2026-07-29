package com.fullstack.taskboard.dto;

import com.fullstack.taskboard.model.Statut;
import java.time.LocalDateTime;

public record TaskResponse(
    Long id,
    String title,
    String description,
    Statut statut,
    LocalDateTime createAt,
    Long boardId,
    String boardTitle,
    Long ownerId,
    String ownerName
) {
}