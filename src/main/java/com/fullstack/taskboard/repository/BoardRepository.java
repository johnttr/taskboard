package com.fullstack.taskboard.repository;

import com.fullstack.taskboard.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //acces direct à la base de donnée
    List<Board> findByOwnerId(Long ownerId);
}