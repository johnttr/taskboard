package com.fullstack.taskboard.service;

import com.fullstack.taskboard.dto.BoardCreateRequest;
import com.fullstack.taskboard.dto.BoardResponse;
import com.fullstack.taskboard.model.Board;
import com.fullstack.taskboard.model.User;
import com.fullstack.taskboard.repository.BoardRepository;
import com.fullstack.taskboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponse createBoard(BoardCreateRequest request) {
        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur " + request.ownerId() + " introuvable"));

        Board board = new Board();
        board.setTitle(request.title());
        board.setCreateAt(LocalDateTime.now());
        board.setOwner(owner);

        Board saved = boardRepository.save(board);
        return toResponse(saved);
    }
    @Transactional
    public BoardResponse updateBoard(Long id, BoardCreateRequest request) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board " + id + " introuvable"));

        User owner = userRepository.findById(request.ownerId())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur " + request.ownerId() + " introuvable"));

        board.setTitle(request.title());
        board.setOwner(owner);
        Board updated = boardRepository.save(board);
        return toResponse(updated);
    }
    
    @Transactional(readOnly = true)
    public Board findBoardOrThrow(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Board " + id + " introuvable"));
    }

        @Transactional(readOnly = true)
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll().stream().map(this::toResponse).toList();
    }
    
    @Transactional(readOnly = true)
    public List<BoardResponse> getBoardsByOwner(Long ownerId) {
        return boardRepository.findByOwnerId(ownerId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public BoardResponse getBoardById(Long id) {
        return toResponse(findBoardOrThrow(id));
    }
    @Transactional
    public void deleteBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board " + id + " introuvable"));
        boardRepository.delete(board);
    }
    
    private BoardResponse toResponse(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getCreateAt(),
                board.getOwner().getId(),
                board.getOwner().getName());
    }
}