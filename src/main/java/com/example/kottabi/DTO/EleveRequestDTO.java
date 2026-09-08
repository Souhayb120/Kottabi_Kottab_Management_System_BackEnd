package com.example.kottabi.DTO;

import com.example.kottabi.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Prenom is required")
    private String prenom;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Tel is required")
    private String tel;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;

    @NotNull(message = "Date de naissance is required")
    private LocalDate dateNaissance;
}
