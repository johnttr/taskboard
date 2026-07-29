package com.fullstack.taskboard.controller;

import com.fullstack.taskboard.dto.BoardCreateRequest;
import com.fullstack.taskboard.dto.BoardResponse;
import com.fullstack.taskboard.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * COUCHE CONTRÔLEUR (API REST) : BoardController
 * Reçoit les requêtes HTTP externes, délègue le traitement au Service,
 * puis retourne la réponse HTTP adéquate.
 */
@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@Valid @RequestBody BoardCreateRequest request) {
        BoardResponse created = boardService.createBoard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        return ResponseEntity.ok(boardService.getAllBoards());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoardById(@PathVariable Long id) {
        return ResponseEntity.ok(boardService.getBoardById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> modifierBoard(@PathVariable Long id, @RequestBody BoardCreateRequest request) {
        BoardResponse updated = boardService.updateBoard(id, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
}