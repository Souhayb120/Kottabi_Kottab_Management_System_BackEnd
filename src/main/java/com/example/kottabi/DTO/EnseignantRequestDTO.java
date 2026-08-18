package com.example.kottabi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnseignantRequestDTO {

    private String username;
    private String prenom;
    private String nom;
    private String tel;
    private String password;
    private String specialite;
    private String description;
}
