package com.example.kottabi.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnseignantResponseDTO implements Serializable {
    private String username;
    private String prenom;
    private String nom;
    private String email;
    private String tel;
    private String specialite;
    private String description;
}
