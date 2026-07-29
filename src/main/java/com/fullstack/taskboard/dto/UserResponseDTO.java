package com.fullstack.taskboard.dto;

import lombok.Data;

@Data
//pour le cours : ceci est un dto (Data Transfer Object)
//  qui est utilisé pour transférer les données
//  de l'utilisateur entre le backend et le frontend. 
// Il contient les informations nécessaires pour représenter 
// un utilisateur dans l'application.
// il ne sagit pas d'une table de la base de données, mais juste 
// d'une coquille pour recevoir et lire la reponse JSON envoyé par l'autre microservice (user-service) via FeignClient
public class UserResponseDTO {
    //les champs doivent correspondre exactement aux noms (JSON keys) de la réponse JSON envoyée par le microservice user-service
    private Long id;
    private String firstName; //dans le microservice user-service, le champ est "firstName" (avec un f minuscule)
    private String lastName; //dans le microservice user-service, le champ est "lastName" (avec un l minuscule)
    private String mail; //dans le microservice user-service, le champ est "mail" (avec un e minuscule) pas email
}