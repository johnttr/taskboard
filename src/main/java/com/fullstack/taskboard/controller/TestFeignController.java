package com.fullstack.taskboard.controller;

import com.fullstack.taskboard.client.OdcBackendClient;
import com.fullstack.taskboard.dto.UserResponseDTO;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestFeignController {

    private final OdcBackendClient odcBackendClient;

    public TestFeignController(OdcBackendClient odcBackendClient) {
        this.odcBackendClient = odcBackendClient;
    }

    @GetMapping("/test-feign/{userId}")
    public String testerCommunication(@PathVariable("userId") Long userId) {
        try {
            //L'appel au microservice ODC pour récupérer l'utilisateur par son ID
            UserResponseDTO user = odcBackendClient.getUserById(userId);
            return "Communication réussie avec le microservice ODC. Utilisateur récupéré : " + user;
        } catch (Exception e) {
            return "Erreur lors de la communication avec le microservice ODC : " + e.getMessage();
        }
    }

}
