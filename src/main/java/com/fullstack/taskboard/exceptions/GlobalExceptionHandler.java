package com.fullstack.taskboard.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

import java.util.HashMap;
import java.util.Map;

/**
 * Gestionnaire d'exceptions centralise.
 *
 * @RestControllerAdvice intercepte les exceptions levees par N'IMPORTE
 *                       QUEL Controller de l'application et les transforme en
 *                       reponse HTTP
 *                       propre. Cela evite d'ecrire des try/catch repetes dans
 *                       chaque
 *                       controller : la couche Controller reste simple,
 *                       lisible, focalisee
 *                       sur le "happy path".
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Ressource non trouvee (User/Board/Task inexistant) -> 404 Not Found.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        ErrorResponse body = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Ressource non trouvée",
            ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    /**
     * Erreurs de validation des DTO (@NotBlank, @NotNull, etc. sur les
     * champs annotes avec @Valid dans les controllers) -> 400 Bad Request.
     *
     * On construit une map { nomDuChamp : messageDerreur } pour que le
     * client de l'API sache precisement quel champ corriger.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        ErrorResponse body = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Données invalides",
            "Un ou plusieurs champs sont invalides",
            fieldErrors);
        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Filet de securite : toute autre exception imprevue -> 500.
     * Evite de renvoyer une stacktrace brute au client.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ex.printStackTrace();
        ErrorResponse body = new ErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erreur interne du serveur",
            "Une erreur inattendue est survenue. Veuillez contacter l'administrateur.");
        return ResponseEntity.internalServerError().body(body);
    }
}