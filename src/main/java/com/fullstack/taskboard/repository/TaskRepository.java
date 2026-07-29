package com.fullstack.taskboard.repository;

import com.fullstack.taskboard.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    // Avec JPA nous avons beaucoup plus de methodes disponibles que CRUD repository. Nous pouvons faire des requetes sur les attributs de l'entité. Par exemple, pour récupérer toutes les tâches d'un tableau spécifique, nous pouvons utiliser la méthode findByBoardId(Long boardId). Cette méthode est automatiquement implémentée par Spring Data JPA en fonction du nom de la méthode et de l'attribut de l'entité Task.
    List<Task> findByBoardId(Long boardId);
}