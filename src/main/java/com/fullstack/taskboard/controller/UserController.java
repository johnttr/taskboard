package com.fullstack.taskboard.controller;

import com.fullstack.taskboard.dto.UserCreateRequest;
import com.fullstack.taskboard.dto.UserResponse;
import com.fullstack.taskboard.service.UserService;
import com.fullstack.taskboard.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * COUCHE CONTRÔLEUR (API REST) : UserController
 * Reçoit les requêtes HTTP externes, délègue le traitement au Service,
 * puis retourne la réponse HTTP adéquate.
 *
 * Annotations :
 * - @RestController : Indique que chaque méthode retourne directement le corps
 * de la réponse JSON.
 * - @RequestMapping("/api/users") : Route racine gérée par ce contrôleur.
 * - @RequiredArgsConstructor : Injection automatique du UserService par
 * constructeur.
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * ENDPOINT : Créer un utilisateur
     * - POST http://localhost:8080/api/users
     * - @Valid : Déclenche la validation des contraintes définies sur
     * UserCreateRequest (@NotBlank, @Email).
     * - @RequestBody : Convertit le corps JSON de la requête HTTP en objet Java.
     * - ResponseEntity : Permet de personnaliser le statut HTTP de retour (ici :
     * 201 Created).
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreateRequest request) {
        UserResponse created = userService.CreateUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * ENDPOINT : Lister tous les utilisateurs
     * - GET http://localhost:8080/api/users
     * - Retourne un code HTTP 200 OK avec la liste au format JSON.
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * ENDPOINT : Récupérer un utilisateur par son ID
     * - GET http://localhost:8080/api/users/{id}
     * - @PathVariable : Lie la variable {id} présente dans le chemin d'URL au
     * paramètre de méthode 'id'.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> modifierUtilisateur(@PathVariable Long id, @RequestBody UserCreateRequest user) {
        UserResponse updated = userService.updateUser(id, user);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerUtilisateur(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}