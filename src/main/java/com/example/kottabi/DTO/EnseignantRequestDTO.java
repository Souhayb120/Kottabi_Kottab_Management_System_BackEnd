package com.example.kottabi.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnseignantRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Prenom is required")
    private String prenom;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Tel is required")
    private String tel;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 6 characters")
    private String password;

    @NotBlank(message = "Specialite is required")
    private String specialite;

    @NotBlank(message = "Description is required")
    private String description;
}
