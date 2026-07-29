package com.fullstack.taskboard.service.support;

import com.fullstack.taskboard.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Utilitaire central de récupération d'entité.
 *
 * Principe DRY : évite de répéter le même
 * ".findById(id).orElseThrow(...)" dans UserService, BoardService,
 * TaskService (et tout futur service). Un seul endroit à maintenir
 * pour la logique "ressource introuvable".
 */
public final class EntityFinder {

    private EntityFinder() {
        // classe utilitaire : pas d'instanciation
    }

    public static <T, ID> T findOrThrow(JpaRepository<T, ID> repository, ID id, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(entityName + " " + id + " introuvable"));
    }
}