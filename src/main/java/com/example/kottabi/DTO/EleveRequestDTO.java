package com.example.kottabi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EleveRequestDTO {
    private String username;
    private String prenom;
    private String nom;
    private String tel;
    private String password;
    private LocalDate dateNaissance;
}
