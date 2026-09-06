package com.example.kottabi.DTO;

import com.example.kottabi.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthRequest {
    private String username;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String tel;
    private String password;
    private Role role;
    private String specialite;
    private String description;
}
