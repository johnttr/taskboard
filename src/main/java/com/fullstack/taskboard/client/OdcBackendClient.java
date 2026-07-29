package com.fullstack.taskboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.fullstack.taskboard.dto.UserResponseDTO;

@FeignClient(name = "odc-backend-client", url = "http://localhost:9000") // Remplacez l'URL par celle de votre microservice user-service
public interface OdcBackendClient {
    @GetMapping("/employee/{id}")
    UserResponseDTO getUserById(@PathVariable("id") Long userid);
}
