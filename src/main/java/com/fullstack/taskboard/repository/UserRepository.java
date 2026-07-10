package com.fullstack.taskboard.repository;

import com.fullstack.taskboard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //acces direct à la base de donnée
    Optional <User> findByEmail(String email);
    Boolean existsByEmail(String email);

}
