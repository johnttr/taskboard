package com.fullstack.taskboard.service;

import com.fullstack.taskboard.dto.UserCreateRequest;
import com.fullstack.taskboard.dto.UserResponse;
import com.fullstack.taskboard.model.User;
import com.fullstack.taskboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponse CreateUser(UserCreateRequest request) {
        //verification si le email existe dans la BDD
        if(userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Cet utiliateur existe déja");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        
        User saved = userRepository.save(user);
        return toResponse(saved);
    }
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    //public User findUserorthrow(Long id) {
    public String findUserorthrow(Long id) {
    //    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable"));
        return "L'utilisateur avec l'ID " + id + " n'existe pas.";
    }


    /*
     * 
     * @param id
     * @return
    
    @Transactional(readOnly = true)
    //public UserResponse getUserById(Long id) {
    public UserResponse getUserById(Long id) {
        //User user = findUserorthrow(id);
        String trouve = findUserorthrow(id);
        //return toResponse(user);
        return trouve != null ? new UserResponse(id, "Nom de l'utilisateur", "Email de l'utilisateur") : null;
    }
    **/
   @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur "+ id + " introuvable"));
        return toResponse(user);
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
