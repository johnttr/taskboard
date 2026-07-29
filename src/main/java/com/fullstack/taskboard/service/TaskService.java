package com.fullstack.taskboard.service;

import com.fullstack.taskboard.dto.TaskCreateRequest;
import com.fullstack.taskboard.dto.TaskResponse;
import com.fullstack.taskboard.model.Board;
import com.fullstack.taskboard.model.Statut;
import com.fullstack.taskboard.model.Task;
import com.fullstack.taskboard.repository.BoardRepository;
import com.fullstack.taskboard.repository.TaskRepository;
import com.fullstack.taskboard.service.support.EntityFinder;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private static final String ENTITY_NAME = "Task";
    private static final String BOARD_ENTITY_NAME = "Board";

    private final TaskRepository taskRepository;
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @Transactional
    public TaskResponse createTask(TaskCreateRequest request) {
        // La tâche est liée au Board ; le owner est obtenu par transitivité (Task -> Board -> User)
        Board board = EntityFinder.findOrThrow(boardRepository, request.boardId(), BOARD_ENTITY_NAME);

        Task task = new Task();
        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatut(request.statut() != null ? request.statut() : Statut.À_FAIRE);
        task.setCreateAt(LocalDateTime.now());
        task.setBoard(board);

        Task saved = taskRepository.save(task);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getTasksByBoard(Long boardId) {
        boardService.findBoardOrThrow(boardId); // Vérifie que le board existe  
        return taskRepository.findByBoardId(boardId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        return toResponse(findTaskOrThrow(id));
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskCreateRequest request) {
        Task task = EntityFinder.findOrThrow(taskRepository, id, ENTITY_NAME);
        Board board = EntityFinder.findOrThrow(boardRepository, request.boardId(), BOARD_ENTITY_NAME);

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setStatut(request.statut() != null ? request.statut() : task.getStatut());
        task.setBoard(board);

        Task updated = taskRepository.save(task);
        return toResponse(updated);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = EntityFinder.findOrThrow(taskRepository, id, ENTITY_NAME);
        taskRepository.delete(task);
    }

    @Transactional(readOnly = true)
    public Task findTaskOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ENTITY_NAME + " non trouvé avec id: " + id));
    }
    
    private TaskResponse toResponse(Task task) {
        Board board = task.getBoard();
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatut(),
                task.getCreateAt(),
                board.getId(),
                board.getTitle(),
                board.getOwner().getId(),
                board.getOwner().getName());
    }
}